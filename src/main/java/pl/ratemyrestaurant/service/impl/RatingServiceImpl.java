package pl.ratemyrestaurant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.dto.RatingDTO;
import pl.ratemyrestaurant.dto.RestaurantPIN;
import pl.ratemyrestaurant.mappers.RatingToRatingDTOMapper;
import pl.ratemyrestaurant.model.*;
import pl.ratemyrestaurant.repository.IngredientRepository;
import pl.ratemyrestaurant.repository.RatingRepository;
import pl.ratemyrestaurant.repository.RestaurantRepository;
import pl.ratemyrestaurant.service.RatingService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;
    private RestaurantRepository restaurantRepository;
    private IngredientRepository ingredientRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, RestaurantRepository restaurantRepository, IngredientRepository ingredientRepository) {
        this.ratingRepository = ratingRepository;
        this.restaurantRepository = restaurantRepository;
        this.ingredientRepository = ingredientRepository;
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
    public List<RatingDTO> retrieveRatingsOfIngredientInRestaurants(String ingredientName, List<RestaurantPIN> pins) {

        List<String> restaurantIDs = pins.stream().map(p -> p.getId()).collect(Collectors.toList());
        Set<Rating> ratings = restaurantIDs.stream()
                .map(p-> ratingRepository
                        .findByRestaurant_Id(p)
                        .stream()
                        .filter(x->x.getIngredient().getName()==ingredientName)
                        .findFirst().get()).collect(Collectors.toSet());

        return ratings.stream().sorted((r1, r2) -> {
            return Float.compare(countThumbPercentage(r2.getThumb()), countThumbPercentage(r1.getThumb()));
        }).map(r -> RatingToRatingDTOMapper.mapRatingToRatingDto(r)).collect(Collectors.toList());

    }

    @Override
    public RatingDTO addOrUpdateRating(Vote vote) {
        Rating rating = ratingRepository.findByRestaurant_IdAndIngredient_Id(vote.getRestaurantId(), vote.getIngredientId());
        if(rating == null) {
            Restaurant restaurant = restaurantRepository.findOne(vote.getRestaurantId());
            Ingredient ingredient = ingredientRepository.findOne(vote.getIngredientId());
            rating = new Rating(restaurant, ingredient, new Thumb());
        }
        rating.getThumb().rate(vote.isGood());
        ratingRepository.save(rating);
        return RatingToRatingDTOMapper.mapRatingToRatingDto(rating);
    }

    private float countThumbPercentage(Thumb thumb){
        float a = (float)thumb.getThumbsDown();
        float b = (float)thumb.getThumbsDown();
        return a / (a+b);
    }
}
