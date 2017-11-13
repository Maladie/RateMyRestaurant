package pl.ratemyrestaurant.service;

import pl.ratemyrestaurant.dto.IngredientDTO;
import pl.ratemyrestaurant.model.Ingredient;

import java.util.Set;

public interface IngredientService {

    Ingredient addIngredient(IngredientDTO ingredientDTO);
    Set<IngredientDTO> getAllIngredientsDTO();
    IngredientDTO getIngredientDTOById(Long id);
}
