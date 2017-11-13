package pl.ratemyrestaurant.mappers;

import pl.ratemyrestaurant.dto.RatingDTO;
import pl.ratemyrestaurant.model.Rating;

public class RatingToRatingDTOMapper {

    public static RatingDTO mapRatingToRatingDto(Rating rating){
        return new RatingDTO.Builder()
                .setIngredient(rating.getIngredient())
                .setRestaurant(rating.getRestaurant())
                .setThumb(rating.getThumb()).build();
    }

    public static Rating ratingDTOToRating(RatingDTO ratingDTO){
        return new Rating(ratingDTO.getRestaurant(), ratingDTO.getIngredient(), ratingDTO.getThumb());
    }
}
