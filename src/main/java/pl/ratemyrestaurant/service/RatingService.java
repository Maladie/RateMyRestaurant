package pl.ratemyrestaurant.service;

import pl.ratemyrestaurant.dto.RatingDTO;
import pl.ratemyrestaurant.dto.RestaurantPIN;
import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.model.Rating;
import pl.ratemyrestaurant.model.Restaurant;

import java.util.List;
import java.util.Set;

public interface RatingService {

    Set<Rating> retrieveRestaurantRatings(String restaurantId);
    Rating createNewRating(Ingredient ingredient, Restaurant restaurant);
    List<RatingDTO> retrieveRatingsOfIngredientInRestaurants(String ingredientName, List<RestaurantPIN> pins);
}
