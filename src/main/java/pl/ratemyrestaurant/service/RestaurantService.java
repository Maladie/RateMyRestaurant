package pl.ratemyrestaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.dao.RestaurantDAO;
import pl.ratemyrestaurant.dto.RestaurantDTO;
import pl.ratemyrestaurant.model.Restaurant;
import pl.ratemyrestaurant.repository.RestaurantRepository;

@Service
public class RestaurantService {

    private RestaurantDAO restaurantDAO;
    private RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantDAO restaurantDAO, RestaurantRepository restaurantRepository) {
        this.restaurantDAO = restaurantDAO;
        this.restaurantRepository = restaurantRepository;
    }

    public void addRestaurant(RestaurantDTO restaurantDTO) {
        //TODO: implementation
    }

    public RestaurantDTO getRestaurantDTOById(Long id) {
        return transformRestaurantToDTO(restaurantRepository.findOne(id));
    }

    private RestaurantDTO transformRestaurantToDTO(Restaurant restaurant) {
        RestaurantDTO restaurantDTO = new RestaurantDTO(restaurant);
        return restaurantDTO;
    }
}
