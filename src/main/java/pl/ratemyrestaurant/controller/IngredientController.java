package pl.ratemyrestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ratemyrestaurant.dto.IngredientDTO;
import pl.ratemyrestaurant.exception.APIValidationException;
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
    public Set<IngredientDTO> getAllIngredients() {
        return ingredientService.getAllIngredientsDTO();
    }

    @PostMapping
    public ResponseEntity addIngredient(@RequestBody IngredientDTO ingredientDTO) {
        try {
            IngredientDTO addedIngredientDTO = ingredientService.addIngredient(ingredientDTO);
            return new ResponseEntity<>(addedIngredientDTO, HttpStatus.CREATED);
        } catch (APIValidationException e) {
            System.out.println(e.getInfo().getDesc() + " " + e.getInfo().getInfoCode().getValue());
            HttpStatus status = e.getInfo().getHttpStatusCode() == 422L ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(e.getInfo(), status);
        }
    }

    @GetMapping(value = "/{ingredientId}")
    public ResponseEntity<IngredientDTO> getIngredientById(@PathVariable Long ingredientId) {
        IngredientDTO ingredientDTOById = ingredientService.getIngredientDTOById(ingredientId);
        if (ingredientDTOById != null) {
            return new ResponseEntity<>(ingredientDTOById, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
