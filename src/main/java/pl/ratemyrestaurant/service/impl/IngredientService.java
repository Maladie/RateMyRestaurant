package pl.ratemyrestaurant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.dto.IngredientDTO;
import pl.ratemyrestaurant.mappers.IngredientToIngredientDTOMapper;
import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.repository.IngredientRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class IngredientService {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient addIngredient(IngredientDTO ingredientDTO){
        Ingredient ingredient = IngredientToIngredientDTOMapper.mapIngredientDTOToIngredient(ingredientDTO);
        ingredientRepository.save(ingredient);
        return ingredient;
    }

    public Set<IngredientDTO> getAllIngredients() {
        List<Ingredient> allIngredients = ingredientRepository.findAll();
        Set<IngredientDTO> allIngredientsDTO = allIngredients.stream()
                                                        .map(ingredient -> IngredientToIngredientDTOMapper.mapIngredientToIngredientDTO(ingredient))
                                                        .collect(Collectors.toSet());
        return allIngredientsDTO;
    }

}
