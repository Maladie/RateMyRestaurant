package pl.ratemyrestaurant.mappers;

import pl.ratemyrestaurant.dto.IngredientDTO;
import pl.ratemyrestaurant.model.Ingredient;

public class IngredientToIngredientDTOMapper {

    public static IngredientDTO mapIngredientToIngredientDTO(Ingredient ingredient) {
        return new IngredientDTO.Builder()
                .setId(ingredient.getId())
                .setName(ingredient.getName())
                .build();
    }

    public static Ingredient mapIngredientDTOToIngredient(IngredientDTO ingredientDTO) {
        Ingredient ingredient = new Ingredient(ingredientDTO.getName());
        ingredient.setId(ingredientDTO.getId());
        return ingredient;
    }
}
