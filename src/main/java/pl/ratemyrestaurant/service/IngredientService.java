package pl.ratemyrestaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.dto.IngredientDTO;
import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.repository.IngredientRepository;

import javax.transaction.Transactional;
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
        Ingredient i = ingredientDTO.toIngredient();
        ingredientRepository.save(i);
        return i;
    }

    public Ingredient thumbUp(long id){
        Ingredient i = ingredientRepository.findById(id);
        i.giveThumbUp();
        ingredientRepository.save(i);
        return i;
    }

    public Ingredient thumbDown(long id){
        Ingredient i = ingredientRepository.findById(id);
        i.giveThumbDown();
        ingredientRepository.save(i);
        return i;
    }

    public Set<IngredientDTO> getAllIngredientDTOs(){
        return ingredientRepository
                .findAll()
                .stream()
                .map(i -> i.toIngredientDto())
                .collect(Collectors.toSet());
    }

    public IngredientDTO getIngredientDTOById(long id){
        return ingredientRepository.findById(id).toIngredientDto();
    }
}
