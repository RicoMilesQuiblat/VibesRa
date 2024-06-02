package com.NeuralN.VibesRa.service;

import com.NeuralN.VibesRa.dto.*;
import com.NeuralN.VibesRa.model.Favorite;
import com.NeuralN.VibesRa.model.HotelRoom;
import com.NeuralN.VibesRa.model.User;
import com.NeuralN.VibesRa.repository.FavoriteRepository;
import com.NeuralN.VibesRa.repository.HotelRoomRepository;
import com.NeuralN.VibesRa.repository.UserRepository;
import com.NeuralN.VibesRa.specification.HotelRoomSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.github.slugify.Slugify;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HotelRoomService {

    @Autowired
    private HotelRoomRepository hotelRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    final Slugify slg = Slugify.builder().build();

    public List<HotelRoomDTO> getAllRooms() {
        List<HotelRoom> rooms = hotelRoomRepository.findAll();
        return rooms.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public HotelRoom getById(UUID id) {
        return hotelRoomRepository.findById(id).orElse(null);
    }

    public HotelRoomDTO getRoomById(UUID id) {
        HotelRoom room = hotelRoomRepository.findById(id).orElse(null);
        return room != null ? convertToDTO(room) : null;
    }

    public HotelRoomDTO saveRoom(HotelRoomDTO hotelRoomDTO, UUID userId) {
        hotelRoomDTO.setSlug(slg.slugify(hotelRoomDTO.getName()));
        hotelRoomDTO.setUserId(userId);
        HotelRoom room = convertToEntity(hotelRoomDTO);
        HotelRoom savedRoom = hotelRoomRepository.save(room);
        return convertToDTO(savedRoom);
    }

    public HotelRoomDTO updateRoom(HotelRoomDTO hotelRoomDTO) {
        HotelRoom room = hotelRoomRepository.findById(hotelRoomDTO.getHotelId()).orElse(null);
        if (room != null) {
            updateEntityFromDTO(room, hotelRoomDTO);
            HotelRoom updatedRoom = hotelRoomRepository.save(room);
            return convertToDTO(updatedRoom);
        }
        return null;
    }

    public boolean deleteRoom(UUID id) {
        if (hotelRoomRepository.existsById(id)) {
            HotelRoom room = hotelRoomRepository.findById(id).orElse(null);
            hotelRoomRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Boolean isRoomBooked(UUID id) {
        HotelRoom room = hotelRoomRepository.findById(id).orElse(null);
        if (room != null) {
            return room.getIsBooked();
        }
        return null;
    }

    private HotelRoomDTO convertToDTO(HotelRoom room) {
        HotelRoomDTO dto = new HotelRoomDTO();
        dto.setUserId(room.getUser().getId());
        dto.setHotelId(room.getId());
        dto.setRoomImages(room.getRoomImages());
        dto.setName(room.getName());
        dto.setLocation(room.getLocation());
        dto.setDescription(room.getDescription());
        dto.setContact(room.getContact());
        dto.setEmail(room.getEmail());
        dto.setSpecialNote(room.getSpecialNote());
        dto.setPrice(room.getPrice());
        dto.setIsBooked(room.getIsBooked());
        dto.setType(room.getType());
        dto.setDimension(room.getDimension());
        dto.setNumOfBedrooms(room.getNumOfBedrooms());
        dto.setNumOfBathrooms(room.getNumOfBathrooms());
        dto.setNumOfGuests(room.getNumOfGuests());
        dto.setSlug(slg.slugify(room.getName()));
        dto.setCoverImage(room.getCoverImage());
        dto.setAddedCategory(room.isAddedCategory());
        dto.setAddedDescription(room.isAddedDescription());
        dto.setAddedLocation(room.isAddedLocation());
        return dto;
    }

    private HotelRoom convertToEntity(HotelRoomDTO dto) {
        HotelRoom room = new HotelRoom();
        room.setUser(userRepository.findById(dto.getUserId()).orElse(null));
        room.setId(dto.getHotelId());
        room.setRoomImages(dto.getRoomImages());
        room.setName(dto.getName());
        room.setLocation(dto.getLocation());
        room.setDescription(dto.getDescription());
        room.setContact(dto.getContact());
        room.setEmail(dto.getEmail());
        room.setSpecialNote(dto.getSpecialNote());
        room.setPrice(dto.getPrice());
        room.setIsBooked(dto.getIsBooked());
        room.setType(dto.getType());
        room.setDimension(dto.getDimension());
        room.setNumOfBedrooms(dto.getNumOfBedrooms());
        room.setNumOfBathrooms(dto.getNumOfBathrooms());
        room.setNumOfGuests(dto.getNumOfGuests());
        room.setCoverImage(dto.getCoverImage());
        room.setSlug(dto.getSlug());
        room.setAddedCategory(dto.isAddedCategory());
        room.setAddedDescription(dto.isAddedDescription());
        room.setAddedLocation(dto.isAddedLocation());


        return room;
    }

    private void updateEntityFromDTO(HotelRoom room, HotelRoomDTO dto) {
        if (dto.getRoomImages() != null) room.setRoomImages(dto.getRoomImages());
        if (dto.getName() != null) room.setName(dto.getName());
        if (dto.getLocation() != null) room.setLocation(dto.getLocation());
        if (dto.getDescription() != null) room.setDescription(dto.getDescription());
        if (dto.getContact() != null) room.setContact(dto.getContact());
        if (dto.getEmail() != null) room.setEmail(dto.getEmail());
        if (dto.getSpecialNote() != null) room.setSpecialNote(dto.getSpecialNote());
        if (dto.getPrice() != 0) room.setPrice(dto.getPrice());
        if (dto.getIsBooked() != null) room.setIsBooked(dto.getIsBooked());
        if (dto.getType() != null) room.setType(dto.getType());
        if (dto.getDimension() != null) room.setDimension(dto.getDimension());
        if (dto.getNumOfBedrooms() != null) room.setNumOfBedrooms(dto.getNumOfBedrooms());
        if (dto.getNumOfBathrooms() != null) room.setNumOfBathrooms(dto.getNumOfBathrooms());
        if (dto.getNumOfGuests() != null) room.setNumOfGuests(dto.getNumOfGuests());
        if (dto.getName() != null) room.setSlug(slg.slugify(dto.getName()));
        if (dto.getCoverImage() != null) room.setCoverImage(dto.getCoverImage());
        room.setAddedCategory(dto.isAddedCategory());
        room.setAddedDescription(dto.isAddedDescription());
        room.setAddedLocation(dto.isAddedLocation());
    }


    public Optional<List<HotelRoomDTO>> getHotelsByLocation(String location) {
        Optional<List<HotelRoom>> hotels = hotelRoomRepository.findByLocation(location);
        return hotels.map(list -> list.stream().map(this::convertToDTO).collect(Collectors.toList()));
    }


    public HotelRoomDTO getLatestHotelByUserId(UUID id) {
        Optional<HotelRoom> hotelRoomOptional = hotelRoomRepository.findFirstByUserIdOrderByIdDesc(id);
        if (hotelRoomOptional.isPresent()) {
            HotelRoom hotelRoom = hotelRoomOptional.get();
            return convertToDTO(hotelRoom);
        } else {
            return null;
        }
    }

    public HotelRoom getHotelRoomWithFavorites(UUID id) {
        return hotelRoomRepository.findByIdWithFavorites(id)
                .orElse(null);
    }

    public List<HotelRoomwithOwnerDTO> findHotelRooms(Boolean addedCategory, Boolean addedLocation, Boolean addedDescription, String type, String location, Integer guests, Integer bedrooms, Integer bathrooms, UUID userId) {
        Specification<HotelRoom> spec = Specification.where(HotelRoomSpecification.hasAddedCategory(addedCategory))
                .and(HotelRoomSpecification.hasAddedLocation(addedLocation))
                .and(HotelRoomSpecification.hasAddedDescription(addedDescription))
                .and(HotelRoomSpecification.belongsToType(type))
                .and(HotelRoomSpecification.hasLocation(location))
                .and(HotelRoomSpecification.hasGuests(guests))
                .and(HotelRoomSpecification.hasBedrooms(bedrooms))
                .and(HotelRoomSpecification.hasBathrooms(bathrooms));
//                .and(HotelRoomSpecification.belongsToUser(userId));

        List<HotelRoom> hotelRooms = hotelRoomRepository.findAll(spec);
        return hotelRooms.stream().map(hotelRoom -> {
            HotelRoomwithOwnerDTO hotelRoomwithOwnerDTO = new HotelRoomwithOwnerDTO();
            User user = hotelRoom.getUser();
            hotelRoomwithOwnerDTO.setHotelRoom(convertToDTO(hotelRoom));
            hotelRoomwithOwnerDTO.setUser(convertToUserDTO(user));
            CheckFavoriteDTO checkFavoriteDTO = new CheckFavoriteDTO();
            if (hotelRoom.getFavorites().isEmpty()) {
                checkFavoriteDTO.setFavorite(false);
            } else {
                if (hotelRoom.getFavorites().stream().anyMatch(favorite -> favorite.getUser().getId().equals(userId))) {
                    checkFavoriteDTO.setFavorite(true);
                    checkFavoriteDTO.setFavoriteId(hotelRoom.getFavorites().stream().filter(favorite -> favorite.getUser().getId().equals(userId)).findFirst().get().getId());
                }
            }
            hotelRoomwithOwnerDTO.setCheckFavorite(checkFavoriteDTO);
            return hotelRoomwithOwnerDTO;
        }).collect(Collectors.toList());
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        return userDTO;
    }



    public UserDTO getUserById(UUID userId) {
        return userRepository.findById(userId).map(user -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setEmail(user.getEmail());
            userDTO.setFirstname(user.getFirstname());
            userDTO.setLastname(user.getLastname());
            return userDTO;
        }).orElse(null);
    }

    public List<HotelRoomDTO> getHotelsByUserId(UUID id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }

        return user.getHotelRooms().stream()
                .map(hotelRoom -> {
                    if (hotelRoom.isAddedLocation() && hotelRoom.isAddedCategory() && hotelRoom.isAddedDescription()) {
                        return convertToDTO(hotelRoom);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
