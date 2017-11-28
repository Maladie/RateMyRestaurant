package pl.ratemyrestaurant.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ratemyrestaurant.dto.FoodTypeDTO;
import pl.ratemyrestaurant.model.Info;
import pl.ratemyrestaurant.service.FoodTypeService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/foodTypes")
public class FoodTypeController {
    private static Logger logger = LogManager.getLogger(FoodTypeController.class);
    private FoodTypeService foodTypeService;

    @Autowired
    public FoodTypeController(FoodTypeService foodTypeService) {
        this.foodTypeService = foodTypeService;
    }

    @GetMapping
    public List<FoodTypeDTO> getAllFoodTypes() {
        return foodTypeService.getAllFoodTypesDTO();
    }

    @GetMapping("/{foodType}")
    public ResponseEntity getFoodTypeByName(@PathVariable String foodType) {
        FoodTypeDTO foodTypeDTO;
        try {
            foodTypeDTO = foodTypeService.getFoodTypeDTOByName(foodType);
        } catch (NoSuchElementException e) {
            logger.catching(e);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(foodTypeDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addNewFoodType(@RequestBody FoodTypeDTO foodTypeDTO) {
        FoodTypeDTO addedFoodTypeDTO = foodTypeService.addNewFoodType(foodTypeDTO);
        if(addedFoodTypeDTO == null) {
            Info info = new Info();
            info.setHttpStatusCode(422L);
            info.setDesc("FoodType already exists");
            info.setObject(foodTypeDTO);
            return new ResponseEntity<>(info, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(addedFoodTypeDTO, HttpStatus.CREATED);
    }
}
