package pl.ratemyrestaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.ratemyrestaurant.model.Thumb;
import pl.ratemyrestaurant.utils.CacheUtil;

@SpringBootApplication
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        //===== runs token caching =====

        CacheUtil.init();
        //==============================
        System.out.println(new Thumb());
//
//        GooglePlacesConnector googlePlacesConnector = new GooglePlacesConnector();
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
