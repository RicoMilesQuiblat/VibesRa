package com.NeuralN.VibesRa.service;

import com.NeuralN.VibesRa.model.Review;
import com.NeuralN.VibesRa.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(int reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        return review.orElse(null);
    }

    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    public void deleteReview(int reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public Review updateReview(int reviewId, Review updatedReview) {
        Review existingReview = getReviewById(reviewId);
        if (existingReview == null) {
            return null; // or throw exception
        }
        updatedReview.setReviewId(reviewId);
        return reviewRepository.save(updatedReview);
    }
}
