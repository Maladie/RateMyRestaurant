package pl.ratemyrestaurant.dto;

import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.model.Thumb;

public class IngredientDTO {

    private String name;
    private boolean thumbsUp;
    private boolean thumbsDown;

    public IngredientDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(boolean thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public boolean isThumbsDown() {
        return thumbsDown;
    }

    public void setThumbsDown(boolean thumbsDown) {
        this.thumbsDown = thumbsDown;
    }

//    public Ingredient toIngredient(){
//        Ingredient ingredient = new Ingredient(name);
//        ingredient.setThumbsUp(thumbsUp);
//        ingredient.setThumbsDown(thumbsDown);
//        return ingredient;
//    }

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

        public IngredientDtoBuilder addThumbsDown(boolean thumbsDown){
            ingredientDTO.setThumbsDown(thumbsDown);
            return this;
        }

        public IngredientDTO build(){
            return ingredientDTO;
        }


    }
}
