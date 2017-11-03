package pl.ratemyrestaurant.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.ratemyrestaurant.model.Restaurant;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class RestaurantDAO {

    private EntityManager entityManager;

    @Autowired
    public RestaurantDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public void saveRestaurant(Restaurant restaurant) {
        entityManager.persist(restaurant);
    }

}
