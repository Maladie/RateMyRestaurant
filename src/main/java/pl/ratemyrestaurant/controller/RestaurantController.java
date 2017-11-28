package pl.ratemyrestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ratemyrestaurant.dto.RatingDTO;
import pl.ratemyrestaurant.dto.RestaurantDTO;
import pl.ratemyrestaurant.dto.RestaurantPIN;
import pl.ratemyrestaurant.exception.IngredientRatingNotFoundException;
import pl.ratemyrestaurant.exception.RestaurantNotFoundException;
import pl.ratemyrestaurant.model.Info;
import pl.ratemyrestaurant.model.UserSearchCircle;
import pl.ratemyrestaurant.service.RatingService;
import pl.ratemyrestaurant.service.RestaurantService;
import pl.ratemyrestaurant.type.APIInfoCodes;

import java.util.Set;

@RestController
@RequestMapping(value = "/restaurants")
public class RestaurantController {

    private RestaurantService restaurantService;
    private RatingService ratingService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, RatingService ratingService) {
        this.restaurantService = restaurantService;
        this.ratingService = ratingService;
    }

    //temp. disabled
//    @GetMapping(value = "/{restaurantID}")
//    public RestaurantDTO getRestaurantById(@PathVariable String restaurantId) {
//        return restaurantService.getRestaurantDTOById(restaurantId);
//    }

    //? unused currently, we cant add new restaurant currently

//    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<RestaurantDTO> persistRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
//        restaurantService.addOrUpdateRestaurant(restaurantDTO);
//        return new ResponseEntity<>(restaurantDTO, HttpStatus.CREATED);
//    }

    //? Method to refactor or move filtering to front
//    @GetMapping(value = "/type/{foodType}")
//    public Set<RestaurantDTO> getRestaurantsByFoodType(@PathVariable String foodType) {
//        return restaurantService.getRestaurantsDTOByFoodType(foodType);
//    }

    //TODO refactor
//    //? Refactor idea
//    @GetMapping(value = "/searchInRadius/{foodType}")
//    public Set<RestaurantDTO> getRestaurantsInRadiusByFoodType(@PathVariable String foodType) {
//        return new HashSet<>(); //TODO
//    }

    //? Refactor response type from pin to regular restaurant
    //TODO add parameters validator here or in service
    @GetMapping(value = "/areaSearch")
    public Set<RestaurantPIN> getRestaurantInRadius(@RequestParam double lng, @RequestParam double lat, @RequestParam double radius, @RequestParam(required = false) String type) {
        UserSearchCircle userSearchCircle = new UserSearchCircle(lat, lng, radius);
        return restaurantService.retrieveRestaurantsInRadius(userSearchCircle);
    }

    @GetMapping(value = "/{restaurantID}")
    public ResponseEntity getRestaurantDetails(@PathVariable String restaurantID) {
        RestaurantDTO restaurantDTO = restaurantService.getOrRetrieveRestaurantDTOByID(restaurantID);
        if (restaurantDTO == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(restaurantDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/{restaurantId}/ingredientRatings")
    public ResponseEntity addIngredientRating(@PathVariable("restaurantId") String restaurantId, @RequestBody Long ingredientID) {
        if (!restaurantService.isRestaurantInDB(restaurantId)) {
            restaurantService.addOrUpdateRestaurant(restaurantService.getOrRetrieveRestaurantDTOByID(restaurantId));
        }
        RatingDTO ratingDTO = ratingService.addNewIngredientRating(restaurantId, ingredientID);
        if (ratingDTO == null) {
            Info info = new Info();
            info.setHttpStatusCode(422L);
            info.setInfoCode(APIInfoCodes.ENTITY_ALREADY_EXISTS);
            info.setDesc("Could not create. Ingredient rating already exists");
            return new ResponseEntity<>(info, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(ratingDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{restaurantId}/ingredientRatings/{ingredientId}")
    public ResponseEntity voteOnIngredient(@PathVariable("restaurantId") String restaurantId, @PathVariable("ingredientId") Long ingredientId, @RequestBody Boolean upVote) {
        RatingDTO rating = null;
        try {
            rating = ratingService.rateIngredient(restaurantId, ingredientId, upVote);
        } catch (RestaurantNotFoundException e) {
            Info info = new Info.InfoBuilder()
                    .setHttpStatusCode(422L)
                    .setInfoCode(APIInfoCodes.RESTAURANT_NOT_EXISTS)
                    .setDescription("Could not create. Restaurant with restaurantID does not exist: " + restaurantId).build();
            return new ResponseEntity<>(info, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (IngredientRatingNotFoundException e) {
            Info info = new Info.InfoBuilder()
                    .setHttpStatusCode(422L)
                    .setInfoCode(APIInfoCodes.INGREDIENT_RATING_NOT_EXISTS)
                    .setDescription("Could not create. IngredientRating with ingredientID does not exist: " + ingredientId).build();
            return new ResponseEntity<>(info, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        HttpStatus status = rating != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(rating, status);
    }
}
