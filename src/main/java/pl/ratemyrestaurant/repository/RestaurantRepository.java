package pl.ratemyrestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.ratemyrestaurant.model.Restaurant;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, String> {

    Restaurant findById(String id);

    @Query(value = "SELECT *" +
            "            FROM (" +
            "                SELECT *" +
            "                FROM Restaurant" +
            "                WHERE lat BETWEEN :minLat AND :maxLat" +
            "                  AND lng BETWEEN :minLng AND :maxLng" +
            "            ) AS FirstCut" +
            "            WHERE acos(sin(:lat)*sin(radians(lat)) + cos(:lat)*cos(radians(lat))*cos(radians(lng)-:lng)) * :earthRad < :rad",
            nativeQuery = true)
    List<Restaurant> findRestaurantsByFoodTypeInRadius(@Param("minLat") double minLat, @Param("maxLat") double maxLat,
                                                       @Param("minLng") double minLng, @Param("maxLng") double maxLng,
                                                       @Param("lat") double lat, @Param("lng") double lng,
                                                       @Param("rad") double rad, @Param("earthRad") double earthRad);
}
