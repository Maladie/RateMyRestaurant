package pl.ratemyrestaurant.service.placesconnectorservice.impl;

import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.service.placesconnectorservice.PlacesConnector;
import pl.ratemyrestaurant.model.UserSearchCircle;
import pl.ratemyrestaurant.utils.StreamUtils;
import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Param;
import se.walkercrou.places.Place;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GooglePlacesConnector implements PlacesConnector {

    private GooglePlaces client;

    public GooglePlacesConnector() {
        client = new GooglePlaces("AIzaSyD8YUXZXgf6IOslQnwLolmbbabf-s1ajPM");
    }

    @Override
    public Set<Place> retrieveRestaurants(UserSearchCircle userSearchCircle) throws InterruptedException {
        Set<Place> places = new HashSet<>();
        addAllRetrievedRestaurantsToPlaces(places, userSearchCircle);
        return places.stream().filter(StreamUtils.distinctByKey(Place::getPlaceId)).collect(Collectors.toSet());
    }

    @Override
    public Place retrieveRestaurantById(String placeId) {
        return client.getPlaceById(placeId);
    }

    private List<Place> retrieveRestaurants(UserSearchCircle userSearchCircle, String name) {
        return client.getNearbyPlaces(userSearchCircle.getLat(), userSearchCircle.getLng(), userSearchCircle.getRadius()
                , 100, Param.name("type").value(name));
    }

    private void addAllRetrievedRestaurantsToPlaces(Set<Place> places, UserSearchCircle userSearchCircle) {
        String[] placesNames = {"bar", "food", "cafe", "restaurant"};
        for (String placesName : placesNames) {
            List<Place> restaurants = retrieveRestaurants(userSearchCircle, placesName);
            places.addAll(restaurants);
        }
    }
}
