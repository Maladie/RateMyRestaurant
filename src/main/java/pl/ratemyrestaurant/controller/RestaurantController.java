package pl.ratemyrestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ratemyrestaurant.dto.RestaurantDTO;
import pl.ratemyrestaurant.service.RestaurantService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping(value = "/{restaurantId}")
    public RestaurantDTO getRestaurantById(@PathVariable String restaurantId) {
        return restaurantService.getRestaurantDTOById(restaurantId);
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantDTO> persistRestaurant(@RequestBody RestaurantDTO restaurantDTO){
        restaurantService.addOrUpdateRestaurant(restaurantDTO);
        return new ResponseEntity<>(restaurantDTO, HttpStatus.CREATED);
    }

    //nie działa
    @GetMapping(value = "/type/{foodType}")
    public Set<RestaurantDTO> getRestaurantsByFoodType(@PathVariable List<String> foodType) {
        Set<RestaurantDTO> restaurantsDTOByFoodType = restaurantService.getRestaurantsDTOByFoodType(foodType);
        return restaurantsDTOByFoodType;
    }
}
