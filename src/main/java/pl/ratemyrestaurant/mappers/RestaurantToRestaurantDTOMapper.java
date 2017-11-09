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

//    public static Set<IngredientDTO> mapToDTOSet(Restaurant restaurant){
//        if(restaurant.getIngredients() == null){
//            return new HashSet<>();
//        }
//       return restaurant.getIngredients().stream().map(ingredient -> ingredient.toIngredientDto()).collect(Collectors.toSet());
//    }
//
//    public static Set<Ingredient> mapToIngredientSet(RestaurantDTO restaurantDTO){
//        if(restaurantDTO.getIngredients() == null){
//            return new HashSet<>();
//        }
//        return restaurantDTO.getIngredients().stream().map(ingredientDTO -> ingredientDTO.toIngredient()).collect(Collectors.toSet());
//    }
}
