package tec.bd.Social.repository;

import tec.bd.Social.Rating;

public interface RatingsRepository {

    Rating findById(int id);
    float findAvg(String todoId);
    void addRating(Rating rating);

    void deleteRating(String ratingId);
}
