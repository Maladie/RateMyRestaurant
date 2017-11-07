package pl.ratemyrestaurant.mappers;

import pl.ratemyrestaurant.dto.RestaurantPIN;
import pl.ratemyrestaurant.model.Restaurant;

public class RestaurantToPinMapper {

    public static RestaurantPIN mapRestaurantToPin(Restaurant restaurant){
        RestaurantPIN restaurantPIN = new RestaurantPIN();
        restaurantPIN.setId(restaurant.getId());
        restaurantPIN.setLocation(restaurant.getLocation());
        restaurantPIN.setName(restaurant.getName());
        return restaurantPIN;
    }
}
