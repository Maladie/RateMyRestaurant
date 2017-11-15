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

    @PostMapping
    public ResponseEntity<RatingDTO> rateIngredient(@RequestBody Vote vote) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        RatingDTO rating = new RatingDTO();
        if (vote != null && !vote.getId().equals("")) {
            rating = ratingService.rateIngredient(Long.parseLong(vote.getId()), vote.isGood());
            if (rating != null) {
                status = HttpStatus.ACCEPTED;
            }
        }
        return new ResponseEntity<>(rating, status);
    }
}
