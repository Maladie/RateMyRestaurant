package pl.ratemyrestaurant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.dto.RatingDTO;
import pl.ratemyrestaurant.dto.RestaurantDTO;
import pl.ratemyrestaurant.mappers.RatingToRatingDTOMapper;
import pl.ratemyrestaurant.model.*;
import pl.ratemyrestaurant.repository.IngredientRepository;
import pl.ratemyrestaurant.repository.RatingRepository;
import pl.ratemyrestaurant.repository.RestaurantRepository;
import pl.ratemyrestaurant.service.RatingService;
import pl.ratemyrestaurant.service.RestaurantService;

import java.util.Set;

@Service
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;
    private RestaurantRepository restaurantRepository;
    private IngredientRepository ingredientRepository;
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, RestaurantRepository restaurantRepository, IngredientRepository ingredientRepository) {
        this.ratingRepository = ratingRepository;
        this.restaurantRepository = restaurantRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public Set<Rating> retrieveRestaurantRatings(String restaurantId) {
        return ratingRepository.findByRestaurant_Id(restaurantId);
    }

    // TODO  refactor

//    @Override
//    public List<RatingDTO> retrieveRatingsOfIngredientInRestaurants(String ingredientName, List<RestaurantPIN> pins) {
//
//        List<String> restaurantIDs = pins.stream().map(RestaurantPIN::getId).collect(Collectors.toList());
//        Set<Rating> ratings = restaurantIDs.stream()
//                .map(p -> ratingRepository
//                        .findByRestaurant_Id(p)
//                        .stream()
//                        .filter(x -> x.getIngredient().getName().equals(ingredientName))
//                        .findFirst()
//                        .get()
//                ).collect(Collectors.toSet());
//
//        return ratings
//                .stream()
//                .sorted((r1, r2) ->
//                                Float.compare(countThumbPercentage(r2.getThumb()), countThumbPercentage(r1.getThumb()))
//                ).map(RatingToRatingDTOMapper::mapRatingToRatingDto)
//                .collect(Collectors.toList());
//    }

    @Override
    public RatingDTO addOrUpdateRating(Vote vote) {
        Rating rating = ratingRepository.findByRestaurant_IdAndIngredient_Id(vote.getRestaurantId(), vote.getIngredientId());
        if (rating == null) {
            if (vote.getRestaurantId() == null) {
                createRestaurant(vote.getRestaurantId());
            }
            Restaurant restaurant = restaurantRepository.findOne(vote.getRestaurantId());
            Ingredient ingredient = ingredientRepository.findOne(vote.getIngredientId());
            rating = new Rating();
            rating.setRestaurant(restaurant);
            rating.setIngredient(ingredient);
            rating.setThumb(new Thumb());
        }
        rating.getThumb().rate(vote.isGood());
        ratingRepository.save(rating);
        return RatingToRatingDTOMapper.mapRatingToRatingDto(rating);
    }

    @Override
    public RatingDTO addNewIngredientRating(String restaurantID, Long ingredientID) {
        Restaurant restaurant = restaurantRepository.findOne(restaurantID);
        if(restaurant == null) {
            //create if not exists in DB
            restaurant = createRestaurant(restaurantID);
        }
        Ingredient ingredient = ingredientRepository.findOne(ingredientID);
        Rating rating =  new Rating();
        rating.setRestaurant(restaurant);
        rating.setIngredient(ingredient);
        rating.setThumb(new Thumb());
        ratingRepository.save(rating);
        return RatingToRatingDTOMapper.mapRatingToRatingDto(rating);
    }

    @Override
    public RatingDTO rateIngredient(String restaurantID, Long ingredientID, boolean upVote) {
        Rating rating = ratingRepository.findByRestaurant_IdAndIngredient_Id(restaurantID, ingredientID);
        if(rating != null) {
            rating.getThumb().rate(upVote);
            ratingRepository.save(rating);
        }
        return RatingToRatingDTOMapper.mapRatingToRatingDto(rating);
    }

    @Override
    public RatingDTO rateIngredient(Long ratingID, boolean upVote) {
        Rating rating = ratingRepository.findById(ratingID);
        if(rating != null) {
            rating.getThumb().rate(upVote);
            ratingRepository.save(rating);
        }
        return RatingToRatingDTOMapper.mapRatingToRatingDto(rating);
    }

    // currently not used, waiting for ^^ retrieveRatingsOfIngredientInRestaurants() refactoring

//    private float countThumbPercentage(Thumb thumb) {
//        float a = (float) thumb.getThumbsDown();
//        float b = (float) thumb.getThumbsDown();
//        return a / (a + b);
//    }

    private Restaurant createRestaurant(String restaurantID) {
        RestaurantDTO restaurantDTO = restaurantService.getOrRetrieveRestaurantDTOByID(restaurantID);
        restaurantService.addOrUpdateRestaurant(restaurantDTO);
        return restaurantRepository.findOne(restaurantID);
    }
}
