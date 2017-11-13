package pl.ratemyrestaurant.dto;

import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.model.Restaurant;
import pl.ratemyrestaurant.model.Thumb;

public class RatingDTO {

    private Ingredient ingredient;
    private Restaurant restaurant;
    private Thumb thumb;

    public RatingDTO() {
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Thumb getThumb() {
        return thumb;
    }

    public void setThumb(Thumb thumb) {
        this.thumb = thumb;
    }

    public static class Builder {

        private RatingDTO ratingDTO;

        public Builder() {
            this.ratingDTO = new RatingDTO();
        }

        public Builder setIngredient(Ingredient ingredient){
            ratingDTO.setIngredient(ingredient);
            return this;
        }

        public Builder setRestaurant(Restaurant restaurant) {
            ratingDTO.setRestaurant(restaurant);
            return this;
        }

        public Builder setThumb(Thumb thumb){
            ratingDTO.setThumb(thumb);
            return this;
        }

        public RatingDTO build(){
            return ratingDTO;
        }
    }
}
