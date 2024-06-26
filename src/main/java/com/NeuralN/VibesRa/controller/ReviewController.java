package com.NeuralN.VibesRa.controller;

import com.NeuralN.VibesRa.model.Review;
import com.NeuralN.VibesRa.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable UUID reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        if (review == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(review, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<Review> saveReview(@RequestBody Review review) {
        return new ResponseEntity<>(reviewService.saveReview(review), HttpStatus.CREATED);
    }

    @PutMapping("/{reviewId}/update")
    public ResponseEntity<Review> updateReview(@PathVariable UUID reviewId, @RequestBody Review updatedReview) {
        Review review = reviewService.updateReview(reviewId, updatedReview);
        if (review == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}/delete")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID reviewId) {
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
