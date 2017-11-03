package pl.ratemyrestaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.repository.IngredientRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class IngredientService {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient persistIngredient(Ingredient i){
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

    public Set<Ingredient> getAllIngredients(){
        return new HashSet<Ingredient>(ingredientRepository.findAll());
    }

    public Ingredient getIngredientById(long id){
        return ingredientRepository.findById(id);
    }
}
