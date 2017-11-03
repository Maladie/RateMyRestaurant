package pl.ratemyrestaurant.model;

import javax.persistence.*;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Embedded
    private Thumb thumb;

    {thumb = new Thumb();}

    public Ingredient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbsUp(){
        return thumb.getThumbsUp();
    }

    public int getThumbsDown(){
        return thumb.getThumbsDown();
    }

    @Override
    public String toString() {
        return name + " - " + thumb.toString();
    }
}
