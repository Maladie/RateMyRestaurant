package pl.ratemyrestaurant.service;

import pl.ratemyrestaurant.dto.IngredientDTO;

import java.util.Set;

public interface IngredientService {

    IngredientDTO addIngredient(IngredientDTO ingredientDTO);
    Set<IngredientDTO> getAllIngredientsDTO();
    IngredientDTO getIngredientDTOById(Long id);
}
