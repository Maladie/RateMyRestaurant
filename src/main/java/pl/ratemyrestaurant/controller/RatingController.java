package pl.ratemyrestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ratemyrestaurant.dto.RatingDTO;
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

    @GetMapping("/{ingredientName}")
    public List<RatingDTO> retrieveRatingsOfIngredientInRestaurants(@PathVariable String ingredientName){
         return ratingService.retrieveRatingsOfIngredientInRestaurants(ingredientName);
    }

}
