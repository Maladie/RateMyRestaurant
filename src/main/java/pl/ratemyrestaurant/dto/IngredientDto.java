package pl.ratemyrestaurant.dto;

public class IngredientDto {

    private String name;
    private int thumbsUp;
    private int thumbsDown;

    public IngredientDto() {
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

    public static class IngredientDtoBuilder{

        private IngredientDto ingredientDto;

        public IngredientDtoBuilder() {
            ingredientDto = new IngredientDto();
        }

        public IngredientDtoBuilder addName(String name){
            ingredientDto.setName(name);
            return this;
        }

        public IngredientDtoBuilder addThumbsUp(int thumbsUp){
            ingredientDto.setThumbsUp(thumbsUp);
            return this;
        }

        public IngredientDtoBuilder addThumbsDown(int thumbsDown){
            ingredientDto.setThumbsDown(thumbsDown);
            return this;
        }

        public IngredientDto build(){
            return ingredientDto;
        }


    }
}
