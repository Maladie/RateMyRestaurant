package pl.sdacademy.model;

/**
 * Created by RENT on 2017-11-03.
 */
public class UserSearchCircle {
    double lat;
    double lng;
    double radius;

    public UserSearchCircle(double lat, double lng, double radius) {
        this.lat = lat;
        this.lng = lng;
        this.radius = radius;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
