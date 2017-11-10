package pl.ratemyrestaurant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.dto.RatingDTO;
import pl.ratemyrestaurant.mappers.RatingToRatingDTOMapper;
import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.model.Rating;
import pl.ratemyrestaurant.model.Restaurant;
import pl.ratemyrestaurant.model.Thumb;
import pl.ratemyrestaurant.repository.RatingRepository;
import pl.ratemyrestaurant.service.RatingService;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public List<RatingDTO> retrieveRatingsOfIngredientInRestaurants(String ingredientName) {
        List<Rating> ratings = ratingRepository.findByIngredient_Name(ingredientName);
        return ratings.stream().sorted((r1, r2) -> {
            return Float.compare(countThumbPercentage(r2.getThumb()), countThumbPercentage(r1.getThumb()));
        }).map(r -> RatingToRatingDTOMapper.mapRatingToRatingDto(r)).collect(Collectors.toList());

    }

    private float countThumbPercentage(Thumb thumb){
        float a = (float)thumb.getThumbsDown();
        float b = (float)thumb.getThumbsDown();
        return a / (a+b);
    }
}
