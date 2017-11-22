package pl.ratemyrestaurant.service;

import pl.ratemyrestaurant.dto.IngredientDTO;
import pl.ratemyrestaurant.dto.RatingDTO;
import pl.ratemyrestaurant.dto.RestaurantPIN;
import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.model.Rating;
import pl.ratemyrestaurant.model.Restaurant;
import pl.ratemyrestaurant.model.Vote;

import java.util.List;
import java.util.Set;

public interface RatingService {

    Set<Rating> retrieveRestaurantRatings(String restaurantId);
    List<RatingDTO> retrieveRatingsOfIngredientInRestaurants(String ingredientName, List<RestaurantPIN> pins);
    RatingDTO addOrUpdateRating(Vote vote);
    RatingDTO addNewIngredientRating(String restaurantID, Long ingredientID);
    RatingDTO rateIngredient(String restaurantID, Long ingredientID, boolean upVote);
    RatingDTO rateIngredient(Long ratingID, boolean upVote);
}
