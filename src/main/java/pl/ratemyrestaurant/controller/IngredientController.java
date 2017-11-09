package pl.ratemyrestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ratemyrestaurant.dto.IngredientDTO;
import pl.ratemyrestaurant.mappers.IngredientToIngredientDTOMapper;
import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.service.IngredientService;

import java.util.Set;

@RestController
@RequestMapping("/ingredients")
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

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IngredientDTO> addIngredient(@RequestBody IngredientDTO ingredientDTO) {
      Ingredient addedIngredient = ingredientService.addIngredient(ingredientDTO);
      IngredientDTO addedIngredientDTO = IngredientToIngredientDTOMapper.mapIngredientToIngredientDTO(addedIngredient);
      return new ResponseEntity<>(addedIngredientDTO, HttpStatus.CREATED);
    }
}
