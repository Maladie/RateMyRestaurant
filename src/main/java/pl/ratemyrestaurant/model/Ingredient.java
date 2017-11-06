package pl.ratemyrestaurant.model;

import pl.ratemyrestaurant.dto.IngredientDTO;

import javax.persistence.*;

@Entity
public class Ingredient implements Comparable<Ingredient>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Embedded
    private Thumb thumb;

    {thumb = new Thumb();}

    public Ingredient() {
    }

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

    public void setThumbsUp(int thumbsUp) {
        thumb.setThumbsUp(thumbsUp);
    }

    public void setThumbsDown(int thumbsDown) {
        thumb.setThumbsDown(thumbsDown);
    }

    public void giveThumbUp(){
        thumb.giveThumbUp();
    }

    public void giveThumbDown(){
        thumb.giveThumbDown();
    }

    public int getUpDownDifference(){
        return getThumbsUp() - getThumbsDown();
    }

    public IngredientDTO toIngredientDto(){
        int ups = thumb.getThumbsUp();
        int downs = thumb.getThumbsDown();

        return new IngredientDTO.IngredientDtoBuilder()
                .addName(name)
                .addThumbsUp(ups)
                .addThumbsDown(downs)
                .build();
    }

    @Override
    public String toString() {
        return name + " - " + thumb.toString();
    }

    @Override
    public int compareTo(Ingredient o) {
        return Integer.compare(getUpDownDifference(), o.getUpDownDifference());
    }
}
