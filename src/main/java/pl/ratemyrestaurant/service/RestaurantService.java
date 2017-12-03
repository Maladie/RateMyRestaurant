package pl.ratemyrestaurant.service;

import pl.ratemyrestaurant.dto.FoodTypeDTO;
import pl.ratemyrestaurant.dto.RestaurantDTO;
import pl.ratemyrestaurant.dto.RestaurantPIN;
import pl.ratemyrestaurant.model.UserSearchCircle;
import se.walkercrou.places.Place;

import java.util.Set;

public interface RestaurantService {

    Set<RestaurantPIN> retrieveRestaurantsInRadius(UserSearchCircle userSearchCircle);
    Set<RestaurantPIN> retrieveRestaurantsInRadiusWithFoodType(UserSearchCircle userSearchCircle, String foodTypeName);
    RestaurantPIN mapPlaceToRestaurantDto(Place place);
    void addOrUpdateRestaurant(RestaurantDTO restaurantDTO);
    RestaurantDTO getOrRetrieveRestaurantDTOByID(String placeId);
    RestaurantDTO getRestaurantDTOById(String id);
    boolean isRestaurantInDB(String restaurantID);

    RestaurantDTO addFoodType(String restaurantID, FoodTypeDTO foodTypeDTO);
}
