package pl.ratemyrestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ratemyrestaurant.dto.RatingDTO;
import pl.ratemyrestaurant.dto.RestaurantDTO;
import pl.ratemyrestaurant.dto.RestaurantPIN;
import pl.ratemyrestaurant.model.UserSearchCircle;
import pl.ratemyrestaurant.model.Vote;
import pl.ratemyrestaurant.service.RestaurantService;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    private RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    //temp. disabled
//    @GetMapping(value = "/{restaurantID}")
//    public RestaurantDTO getRestaurantById(@PathVariable String restaurantId) {
//        return restaurantService.getRestaurantDTOById(restaurantId);
//    }

    //? unused currently
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantDTO> persistRestaurant(@RequestBody RestaurantDTO restaurantDTO){
        restaurantService.addOrUpdateRestaurant(restaurantDTO);
        return new ResponseEntity<>(restaurantDTO, HttpStatus.CREATED);
    }

    //? Method to refactor
    @GetMapping(value = "/type/{foodType}")
    public Set<RestaurantDTO> getRestaurantsByFoodType(@PathVariable String foodType) {
        return restaurantService.getRestaurantsDTOByFoodType(foodType);
    }

    //TODO refactor
    //? Refactor idea
    @GetMapping(value = "/searchInRadius/{foodType}")
    public Set<RestaurantDTO> getRestaurantsInRadiusByFoodType(@PathVariable String foodType){
        return new HashSet<>(); //TODO
    }

    //? Refactor response type from pin to regular restaurant
    //TODO add parameters validator here or in service
    @GetMapping(value = "/areaSearch")
    public Set<RestaurantPIN> getRestaurantInRadius(@RequestParam double lng, @RequestParam double lat, @RequestParam double radius, @RequestParam(required = false) String type){
        UserSearchCircle userSearchCircle = new UserSearchCircle(lat, lng, radius);
        return restaurantService.retrieveRestaurantsInRadius(userSearchCircle);
    }

    @GetMapping(value = "/{restaurantID}")
    public RestaurantDTO getRestaurantDetails(@PathVariable String restaurantID){
        return restaurantService.getOrRetrieveRestaurantDTOByID(restaurantID);
    }

    //TODO impl.
    @PostMapping(value = "/{restaurantId}/rate")
    public ResponseEntity<RatingDTO> addIngredientRating(@PathVariable("restaurantId") String restaurantId, @RequestBody Vote vote){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
