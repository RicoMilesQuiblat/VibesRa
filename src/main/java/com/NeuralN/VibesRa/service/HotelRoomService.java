package com.NeuralN.VibesRa.service;

import com.NeuralN.VibesRa.dto.HotelRoomDTO;
import com.NeuralN.VibesRa.model.HotelRoom;
import com.NeuralN.VibesRa.repository.HotelRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.slugify.Slugify;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelRoomService  {

    @Autowired
    private HotelRoomRepository hotelRoomRepository;

    final Slugify slg = Slugify.builder().build();

    public List<HotelRoomDTO> getAllRooms() {
        List<HotelRoom> rooms = hotelRoomRepository.findAll();
        return rooms.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public HotelRoomDTO getRoomById(Long id) {
        HotelRoom room = hotelRoomRepository.findById(id).orElse(null);
        return room != null ? convertToDTO(room) : null;
    }

    public HotelRoomDTO saveRoom(HotelRoomDTO hotelRoomDTO) {
        hotelRoomDTO.setSlug(slg.slugify(hotelRoomDTO.getName()));
        HotelRoom room = convertToEntity(hotelRoomDTO);
        HotelRoom savedRoom = hotelRoomRepository.save(room);
        return convertToDTO(savedRoom);
    }

    public HotelRoomDTO updateRoom(Long id, HotelRoomDTO hotelRoomDTO) {
        HotelRoom existingRoom = hotelRoomRepository.findById(id).orElse(null);
        if (existingRoom != null) {
            updateEntityFromDTO(existingRoom, hotelRoomDTO);
            HotelRoom updatedRoom = hotelRoomRepository.save(existingRoom);
            return convertToDTO(updatedRoom);
        }
        return null;
    }

    public HotelRoom deleteRoom(Long id) {
        if (hotelRoomRepository.existsById(id)) {
            HotelRoom room = hotelRoomRepository.findById(id).orElse(null);
            hotelRoomRepository.deleteById(id);
            return room;
        }
        return null;
    }

    private HotelRoomDTO convertToDTO(HotelRoom room) {
        HotelRoomDTO dto = new HotelRoomDTO();
        dto.setHotelID(room.getId());
        dto.setImages(room.getImages());
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
        dto.setNumberOfBeds(room.getNumberOfBeds());
        dto.setSlug(slg.slugify(room.getName()));

        HotelRoomDTO.CoverImageDTO coverImageDTO = new HotelRoomDTO.CoverImageDTO();
        coverImageDTO.setUrl(room.getCoverImage().getUrl());
        coverImageDTO.setFile(room.getCoverImage().getFile());
        dto.setCoverImage(coverImageDTO);

        List<HotelRoomDTO.AmenityDTO> amenityDTOs = room.getOfferedAmenities().stream().map(amenity -> {
            HotelRoomDTO.AmenityDTO amenityDTO = new HotelRoomDTO.AmenityDTO();
            amenityDTO.setIcon(amenity.getIcon());
            amenityDTO.setAmenity(amenity.getAmenity());
            return amenityDTO;
        }).collect(Collectors.toList());
        dto.setOfferedAmenities(amenityDTOs);

        return dto;
    }

    private HotelRoom convertToEntity(HotelRoomDTO dto) {
        HotelRoom room = new HotelRoom();
        room.setId(dto.getHotelID());
        room.setImages(dto.getImages());
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
        room.setNumberOfBeds(dto.getNumberOfBeds());
        room.setSlug(dto.getSlug());

        HotelRoom.CoverImage coverImage = new HotelRoom.CoverImage();
        coverImage.setUrl(dto.getCoverImage().getUrl());
        coverImage.setFile(dto.getCoverImage().getFile());
        room.setCoverImage(coverImage);

        List<HotelRoom.Amenity> amenities = dto.getOfferedAmenities().stream().map(amenityDTO -> {
            HotelRoom.Amenity amenity = new HotelRoom.Amenity();
            amenity.setIcon(amenityDTO.getIcon());
            amenity.setAmenity(amenityDTO.getAmenity());
            return amenity;
        }).collect(Collectors.toList());
        room.setOfferedAmenities(amenities);

        return room;
    }

    private void updateEntityFromDTO(HotelRoom room, HotelRoomDTO dto) {
        room.setImages(dto.getImages());
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
        room.setNumberOfBeds(dto.getNumberOfBeds());
        room.setSlug(slg.slugify(dto.getName()));

        HotelRoom.CoverImage coverImage = new HotelRoom.CoverImage();
        coverImage.setUrl(dto.getCoverImage().getUrl());
        coverImage.setFile(dto.getCoverImage().getFile());
        room.setCoverImage(coverImage);

        List<HotelRoom.Amenity> amenities = dto.getOfferedAmenities().stream().map(amenityDTO -> {
            HotelRoom.Amenity amenity = new HotelRoom.Amenity();
            amenity.setIcon(amenityDTO.getIcon());
            amenity.setAmenity(amenityDTO.getAmenity());
            return amenity;
        }).collect(Collectors.toList());
        room.setOfferedAmenities(amenities);
    }

    public Optional<List<HotelRoomDTO>> getHotelsByLocation(String location) {
        Optional<List<HotelRoom>> hotels = hotelRoomRepository.findByLocation(location);
        return hotels.map(list -> list.stream().map(this::convertToDTO).collect(Collectors.toList()));
    }
}
