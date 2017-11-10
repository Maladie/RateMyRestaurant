package pl.ratemyrestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ratemyrestaurant.dto.IngredientDTO;
import pl.ratemyrestaurant.mappers.IngredientToIngredientDTOMapper;
import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.service.impl.IngredientServiceImpl;

import java.util.Set;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private IngredientServiceImpl ingredientServiceImpl;

    @Autowired
    public IngredientController(IngredientServiceImpl ingredientServiceImpl) {
        this.ingredientServiceImpl = ingredientServiceImpl;
    }

    @GetMapping
    public Set<IngredientDTO> getAllIngredients(){
        return ingredientServiceImpl.getAllIngredientsDTO();
    }

    @PostMapping(value = "/add")
    public ResponseEntity<IngredientDTO> addIngredient(@RequestBody IngredientDTO ingredientDTO) {
      Ingredient addedIngredient = ingredientServiceImpl.addIngredient(ingredientDTO);
      IngredientDTO addedIngredientDTO = IngredientToIngredientDTOMapper.mapIngredientToIngredientDTO(addedIngredient);
      return new ResponseEntity<>(addedIngredientDTO, HttpStatus.CREATED);
    }
}
