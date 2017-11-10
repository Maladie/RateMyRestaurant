package pl.ratemyrestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ratemyrestaurant.dto.RestaurantDTO;
import pl.ratemyrestaurant.service.impl.RestaurantServiceImpl;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private RestaurantServiceImpl restaurantServiceImpl;

    @Autowired
    public RestaurantController(RestaurantServiceImpl restaurantServiceImpl) {
        this.restaurantServiceImpl = restaurantServiceImpl;
    }

//    @GetMapping(value = "/{restaurantId}/ingredients", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<IngredientDTO> getIngredientsByThumbs(@PathVariable String restaurantId,
//                                                      @RequestParam (required = false) String orderBy){
//        return restaurantService.getIngredientsByThumbs(restaurantId, orderBy);
//    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantDTO> persistRestaurant(@RequestBody RestaurantDTO restaurantDTO){
        restaurantServiceImpl.addOrUpdateRestaurant(restaurantDTO);
        return new ResponseEntity<>(restaurantDTO, HttpStatus.CREATED);
    }

//    @GetMapping("/restaurants/ingredient/{ingredientName}")
//    public List<RestaurantDTO> getAllRestaurantsContainingIngredient(@PathVariable String ingredientName){
//        return restaurantService.getRestaurantsContainingIngredient(ingredientName);
//    }
}
