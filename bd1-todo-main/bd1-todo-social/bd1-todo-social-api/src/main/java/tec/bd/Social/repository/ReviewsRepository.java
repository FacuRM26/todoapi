package tec.bd.Social.repository;

import tec.bd.Social.Image;
import tec.bd.Social.Reviews;

import java.util.Date;
import java.util.List;

public interface ReviewsRepository {
    Reviews getall(String todo_id);
    Reviews addReview(Reviews reviews);
    void deleteReview(String reviewId);
    Reviews updateReview(Reviews reviews);
    Reviews addImage(Reviews reviews);
    Image getImage(String reviewId, String imageId);


}
