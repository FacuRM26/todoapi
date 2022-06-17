package tec.bd.Social.service;

import tec.bd.Social.Image;
import tec.bd.Social.Reviews;
import tec.bd.Social.repository.ReviewsRepository;


import java.util.List;

public class ReviewsServiceImpl implements ReviewsService {
    private ReviewsRepository reviewsRepository;


    public ReviewsServiceImpl(ReviewsRepository reviewsRepository) {
        this.reviewsRepository = reviewsRepository;
    }
    @Override
    public Reviews getAll(String todo_id) {
       return this.reviewsRepository.getall(todo_id);
    }

    @Override
    public Reviews addReview(Reviews reviews) {

        return this.reviewsRepository.addReview(reviews);
    }

    @Override
    public void deleteReview(String reviewId) {
        this.reviewsRepository.deleteReview(reviewId);

    }

    @Override
    public Reviews updateReview(Reviews reviews) {
        return this.reviewsRepository.updateReview(reviews);
    }

    @Override
    public Reviews addImage(Reviews reviews) {
        return null;
    }

    @Override
    public Image getImage(String reviewId, String imageId) {
        return null;
    }
}

