package pl.ratemyrestaurant.mappers;

import pl.ratemyrestaurant.dto.IngredientDTO;
import pl.ratemyrestaurant.model.Ingredient;

public class IngredientToIngredientDTOMapper {

    public static IngredientDTO mapIngredientToIngredientDTO(Ingredient ingredient) {
        IngredientDTO ingredientDTO = new IngredientDTO.Builder()
                                                        .setName(ingredient.getName())
                                                        .build();
        return ingredientDTO;
    }

    public static Ingredient mapIngredientDTOToIngredient(IngredientDTO ingredientDTO) {
        Ingredient ingredient = new Ingredient(ingredientDTO.getName());
        return ingredient;
    }
}
