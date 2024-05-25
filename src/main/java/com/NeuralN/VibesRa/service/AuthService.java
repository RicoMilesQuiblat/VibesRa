package com.NeuralN.VibesRa.service;


import com.NeuralN.VibesRa.dto.SignUpRequest;
import com.NeuralN.VibesRa.exception.UserNotFoundException;
import com.NeuralN.VibesRa.model.AuthResponse;
import com.NeuralN.VibesRa.model.Role;
import com.NeuralN.VibesRa.model.Token;
import com.NeuralN.VibesRa.model.User;
import com.NeuralN.VibesRa.repository.TokenRepository;
import com.NeuralN.VibesRa.repository.UserRepository;
import com.NeuralN.VibesRa.util.JwtUtil;
import com.sun.tools.jconsole.JConsoleContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private final TokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository repository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil,
                       TokenRepository tokenRepository,
                       AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
    }


    public AuthResponse register(SignUpRequest request) {
        try {
            if(repository.findByUsername(request.getUsername()).isPresent()) {
                return new AuthResponse(null, null,"User already exist", null);
            }

            User user = new User();
            user.setFirstname(request.getFirstname());
            user.setLastname(request.getLastname());
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setRole(Role.ROLE_USER);
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            user = repository.save(user);

            String accessToken = jwtUtil.generateAccessToken(user);
            String refreshToken = jwtUtil.generateRefreshToken(user);

            saveUserToken(accessToken, refreshToken, user);

            return new AuthResponse(accessToken, refreshToken,"User registration was successful", user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new AuthResponse(null, null, "An unexpected error occurred", null);
        }
    }

    public AuthResponse authenticate(User request) {
        try {
            User user = repository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new UserNotFoundException("No user found with username: " + request.getUsername()));

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            String accessToken = jwtUtil.generateAccessToken(user);
            String refreshToken = jwtUtil.generateRefreshToken(user);

            revokeAllTokenByUser(user);
            saveUserToken(accessToken, refreshToken, user);
            System.out.println("User login was successful");
            return new AuthResponse(accessToken, refreshToken, "User login was successful", user);
        } catch (UserNotFoundException e) {
            System.err.println(e.getMessage());
            return new AuthResponse(null, null, e.getMessage(), null);
        } catch (AuthenticationException e) {
            System.err.println(e.getMessage());
            return new AuthResponse(null, null, "Invalid username or password", null);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new AuthResponse(null, null, "An unexpected error occurred", null);
        }
    }

    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllAccessTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }
    private void saveUserToken(String accessToken, String refreshToken, User user) {
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    public ResponseEntity refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        // extract the token from authorization header
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);

        String username = jwtUtil.extractUsername(token);

        User user = repository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("No user found"));

        if(jwtUtil.isValidRefreshToken(token, user)) {
            String accessToken = jwtUtil.generateAccessToken(user);
            String refreshToken = jwtUtil.generateRefreshToken(user);

            revokeAllTokenByUser(user);
            saveUserToken(accessToken, refreshToken, user);
            return new ResponseEntity(new AuthResponse(accessToken, refreshToken, "New token generated", user), HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}