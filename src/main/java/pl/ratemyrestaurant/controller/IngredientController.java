package pl.ratemyrestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ratemyrestaurant.dto.IngredientDTO;
import pl.ratemyrestaurant.service.impl.IngredientService;

import java.util.Set;

@RestController("/ingredients")
public class IngredientController {

    private IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public Set<IngredientDTO> getAllIngredients(){
        return ingredientService.getAllIngredients();
    }
}
