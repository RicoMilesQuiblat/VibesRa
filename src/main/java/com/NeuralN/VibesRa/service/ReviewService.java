package com.NeuralN.VibesRa.service;

import com.NeuralN.VibesRa.model.Review;
import com.NeuralN.VibesRa.repository.HotelRoomRepository;
import com.NeuralN.VibesRa.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private HotelRoomRepository hotelRoomRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(UUID reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        return review.orElse(null);
    }

    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    public void deleteReview(UUID reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public Review updateReview(UUID reviewId, Review updatedReview) {
        Review existingReview = getReviewById(reviewId);
        if (existingReview == null) {
            return null;
        }
        updatedReview.setId(reviewId);
        return reviewRepository.save(updatedReview);
    }
}
