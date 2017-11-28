package pl.ratemyrestaurant.factories;

import pl.ratemyrestaurant.dto.RatingDTO;
import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.model.Rating;
import pl.ratemyrestaurant.model.Restaurant;
import pl.ratemyrestaurant.model.Thumb;

public class RatingFactory {

    public RatingDTO getModelRatingDto(){
        return new RatingDTO.Builder().setIngredient(getModelIngredient())
                .setRestaurant(getModelRestaurant())
                .setThumb(getModelThumb())
                .build();
    }

    public Rating getModelRating(){

        return new Rating(getModelRestaurant(), getModelIngredient(), getModelThumb());
    }

    private Ingredient getModelIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("kasztan");
        ingredient.setId(12345);
        return ingredient;
    }

    private Restaurant getModelRestaurant(){

        return new Restaurant("restaurantStringID", "RestaurantName", null);
    }

    private Thumb getModelThumb(){
        Thumb thumb = new Thumb();
        thumb.setThumbsDown(2);
        thumb.setThumbsUp(10);
        return thumb;
    }
}
