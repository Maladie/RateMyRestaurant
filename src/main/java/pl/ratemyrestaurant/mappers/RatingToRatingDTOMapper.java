package pl.ratemyrestaurant.mappers;

import pl.ratemyrestaurant.dto.RatingDTO;
import pl.ratemyrestaurant.model.Rating;

public class RatingToRatingDTOMapper {

    /**
     * Maps Rating to RatingDTO
     * @param rating Rating to map to RatingDTO
     * @return null if rating parameter was null otherwise RatingDTO
     */
    public static RatingDTO mapRatingToRatingDto(Rating rating){
        if(rating == null) {
            return null;
        }
        return new RatingDTO.Builder()
                .setIngredient(rating.getIngredient())
                .setRestaurant(rating.getRestaurant())
                .setThumb(rating.getThumb()).build();
    }

    public static Rating ratingDTOToRating(RatingDTO ratingDTO){
        return new Rating(ratingDTO.getRestaurant(), ratingDTO.getIngredient(), ratingDTO.getThumb());
    }
}
