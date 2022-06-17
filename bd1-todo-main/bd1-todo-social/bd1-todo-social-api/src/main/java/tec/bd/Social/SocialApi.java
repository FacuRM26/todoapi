package tec.bd.Social;

import com.google.gson.Gson;

import tec.bd.todo.auth.service.SessionServiceImpl;

import java.util.Map;

import static spark.Spark.*;

public class SocialApi
{
    public static void main( String[] args )
    {
        port(8082);
        var context = WebApplicationContext.init();
        var authenticationClient = context.getAuthenticationClient();
        var ratingsService = context.getRatingsService();
        var reviewsService = context.getReviewsService();
        Gson gson= context.getGson();


        before((request, response) -> {

            var sessionParam = request.headers("x-session-id");
            if(null == sessionParam) {
                halt(401, "Unauthorized");
            }
        });

           options("/", (request, response) -> {
            response.header("Content-Type", "application/json");
            return Map.of(
                    "message", "SOCIAL API V1");
        }, gson::toJson);

        get("/ratings/:rating-id", (request, response) -> {
            var ratingIdParams = request.params("rating-id");
            var ratingId = Integer.parseInt(ratingIdParams);
            var rating =ratingsService.getRating(ratingId);
            if (rating!=null) {
                return rating;
            }
            response.status(404);
            return Map.of();
        },gson::toJson);

        //obtener promedio
        get("/todos/:todo-id/rating", (request, response) -> {
            var todoId = request.params("todo-id");
            var ratingAvg=ratingsService.getRatingAverage(todoId);
            response.status(200);
            response.header("Content-Type", "application/json");
            return Map.of(
                    "todo-id",todoId,"ratingAvg", ratingAvg);
        },gson::toJson);

        post("/ratings/:todo-id", (request, response) -> {
            var todoId= request.params("todo-id");
            var rating = gson.fromJson(request.body(), Rating.class);
            rating.setTodoId(todoId);
            rating.setClientId(request.headers("x-session-id"));
            try {
                ratingsService.addRating(rating);
                response.status(200);
                return rating;
            } catch (SessionServiceImpl.CredentialsException e) {
                response.status(400);
                return Map.of("Message", "Bad Credentials");
            }

        },gson::toJson);

        delete("/ratings/:todo-id", (request, response) -> {
            ratingsService.deleteRating(request.params("todo-id"));
            response.status(200);
            return Map.of("Deleted", "OK");
        },gson::toJson);

        get("/reviews/:todo-id", (request, response) -> {
            var reviewId = request.params("todo-id");

            return reviewsService.getAll(reviewId);


        }, gson::toJson);

        post("/reviews/:todo-id", (request, response) -> {
            var todoId= request.params("todo-id");
            var review = gson.fromJson(request.body(), Reviews.class);
            review.setReviewId(todoId);
            try {
                //System.out.println(credentials.getComentario());
                reviewsService.addReview(review);
                response.status(200);
                return review;
            } catch (SessionServiceImpl.CredentialsException e) {
                response.status(400);
                return Map.of("Message", "Bad Credentials");
            }
        },gson::toJson);



        delete("/reviews/:todo-id", (request, response) -> {
            var reviewId = request.params("todo-id");
            reviewsService.deleteReview(reviewId);
            response.status(200);
            return Map.of("Deleted", "OK");
        },gson::toJson);

        put("/reviews/:todo-id", (request, response) -> {
            var todoId= request.params("todo-id");
            var review = gson.fromJson(request.body(), Reviews.class);
            review.setReviewId(todoId);
            try {

                reviewsService.updateReview(review);
                response.status(200);
                return review;
            } catch (SessionServiceImpl.CredentialsException e) {
                response.status(400);
                return Map.of("Message", "Bad Credentials");
            }
        },gson::toJson);
    }

}
