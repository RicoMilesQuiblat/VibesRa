package com.NeuralN.VibesRa.repository;

import com.NeuralN.VibesRa.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {


    @Query("""
        select t from Token t inner join User u on t.user.id = u.id
        where t.user.id = :userId and t.isLoggedOut = false
    """)
    List<Token> findAllAccessTokensByUser(UUID userId);

    Optional<Token> findByAccessToken(String token);

    Optional<Token > findByRefreshToken(String token);
}