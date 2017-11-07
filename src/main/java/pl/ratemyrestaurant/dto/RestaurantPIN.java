package pl.ratemyrestaurant.dto;

import pl.ratemyrestaurant.model.Location;
import pl.ratemyrestaurant.model.Restaurant;

public class RestaurantPIN {

    private String id;
    private String name;
    private Location location;

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
}
