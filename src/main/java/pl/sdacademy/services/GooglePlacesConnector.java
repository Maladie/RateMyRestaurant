package pl.sdacademy.services;

import org.springframework.stereotype.Service;
import pl.sdacademy.model.UserSearchCircle;
import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Param;
import se.walkercrou.places.Place;

import java.util.List;

@Service
public class GooglePlacesConnector implements PlacesConnector{

    private GooglePlaces client;

    public GooglePlacesConnector(){
        client = new GooglePlaces("AIzaSyDwiEyXS5W2lZus1riL0MZ0j_Yfn6TxC2A");
    }

    public List<Place> retrieveRestaurants(UserSearchCircle userSearchCircle) {
        List<Place> places = client.getNearbyPlaces(userSearchCircle.getLat(), userSearchCircle.getLng(), userSearchCircle.getRadius()
                , GooglePlaces.MAXIMUM_RESULTS, Param.name("type").value("cafe"));
        return places;
    }

}
