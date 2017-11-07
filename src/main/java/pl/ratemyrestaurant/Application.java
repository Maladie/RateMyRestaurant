package pl.ratemyrestaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import pl.ratemyrestaurant.config.ApplicationContextProvider;
import pl.ratemyrestaurant.model.Thumb;
import pl.ratemyrestaurant.model.UserSearchCircle;
import pl.ratemyrestaurant.service.placesconnectorservice.impl.GooglePlacesConnector;
import se.walkercrou.places.Place;

import java.util.Set;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println(new Thumb());

//        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
//
//        GooglePlacesConnector googlePlacesConnector = applicationContext.getBean("googlePlacesConnector", GooglePlacesConnector.class);
//
//        Set<Place> places = null;
//        try {
//            places = googlePlacesConnector.retrievePlaces(new UserSearchCircle(50.2606155, 19.0237505, 1000));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(places);
    }
}
