package pl.ratemyrestaurant.dto;

public class IngredientDTO {

    private String name;

    public IngredientDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Builder {

        private IngredientDTO ingredientDTO;

        public Builder() {
            ingredientDTO = new IngredientDTO();
        }

        public Builder setName(String name){
            ingredientDTO.setName(name);
            return this;
        }

        public IngredientDTO build(){
            return ingredientDTO;
        }
    }
}
