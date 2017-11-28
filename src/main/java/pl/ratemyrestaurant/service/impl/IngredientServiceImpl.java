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

    @Override
    public IngredientDTO addIngredient(IngredientDTO ingredientDTO){
        Ingredient ingredient = null;
        //TODO simplify
        if(ingredientDTO.getName() !=null && getAllIngredients().stream().noneMatch(i -> i.getName().equalsIgnoreCase(ingredientDTO.getName()))) {
            ingredient = IngredientToIngredientDTOMapper.mapIngredientDTOToIngredient(ingredientDTO);
            ingredientRepository.save(ingredient);
        }
        return ingredient != null ? IngredientToIngredientDTOMapper.mapIngredientToIngredientDTO(ingredient) : null;
    }

    @Override
    public Set<IngredientDTO> getAllIngredientsDTO() {
        List<Ingredient> allIngredients = getAllIngredients();
        return allIngredients
                .stream()
                .map(IngredientToIngredientDTOMapper::mapIngredientToIngredientDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public IngredientDTO getIngredientDTOById(Long id) {
        Ingredient ingredient = ingredientRepository.findOne(id);
        return ingredient != null ? IngredientToIngredientDTOMapper.mapIngredientToIngredientDTO(ingredient) : null;
    }

    private List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

}
