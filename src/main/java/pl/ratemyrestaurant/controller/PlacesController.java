package pl.ratemyrestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.ratemyrestaurant.dto.RestaurantDTO;
import pl.ratemyrestaurant.dto.RestaurantPIN;
import pl.ratemyrestaurant.model.UserSearchCircle;
import pl.ratemyrestaurant.service.RestaurantService;

import java.util.Set;

@RestController
@RequestMapping("/places")
public class PlacesController {

    private RestaurantService restaurantService;

    @Autowired
    public PlacesController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping(value = "/allInRadius",produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<RestaurantPIN> getPlacesInRadius(@RequestParam double lng, @RequestParam double lat, @RequestParam double radius, @RequestParam(required = false) String type){
        UserSearchCircle userSearchCircle = new UserSearchCircle(lng, lat, radius);
        Set<RestaurantPIN> restaurantPINSet = restaurantService.retrieveRestaurantsInRadius(userSearchCircle);
        return restaurantPINSet;
    }

    @GetMapping(value = "/{placeId}/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantDTO getPlaceDetails(@PathVariable String placeId){
        return restaurantService.getOrRetrieveRestaurantDTOByID(placeId);
    }
}
