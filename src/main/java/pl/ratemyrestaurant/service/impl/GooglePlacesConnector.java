package pl.ratemyrestaurant.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.model.UserSearchCircle;
import pl.ratemyrestaurant.service.PlacesConnector;
import pl.ratemyrestaurant.utils.StreamUtils;
import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Param;
import se.walkercrou.places.Place;
import se.walkercrou.places.exception.GooglePlacesException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GooglePlacesConnector implements PlacesConnector {
    private static Logger logger = LogManager.getLogger(GooglePlacesConnector.class);
    private GooglePlaces client;

    @Autowired
    public GooglePlacesConnector(@Value("${googlekey}") String apiKey) {
        client = new GooglePlaces(apiKey);
    }

    @Override
    public Set<Place> retrievePlaces(UserSearchCircle userSearchCircle) throws InterruptedException {
        Set<Place> places = new HashSet<>();
        addAllRetrievedRestaurantsToPlaces(places, userSearchCircle);
        return places.stream().filter(StreamUtils.distinctByKey(Place::getPlaceId)).collect(Collectors.toSet());
    }

    @Override
    public Place retrievePlaceById(String placeId) {
        return client.getPlaceById(placeId);
    }

    private List<Place> retrieveRestaurants(UserSearchCircle userSearchCircle, String name) throws GooglePlacesException {
        // wg. nowej dokumentacji limit wynosi 200
        List<Place> nearbyPlaces;
        try {
             nearbyPlaces = client.getNearbyPlaces(userSearchCircle.getLat(), userSearchCircle.getLng(), userSearchCircle.getRadius()
                    , 20, Param.name("type").value(name));
        } catch (GooglePlacesException e){
            logger.catching(e);
            throw e;
        }
        return nearbyPlaces;
    }

    private void addAllRetrievedRestaurantsToPlaces(Set<Place> places, UserSearchCircle userSearchCircle) {
        String[] placesNames = {"bar", "cafe", "restaurant"};
        for (String placesName : placesNames) {
            try {
                List<Place> restaurants = retrieveRestaurants(userSearchCircle, placesName);
                places.addAll(restaurants);
            } catch (GooglePlacesException e){
                logger.debug("Retrieve restaurants exception! Message: | "+ e.getMessage()+ " | while trying to get places with type: "+placesName);
            }
        }
    }
}
