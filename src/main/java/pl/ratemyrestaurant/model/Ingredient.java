package pl.ratemyrestaurant.model;

import pl.ratemyrestaurant.dto.IngredientDto;

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

    public void giveThumbUp(){
        thumb.giveThumbUp();
    }

    public void giveThumbDown(){
        thumb.giveThumbDown();
    }

    public IngredientDto toIngredientDto(){
        int ups = thumb.getThumbsUp();
        int downs = thumb.getThumbsDown();

        return new IngredientDto.IngredientDtoBuilder()
                .addName(name)
                .addThumbsUp(ups)
                .addThumbsDown(downs)
                .build();
    }

    @Override
    public String toString() {
        return name + " - " + thumb.toString();
    }
}
