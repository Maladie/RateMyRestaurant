package pl.ratemyrestaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.ratemyrestaurant.model.Thumb;
import pl.ratemyrestaurant.model.UserSearchCircle;
import pl.ratemyrestaurant.service.placesconnectorservice.impl.GooglePlacesConnector;
import pl.ratemyrestaurant.utils.CacheUtil;
import se.walkercrou.places.Place;

import java.util.Set;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        //===== runs token caching =====
        CacheUtil.init();
        //==============================
        System.out.println(new Thumb());

        GooglePlacesConnector googlePlacesConnector = new GooglePlacesConnector();

        Set<Place> places = null;
        try {
            places = googlePlacesConnector.retrieveRestaurants(new UserSearchCircle(50.2606155, 19.0237505, 300));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(places);
    }
}
