package com.NeuralN.VibesRa.specification;

import com.NeuralN.VibesRa.model.HotelRoom;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class HotelRoomSpecification {

    public static Specification<HotelRoom> hasAddedCategory(boolean addedCategory) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("addedCategory"), addedCategory);
    }

    public static Specification<HotelRoom> hasAddedLocation(boolean addedLocation) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("addedLocation"), addedLocation);
    }

    public static Specification<HotelRoom> hasAddedDescription(boolean addedDescription) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("addedDescription"), addedDescription);
    }

    public static Specification<HotelRoom> hasLocation(String location) {
        return (root, query, criteriaBuilder) -> location != null ? criteriaBuilder.equal(root.get("location"), location) : null;
    }

    public static Specification<HotelRoom> hasGuests(Integer guests) {
        return (root, query, criteriaBuilder) -> guests != null ? criteriaBuilder.equal(root.get("numOfGuests"), guests) : null;
    }

    public static Specification<HotelRoom> hasBedrooms(Integer bedrooms) {
        return (root, query, criteriaBuilder) -> bedrooms != null ? criteriaBuilder.equal(root.get("numOfBedrooms"), bedrooms) : null;
    }

    public static Specification<HotelRoom> hasBathrooms(Integer bathrooms) {
        return (root, query, criteriaBuilder) -> bathrooms != null ? criteriaBuilder.equal(root.get("numOfBathrooms"), bathrooms) : null;
    }

//    public static Specification<HotelRoom> belongsToUser(UUID userId) {
//        return (root, query, criteriaBuilder) -> userId != null ? criteriaBuilder.equal(root.get("user").get("id"), userId) : null;
//    }
    public static Specification<HotelRoom> belongsToType(String type) {
        return (root, query, criteriaBuilder) -> type != null ? criteriaBuilder.equal(root.get("type"), type) : null;
    }
}
