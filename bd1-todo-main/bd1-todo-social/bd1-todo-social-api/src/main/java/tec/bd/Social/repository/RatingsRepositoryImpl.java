package tec.bd.Social.repository;

import tec.bd.Social.Rating;
import tec.bd.Social.datasource.DBManager;


import java.sql.*;

public class RatingsRepositoryImpl extends BaseRepository implements RatingsRepository {

    private static final String  FIND_BY_ID_QUERY = "SELECT * FROM rating WHERE id = ?";
    private static final String CALCULATE_AVG_PROC="{CALL calculated_rating_avg(?)}";

    private static final String ADD_RATING_PROC="{CALL add_rating(?, ?, ?, ?)}";

    private static final String DELETE_RATING_PROC="{CALL delete_rating(?)}";

    public RatingsRepositoryImpl(DBManager dbManager) {
        super(dbManager);
    }



    @Override
    public Rating findById(int ratingId) {
        System.out.println("hola");
        try {
            var connection = this.connect();
            var statement = connection.prepareStatement(FIND_BY_ID_QUERY);
            statement.setInt(1, ratingId);
            var resultSet = this.query(statement);
            while(resultSet.next()) {
                var rating = toEntity(resultSet);
                return (Rating) rating;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public float findAvg(String todoId) {
        try {
            var connection = this.connect();
            CallableStatement statement = connection.prepareCall(CALCULATE_AVG_PROC);
            statement.setString(1,todoId );

            var rs = this.query(statement);
            while(rs.next()) {
                var ratingAvg = rs.getFloat("ratingAvg");
                return ratingAvg;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("hola");
        return 0.0f;
    }

    @Override
    public void addRating(Rating rating) {
        try {
            System.out.println("hola");
            var connect = this.connect();
            var statement = connect.prepareStatement(ADD_RATING_PROC);
            statement.setInt(1,rating.getRatingValue());
            statement.setString(2,rating.getTodoId());
            statement.setString(3, rating.getClientId());
            statement.setDate(4, new java.sql.Date(rating.getCreatedAt().getTime()));
            var actual = this.execute(statement);
            System.out.println("Actual: " + actual);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRating(String ratingId) {
        try {
            var connect = this.connect();
            var statement = connect.prepareStatement(DELETE_RATING_PROC);
            statement.setString(1, ratingId);
            var actual = this.execute(statement);
            System.out.println("Actual: " + actual);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object toEntity(ResultSet resultSet) {
        try {
            var rating = new Rating(
                    resultSet.getInt("id"),
                    resultSet.getInt("ratingvalue"),
                    resultSet.getString("todoid"),
                    resultSet.getString("clientid"),
                    resultSet.getDate("createdat")
            );

            return rating;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
