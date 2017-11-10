package pl.ratemyrestaurant.service;

import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.model.Rating;
import pl.ratemyrestaurant.model.Restaurant;

import java.util.Set;

public interface RatingService {

    Set<Rating> retrieveRestaurantRatings(String restaurantId);
    Rating createNewRating(Ingredient ingredient, Restaurant restaurant);
}
