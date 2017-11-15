package pl.ratemyrestaurant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.dto.IngredientDTO;
import pl.ratemyrestaurant.mappers.IngredientToIngredientDTOMapper;
import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.repository.IngredientRepository;
import pl.ratemyrestaurant.service.IngredientService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class IngredientServiceImpl implements IngredientService {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient addIngredient(IngredientDTO ingredientDTO){
        Ingredient ingredient;
        if(!getAllIngredients().stream().filter(i -> i.getName().equalsIgnoreCase(ingredientDTO.getName())).findFirst().isPresent()) {
            ingredient = IngredientToIngredientDTOMapper.mapIngredientDTOToIngredient(ingredientDTO);
            ingredientRepository.save(ingredient);
        } else {
            ingredient = ingredientRepository.findByName(ingredientDTO.getName());
        }

        return ingredient;
    }

    public Set<IngredientDTO> getAllIngredientsDTO() {
        List<Ingredient> allIngredients = getAllIngredients();
        Set<IngredientDTO> allIngredientsDTO = allIngredients.stream()
                                                        .map(ingredient -> IngredientToIngredientDTOMapper.mapIngredientToIngredientDTO(ingredient))
                                                        .collect(Collectors.toSet());
        return allIngredientsDTO;
    }

    @Override
    public IngredientDTO getIngredientDTOById(Long id) {
        Ingredient ingredient = ingredientRepository.findOne(id);
        IngredientDTO ingredientDTO = IngredientToIngredientDTOMapper.mapIngredientToIngredientDTO(ingredient);
        return ingredientDTO;
    }

    @Override
    public IngredientDTO getIngredientDTOByName(String ingredientName) {
        Ingredient ingredient = ingredientRepository.findByName(ingredientName);
        if(ingredient != null) {
            IngredientDTO ingredientDTO = IngredientToIngredientDTOMapper.mapIngredientToIngredientDTO(ingredient);
            return ingredientDTO;
        }
        return null;
    }

    private List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

}
