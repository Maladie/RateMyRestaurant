package pl.ratemyrestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ratemyrestaurant.dto.RatingDTO;
import pl.ratemyrestaurant.service.RatingService;

@RestController
@RequestMapping(value = "/rating")
public class RatingController {

    private RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PutMapping(value = "/{ratingID}")
    public ResponseEntity<RatingDTO> rateIngredientByRatingID(@PathVariable Long ratingID, @RequestBody Boolean upVote) {
        RatingDTO rating = null;
        if (upVote != null && ratingID != null) {
            rating = ratingService.rateIngredient(ratingID, upVote);
        }
        HttpStatus status = rating != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(rating, status);
    }
}
