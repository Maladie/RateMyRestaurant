package pl.ratemyrestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ratemyrestaurant.dto.RatingDTO;
import pl.ratemyrestaurant.dto.RestaurantPIN;
import pl.ratemyrestaurant.service.RatingService;

import java.util.List;

@RestController
@RequestMapping(value = "/rating", produces = MediaType.APPLICATION_JSON_VALUE)
public class RatingController {

    private RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/{ingredientName}")
    public List<RatingDTO> retrieveRatingsOfIngredientInRestaurants(@PathVariable String ingredientName,
                                                                    @RequestBody List<RestaurantPIN> restaurantsFound) {
        return ratingService.retrieveRatingsOfIngredientInRestaurants(ingredientName, restaurantsFound);
    }

    @PutMapping(value = "/{ratingID}")
    public ResponseEntity<RatingDTO> rateIngredientOfRatingID(@PathVariable Long ratingID, @RequestBody Boolean upVote) {
        RatingDTO rating = null;
        if (upVote != null) {
            rating = ratingService.rateIngredient(ratingID, upVote);
        }
        HttpStatus status = rating != null ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(rating, status);
    }
}
