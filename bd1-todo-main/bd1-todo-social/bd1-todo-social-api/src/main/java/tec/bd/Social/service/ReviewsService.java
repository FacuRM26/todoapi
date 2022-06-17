package tec.bd.Social.service;

import tec.bd.Social.Image;
import tec.bd.Social.Reviews;

import java.util.List;

public interface ReviewsService {
    Reviews getAll(String todo_id);
    Reviews addReview(Reviews reviews);
    void deleteReview(String reviewId);
    Reviews updateReview(Reviews reviews);
    Reviews addImage(Reviews reviews);
    Image getImage(String reviewId, String imageId);
}
