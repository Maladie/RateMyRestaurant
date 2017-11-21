package pl.ratemyrestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ratemyrestaurant.dto.RatingDTO;
import pl.ratemyrestaurant.dto.RestaurantPIN;
import pl.ratemyrestaurant.model.Vote;
import pl.ratemyrestaurant.service.RatingService;

import java.util.List;

@RestController
@RequestMapping("/rating")
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

    @PostMapping(value = "/rate")
    public ResponseEntity<RatingDTO> rateIngredient(@RequestBody Vote vote) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        RatingDTO rating = new RatingDTO();
        if (vote != null) {
            rating = ratingService.addOrUpdateRating(vote);
            status = HttpStatus.ACCEPTED;
        }
        return new ResponseEntity<>(rating, status);
    }

    //? rateID instead of restaurantID, should be added to restaurant controller
    @PostMapping(value = "/{restaurantId}/rate")
    public ResponseEntity<RatingDTO> addIngredientRating(@PathVariable("restaurantId") String restaurantId, @RequestBody Vote vote){
       return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
