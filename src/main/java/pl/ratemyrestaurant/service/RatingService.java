package pl.ratemyrestaurant.service;

import pl.ratemyrestaurant.dto.RatingDTO;
import pl.ratemyrestaurant.model.Rating;
import pl.ratemyrestaurant.model.Vote;

import java.util.Set;

public interface RatingService {

    Set<Rating> retrieveRestaurantRatings(String restaurantId);
    RatingDTO addOrUpdateRating(Vote vote);
    RatingDTO addNewIngredientRating(String restaurantID, Long ingredientID);
    RatingDTO rateIngredient(String restaurantID, Long ingredientID, boolean upVote);
    RatingDTO rateIngredient(Long ratingID, boolean upVote);
}
