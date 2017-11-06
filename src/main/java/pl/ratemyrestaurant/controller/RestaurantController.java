package pl.ratemyrestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ratemyrestaurant.dto.IngredientDTO;
import pl.ratemyrestaurant.dto.RestaurantDTO;

import pl.ratemyrestaurant.dto.RestaurantPIN;

import pl.ratemyrestaurant.model.Ingredient;

import pl.ratemyrestaurant.service.RestaurantService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class RestaurantController {

    private RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/{restaurantId}")
    public RestaurantDTO getRestaurantById(@PathVariable String restaurantId) {
       return restaurantService.getRestaurantDTOById(restaurantId);
    }


    @GetMapping("getPin/{restaurantId}")
    public RestaurantPIN getRestaurantPINById(@PathVariable String restaurantId) {
        return restaurantService.getRestaurantPINById(restaurantId);
    }
    @GetMapping("/{restaurantId}/ingredients")
    public List<IngredientDTO> getIngredientsByThumbs(@PathVariable String restaurantId,
                                                      @RequestParam (required = false) String orderBy){
        return restaurantService.getIngredientsByThumbs(restaurantId, orderBy);
    }
}
