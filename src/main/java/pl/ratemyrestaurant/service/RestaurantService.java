package pl.ratemyrestaurant.service;

import pl.ratemyrestaurant.dto.RestaurantDTO;
import pl.ratemyrestaurant.dto.RestaurantPIN;
import pl.ratemyrestaurant.model.UserSearchCircle;
import se.walkercrou.places.Place;

import java.util.List;
import java.util.Set;

public interface RestaurantService {

    Set<RestaurantPIN> retrieveRestaurantsInRadius(UserSearchCircle userSearchCircle);
    RestaurantPIN mapPlaceToRestaurantDto(Place place);
    void addOrUpdateRestaurant(RestaurantDTO restaurantDTO);
    RestaurantDTO getOrRetrieveRestaurantDTOByID(String placeId);
    RestaurantDTO getRestaurantDTOById(String id);
    RestaurantPIN getRestaurantPINById(String id);
    Set<RestaurantDTO> getRestaurantsDTOByFoodType(String foodType);
}
