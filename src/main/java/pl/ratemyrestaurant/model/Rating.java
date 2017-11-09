package pl.ratemyrestaurant.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Restaurant restaurants;
    @ManyToOne
    private Ingredient ingredient;
    @Embedded
    private Thumb thumb;
}
