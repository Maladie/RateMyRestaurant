package pl.ratemyrestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ratemyrestaurant.dto.RestaurantDTO;
import pl.ratemyrestaurant.model.UserSearchCircle;
import pl.ratemyrestaurant.service.RestaurantService;

import java.util.Set;

@RestController("/places")
public class PlacesController {

    private RestaurantService restaurantService;

    @Autowired
    public PlacesController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping(value = "/allInRadius",produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<RestaurantDTO> getPlacesInRadius(@RequestParam double lng, @RequestParam double lat, @RequestParam double radius, @RequestParam(required = false) String type){
        UserSearchCircle userSearchCircle = new UserSearchCircle(lng, lat, radius);
        Set<RestaurantDTO> restaurantDTOSet = restaurantService.retrieveRestaurantsInRadius(userSearchCircle);
        return restaurantDTOSet;
    }

}
