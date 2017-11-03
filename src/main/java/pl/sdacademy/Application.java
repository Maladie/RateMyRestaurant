package pl.sdacademy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.sdacademy.model.UserSearchCircle;
import pl.sdacademy.services.GooglePlacesConnector;
import se.walkercrou.places.Place;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

        GooglePlacesConnector googlePlacesConnector = new GooglePlacesConnector();

        Set<Place> places = googlePlacesConnector.retrieveRestaurants(new UserSearchCircle(50.2606155, 19.0237505, 100));

        System.out.println(places);
    }
}
