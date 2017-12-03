package pl.ratemyrestaurant.dto;

public class IngredientDTO {
    private Long id;
    private String name;

    public IngredientDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

        public Builder setId(Long id) {
            ingredientDTO.setId(id);
            return this;
        }

        public Builder setName(String name) {
            ingredientDTO.setName(name);
            return this;
        }

        public IngredientDTO build() {
            return ingredientDTO;
        }
    }
}
