package tec.bd.Social.repository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import tec.bd.Social.Image;

import tec.bd.Social.Reviews;
import tec.bd.Social.datasource.DBManager;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class ReviewsRepositoryImpl extends BaseRepository implements ReviewsRepository {
    private static final String GET_ALL_REVIEWS = "{call get_all(?)}";
    private static final String DELETE_REVIEW_PROC="{CALL delete_review(?)}";

    private static final String ADD_REVIEW_PROC="{call add_review(?,?,?,?)}";

    private static final String UPDATE_REVIEW_PROC="{call update_review(?,?,?)}";

    public ReviewsRepositoryImpl(DBManager dbManager) {
        super(dbManager);
    }


    @Override
    public Reviews getall(String todo_id) {
        var reviewsList = new ArrayList<Reviews>();
        try {
            System.out.println("todo_id: " + todo_id);
            var connection = this.connect();
            var statement = connection.prepareStatement(GET_ALL_REVIEWS);
            statement.setString(1, todo_id);
            var resultSet = this.query(statement);
            while(resultSet.next()) {
                var reviews = toEntity(resultSet);
                return reviews;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Reviews addReview(Reviews reviews) {
        try {

            var connect = this.connect();
            var statement = connect.prepareStatement(ADD_REVIEW_PROC);
            statement.setString(1,reviews.getReviewId());
            statement.setDate(2, new java.sql.Date(reviews.getFecha().getTime()));

            statement.setString(3,reviews.getComentario());
            statement.setString(4, reviews.getClientId());

            var actual = this.execute(statement);
            System.out.println("Actual: " + actual);
            return reviews;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteReview(String reviewId) {
        try {
            var connect = this.connect();
            var statement = connect.prepareStatement(DELETE_REVIEW_PROC);
            statement.setString(1, reviewId);
            var actual = this.execute(statement);
            System.out.println("Actual: " + actual);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Reviews updateReview(Reviews reviews) {
        try {
            var connect = this.connect();
            var statement = connect.prepareStatement(UPDATE_REVIEW_PROC);
            statement.setString(1,reviews.getReviewId());
            statement.setDate(2, new java.sql.Date(reviews.getFecha().getTime()));
            statement.setString(3,reviews.getComentario());
            var actual = this.execute(statement);
            System.out.println("Actual: " + actual);
            return reviews;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Reviews addImage(Reviews reviews) {
        return null;
    }

    @Override
    public Image getImage(String reviewId, String imageId) {
        return null;
    }

    @Override
    public Reviews toEntity(ResultSet resultSet) {
            try {
                var review = new Reviews(
                        resultSet.getString("id"),
                        resultSet.getString("comentario"),
                        resultSet.getString("clientid"),
                        resultSet.getDate("fecha")
                );


                return review;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }


