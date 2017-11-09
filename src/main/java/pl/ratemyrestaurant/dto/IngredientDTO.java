package pl.ratemyrestaurant.dto;

import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.model.Thumb;

public class IngredientDTO {

    private String name;
    private int thumbsUp;
    private int thumbsDown;

    public IngredientDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(int thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public int getThumbsDown() {
        return thumbsDown;
    }

    public void setThumbsDown(int thumbsDown) {
        this.thumbsDown = thumbsDown;
    }

    public Ingredient toIngredient(){
        Ingredient ingredient = new Ingredient(name);
//        ingredient.setThumbsUp(thumbsUp);
//        ingredient.setThumbsDown(thumbsDown);
        return ingredient;
    }

    public static class IngredientDtoBuilder{

        private IngredientDTO ingredientDTO;

        public IngredientDtoBuilder() {
            ingredientDTO = new IngredientDTO();
        }

        public IngredientDtoBuilder addName(String name){
            ingredientDTO.setName(name);
            return this;
        }

        public IngredientDtoBuilder addThumbsUp(int thumbsUp){
            ingredientDTO.setThumbsUp(thumbsUp);
            return this;
        }

        public IngredientDtoBuilder addThumbsDown(int thumbsDown){
            ingredientDTO.setThumbsDown(thumbsDown);
            return this;
        }

        public IngredientDTO build(){
            return ingredientDTO;
        }


    }
}
