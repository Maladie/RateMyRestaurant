package pl.ratemyrestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.ratemyrestaurant.dto.IngredientDTO;
import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.service.IngredientService;

import java.util.Set;

@RestController
public class IngredientController {

    private IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/ingredients")
    public Set<IngredientDTO> getAllIngredients(){
        return ingredientService.getAllIngredientDTOs();
    }

    @GetMapping("/ingredients/{ingredientId}")
    public IngredientDTO getById(@PathVariable Long ingredientId){
        return ingredientService.getIngredientDTOById(ingredientId);
    }
}
