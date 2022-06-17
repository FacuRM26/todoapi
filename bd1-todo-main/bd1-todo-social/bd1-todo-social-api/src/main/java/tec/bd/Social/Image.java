package tec.bd.Social;

import java.util.Date;

public class Image {
    private int imageId;
    private Date fecha;
    private String clientId;
    private String imageUrl;

    public Image(int imageId, Date fecha, String clientId, String imageUrl) {
        this.imageId = imageId;
        this.fecha = fecha;
        this.clientId = clientId;
        this.imageUrl = imageUrl;
    }
    public Image()  {

    }
    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
