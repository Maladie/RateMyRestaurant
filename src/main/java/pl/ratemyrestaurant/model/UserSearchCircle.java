package pl.ratemyrestaurant.model;

public class UserSearchCircle {
    private Location location;
    private double radius;

    public UserSearchCircle(double lat, double lng, double radius) {
        this.location = new Location(lat, lng);
        this.radius = radius;
    }

    public double getLat() {
        return location.getLat();
    }

    public double getLng() {
        return location.getLng();
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
