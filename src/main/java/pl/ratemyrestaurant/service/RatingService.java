package pl.ratemyrestaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.model.Rating;
import pl.ratemyrestaurant.model.Restaurant;
import pl.ratemyrestaurant.model.Thumb;
import pl.ratemyrestaurant.repository.RatingRepository;

import java.util.Set;

@Service
public class RatingService {

    private RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    Set<Rating> retrieveRestaurantRatings(String restaurantId){
        return ratingRepository.findByRestaurant_Id(restaurantId);
    }

    public Rating createNewRating(Ingredient ingredient, Restaurant restaurant){
        Rating rating = new Rating(restaurant, ingredient, new Thumb());
        ratingRepository.save(rating);
        return rating;
    }
}
