package pl.ratemyrestaurant.dto;

import pl.ratemyrestaurant.model.Location;
import pl.ratemyrestaurant.model.Restaurant;

public class RestaurantPIN {

    private String id;
    private String name;
    private Location location;

    public RestaurantPIN() {
    }

    public RestaurantPIN(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.location = restaurant.getLocation();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
