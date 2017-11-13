package pl.ratemyrestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.ratemyrestaurant.dto.RatingDTO;
import pl.ratemyrestaurant.dto.RestaurantPIN;
import pl.ratemyrestaurant.model.Rating;
import pl.ratemyrestaurant.model.Restaurant;
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
                                                                    @RequestBody List<RestaurantPIN> restaurantsFound){
         return ratingService.retrieveRatingsOfIngredientInRestaurants(ingredientName, restaurantsFound);
    }

    @PostMapping
    public RatingDTO rateIngredient(@RequestParam Long id, @RequestParam boolean good){
        return ratingService.rateIngredient(id, good);
    }
}
