package pl.ratemyrestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ratemyrestaurant.dto.FoodTypeDTO;
import pl.ratemyrestaurant.mappers.FoodTypeToFoodTypeDTOMapper;
import pl.ratemyrestaurant.model.FoodType;
import pl.ratemyrestaurant.service.FoodTypeService;

import java.util.List;

@RestController
@RequestMapping("/foodType")
public class FoodTypeController {

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
    public FoodTypeDTO getFoodTypeByName(@PathVariable String foodType) {
        return foodTypeService.getFoodTypeDTOByName(foodType);
    }

    //TODO ? empty endpoint instead of /add should be enough
    @PostMapping("/add")
    public ResponseEntity<FoodTypeDTO> addNewFoodType(@RequestBody FoodTypeDTO foodTypeDTO) {
        FoodType foodType = foodTypeService.addNewFoodType(foodTypeDTO);
        FoodTypeDTO addedFoodTypeDTO = FoodTypeToFoodTypeDTOMapper.mapFoodTypeToFoodTypeDTO(foodType);
        return new ResponseEntity<>(addedFoodTypeDTO, HttpStatus.CREATED);
    }
}
