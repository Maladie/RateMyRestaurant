package pl.ratemyrestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ratemyrestaurant.dto.IngredientDTO;
import pl.ratemyrestaurant.model.Info;
import pl.ratemyrestaurant.service.IngredientService;

import java.util.Set;

@RestController
@RequestMapping(value = "/ingredients")
public class IngredientController {

    private IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public Set<IngredientDTO> getAllIngredients(){
        return ingredientService.getAllIngredientsDTO();
    }

    @PostMapping()
    public ResponseEntity addIngredient(@RequestBody IngredientDTO ingredientDTO) {
      IngredientDTO addedIngredientDTO = ingredientService.addIngredient(ingredientDTO);
      //means already exists
      if(addedIngredientDTO == null) {
          Info info = new Info();
          info.setCode(422L);
          info.setDesc("Ingredient name already exists");
          info.setObject(ingredientDTO);
          return new ResponseEntity<>(info, HttpStatus.UNPROCESSABLE_ENTITY);
      }
      return new ResponseEntity<>(addedIngredientDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{ingredientId}")
    public ResponseEntity<IngredientDTO> getIngredientById(@PathVariable Long ingredientId) {
        IngredientDTO ingredientDTOById = ingredientService.getIngredientDTOById(ingredientId);
        if(ingredientDTOById != null) {
            return  new ResponseEntity<>(ingredientDTOById,  HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
