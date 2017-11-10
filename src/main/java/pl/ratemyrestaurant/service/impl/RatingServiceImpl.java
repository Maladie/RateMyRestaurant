package pl.ratemyrestaurant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.model.Rating;
import pl.ratemyrestaurant.model.Restaurant;
import pl.ratemyrestaurant.model.Thumb;
import pl.ratemyrestaurant.repository.RatingRepository;
import pl.ratemyrestaurant.service.RatingService;

import java.util.Set;

@Service
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Set<Rating> retrieveRestaurantRatings(String restaurantId){
        return ratingRepository.findByRestaurant_Id(restaurantId);
    }

    public Rating createNewRating(Ingredient ingredient, Restaurant restaurant){
        Rating rating = new Rating(restaurant, ingredient, new Thumb());
        ratingRepository.save(rating);
        return rating;
    }
}
