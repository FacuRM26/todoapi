package tec.bd.Social.service;

import tec.bd.Social.Rating;

public interface RatingsService {
    Rating getRating(int ratingId);
    float getRatingAverage(String todoId);
    void addRating(Rating rating);

    void deleteRating(String ratingId);
}
