package pl.ratemyrestaurant.mappers;

import pl.ratemyrestaurant.dto.RestaurantDTO;
import pl.ratemyrestaurant.model.Rating;
import pl.ratemyrestaurant.model.Restaurant;

import java.util.Set;

public class RestaurantToRestaurantDTOMapper {

    public static RestaurantDTO mapToRestaurantDto(Restaurant restaurant, Set<Rating> ratings){
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setId(restaurant.getId());
        restaurantDTO.setName(restaurant.getName());
        restaurantDTO.setLocation(restaurant.getLocation());
        restaurantDTO.setFoodTypes(restaurant.getFoodTypes());
        restaurantDTO.setIngredientRatings(ratings);
        return restaurantDTO;
    }

    public static Restaurant mapToRestaurant(RestaurantDTO restaurantDTO){
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantDTO.getId());
        restaurant.setName(restaurantDTO.getName());
        restaurant.setLocation(restaurantDTO.getLocation());
        restaurant.setFoodTypes(restaurantDTO.getFoodTypes());
        return restaurant;
    }

}
