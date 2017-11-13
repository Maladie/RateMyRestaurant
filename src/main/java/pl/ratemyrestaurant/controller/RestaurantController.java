package pl.ratemyrestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ratemyrestaurant.dto.RestaurantDTO;
import pl.ratemyrestaurant.service.RestaurantService;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

//    @GetMapping(value = "/{restaurantId}/ingredients", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<IngredientDTO> getIngredientsByThumbs(@PathVariable String restaurantId,
//                                                      @RequestParam (required = false) String orderBy){
//        return restaurantService.getIngredientsByThumbs(restaurantId, orderBy);
//    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantDTO> persistRestaurant(@RequestBody RestaurantDTO restaurantDTO){
        restaurantService.addOrUpdateRestaurant(restaurantDTO);
        return new ResponseEntity<>(restaurantDTO, HttpStatus.CREATED);
    }

}
