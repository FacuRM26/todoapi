package tec.bd.Social.service;

import tec.bd.Social.Rating;
import tec.bd.Social.repository.RatingsRepository;

public class RatingsServiceImpl implements RatingsService {
    private RatingsRepository ratingsRepository;

    public RatingsServiceImpl(RatingsRepository ratingsRepository) {

        this.ratingsRepository = ratingsRepository;
    }
    @Override
    public Rating getRating(int ratingId) {

        return this.ratingsRepository.findById(ratingId);
    }

    @Override
    public float getRatingAverage(String todoId) {

        return this.ratingsRepository.findAvg(todoId);
    }

    @Override
    public void addRating(Rating rating) {

            this.ratingsRepository.addRating(rating);
    }

    @Override
    public void deleteRating(String ratingId) {
        this.ratingsRepository.deleteRating(ratingId);

    }
}
