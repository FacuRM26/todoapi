package tec.bd.Social;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reviews {
    private String  reviewId;
    private Date createdAt;
    private String reviewText;
    private String clientid;
    private String imageId;

    public Reviews(){

    }
    public Reviews(String reviewId, String reviewText, String clientid,Date createdAt) throws ParseException {
        this.reviewId = reviewId;
        this.createdAt=createdAt;
        this.reviewText = reviewText;
        this.clientid = clientid;

    }


    public String  getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public Date getFecha() {
        return createdAt;
    }

    public void setFecha(Date fecha) {
        this.createdAt = fecha;
    }

    public String getComentario() {
        return reviewText;
    }

    public void setComentario(String comentario) {
        this.reviewText = comentario;
    }

    public String getClientId() {
        return clientid;
    }

    public void setClientId(String clientId) {
        this.clientid = clientId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
