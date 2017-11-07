package pl.ratemyrestaurant.mappers;

import pl.ratemyrestaurant.dto.RestaurantDTO;
import pl.ratemyrestaurant.model.Restaurant;

public class RestaurantToRestaurantDTOMapper {

    public static RestaurantDTO mapToRestaurantDto(Restaurant restaurant){
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setId(restaurant.getId());
        restaurantDTO.setName(restaurant.getName());
        restaurantDTO.setLocation(restaurant.getLocation());
        restaurantDTO.setFoodTypes(restaurant.getFoodTypes());
        restaurantDTO.setIngredients(restaurant.getIngredients());
        return restaurantDTO;
    }

    public static Restaurant mapToRestaurant(RestaurantDTO restaurantDTO){
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantDTO.getId());
        restaurant.setName(restaurantDTO.getName());
        restaurant.setLocation(restaurantDTO.getLocation());
        restaurant.setIngredients(restaurantDTO.getIngredients());
        restaurant.setFoodTypes(restaurantDTO.getFoodTypes());
        return restaurant;
    }
}
