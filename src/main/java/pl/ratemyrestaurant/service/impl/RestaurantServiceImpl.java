package pl.ratemyrestaurant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.dto.FoodTypeDTO;
import pl.ratemyrestaurant.dto.RestaurantDTO;
import pl.ratemyrestaurant.dto.RestaurantPIN;
import pl.ratemyrestaurant.exception.NoSuchFoodTypeException;
import pl.ratemyrestaurant.mappers.PlaceToRestaurantMapper;
import pl.ratemyrestaurant.mappers.RestaurantToPinMapper;
import pl.ratemyrestaurant.mappers.RestaurantToRestaurantDTOMapper;
import pl.ratemyrestaurant.model.Rating;
import pl.ratemyrestaurant.model.Restaurant;
import pl.ratemyrestaurant.model.UserSearchCircle;
import pl.ratemyrestaurant.repository.RestaurantRepository;
import pl.ratemyrestaurant.service.FoodTypeService;
import pl.ratemyrestaurant.service.PlacesConnector;
import pl.ratemyrestaurant.service.RatingService;
import pl.ratemyrestaurant.service.RestaurantService;
import se.walkercrou.places.Place;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.ratemyrestaurant.mappers.RestaurantToPinMapper.mapRestaurantToPin;
import static pl.ratemyrestaurant.mappers.RestaurantToRestaurantDTOMapper.mapToRestaurant;
import static pl.ratemyrestaurant.mappers.RestaurantToRestaurantDTOMapper.mapToRestaurantDto;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private RestaurantRepository restaurantRepository;
    private PlacesConnector placesConnector;
    private RatingService ratingService;
    private FoodTypeService foodTypeService;

    @Autowired
    public RestaurantServiceImpl(
            RestaurantRepository restaurantRepository,
            PlacesConnector placesConnector,
            RatingService ratingService,
            FoodTypeService foodTypeService) {

        this.restaurantRepository = restaurantRepository;
        this.placesConnector = placesConnector;
        this.ratingService = ratingService;
        this.foodTypeService = foodTypeService;
    }

    public Set<RestaurantPIN> retrieveRestaurantsInRadius(UserSearchCircle userSearchCircle) {
        Set<Place> places = new HashSet<>();
        Set<RestaurantPIN> restaurantPINs = new HashSet<>();
        try {
            places = placesConnector.retrievePlaces(userSearchCircle);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        places.forEach(place -> {
            RestaurantPIN restaurantPIN = mapPlaceToRestaurantDto(place);
            restaurantPINs.add(restaurantPIN);
        });
        return restaurantPINs;
    }

    @Override
    public Set<RestaurantPIN> retrieveRestaurantsInRadiusWithFoodType(UserSearchCircle userSearchCircle, String foodTypeName) {
        Set<RestaurantPIN> restaurantPINSWithFoodType = new HashSet<>();
        FoodTypeDTO foodTypeDTO;
        try {
            foodTypeDTO = foodTypeService.getFoodTypeDTOByName(foodTypeName);
        } catch (NoSuchFoodTypeException e) {
            e.printStackTrace();
            foodTypeDTO = null;
        }
        //TODO compare performance with regular DB select filtering
        //valid foodType else empty Set<RestaurantPIN>
        if (foodTypeDTO != null) {
            Set<RestaurantPIN> restaurantPINSet = retrieveRestaurantsInRadius(userSearchCircle);
            if (restaurantPINSet.size() > 0) {
                List<String> restaurantIDs = restaurantPINSet.stream().map(RestaurantPIN::getId).collect(Collectors.toList());
                List<Restaurant> restaurantsByID = restaurantRepository.findByIdIn(restaurantIDs);
                // filter only with foodType and map to PINs
                restaurantPINSWithFoodType = restaurantsByID.stream()
                        .filter(restaurant -> restaurant.getFoodTypes().stream().anyMatch(foodType -> foodType.getName().equals(foodTypeName)))//get only with foodType
                        .map(this::transformRestaurantToPIN)//map to PINs
                        .collect(Collectors.toSet());
            }
        }
        return restaurantPINSWithFoodType;
    }

    public RestaurantPIN mapPlaceToRestaurantDto(Place place) {
        Restaurant restaurant = PlaceToRestaurantMapper.mapToRestaurant(place);
        return RestaurantToPinMapper.mapRestaurantToPin(restaurant);
    }

    public void addOrUpdateRestaurant(RestaurantDTO restaurantDTO) {
        if (restaurantDTO.isNewlyCreated()) {
            addNewRestaurant(restaurantDTO);
        } else {
            updateRestaurant(restaurantDTO);
        }
    }

    private void addNewRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = mapToRestaurant(restaurantDTO);
        restaurantRepository.save(restaurant);
    }

    private void updateRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantRepository.getOne(restaurantDTO.getId());
        restaurant.setFoodTypes(restaurantDTO.getFoodTypes());
        restaurantRepository.save(restaurant);
    }

    public RestaurantDTO getOrRetrieveRestaurantDTOByID(String placeId) {
        RestaurantDTO restaurantDTO = getRestaurantDTOById(placeId);
        if (restaurantDTO == null) {
            restaurantDTO = retrieveDtoIfNotExistInDB(placeId);
        } else {
            restaurantDTO.setNewlyCreated(false);
        }
        return restaurantDTO;
    }

    public RestaurantDTO getRestaurantDTOById(String id) {
        Restaurant restaurant = restaurantRepository.findOne(id);
        if (restaurant == null) {
            return null;
        }
        return transformRestaurantToDTO(restaurant);
    }

    private RestaurantDTO retrieveDtoIfNotExistInDB(String placeId) {
        Place place = placesConnector.retrievePlaceById(placeId);
        Restaurant restaurant = PlaceToRestaurantMapper.mapToRestaurant(place);
        RestaurantDTO restaurantDTO = transformRestaurantToDTO(restaurant);
        restaurantDTO.setNewlyCreated(true);
        return restaurantDTO;
    }

    private RestaurantDTO transformRestaurantToDTO(Restaurant restaurant) {
        String restaurantId = restaurant.getId();
        Set<Rating> ratings = ratingService.retrieveRestaurantRatings(restaurantId);
        return mapToRestaurantDto(restaurant, ratings);
    }

    public RestaurantPIN getRestaurantPINById(String id) {
        return transformRestaurantToPIN(restaurantRepository.findOne(id));
    }

    @Override
    public Set<RestaurantDTO> getRestaurantsDTOByFoodType(String foodType) {
        List<Restaurant> restaurantsByFoodType = restaurantRepository.findAllByFoodTypes_Name(foodType);
        return restaurantsByFoodType.stream()
                .map(i -> RestaurantToRestaurantDTOMapper.mapToRestaurantDto(i, getRestaurantRatings(i.getId()))).collect(Collectors.toSet());
    }

    @Override
    public boolean isRestaurantInDB(String restaurantID) {
        return restaurantRepository.exists(restaurantID);
    }

    private RestaurantPIN transformRestaurantToPIN(Restaurant restaurant) {
        return mapRestaurantToPin(restaurant);
    }

    private Set<Rating> getRestaurantRatings(String restaurantId) {
        return ratingService.retrieveRestaurantRatings(restaurantId);
    }
}
