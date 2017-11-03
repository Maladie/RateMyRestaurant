package pl.ratemyrestaurant.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Thumb {

    @Column(name = "thumbs_up")
    private int thumbsUp;
    @Column(name = "thumbs_down")
    private int thumbsDown;

    {
        thumbsUp = 0;
        thumbsDown = 0;
    }

    public Thumb() {
    }

    public int getThumbsUp() {
        return thumbsUp;
    }

    public int getThumbsDown() {
        return thumbsDown;
    }

    public void setThumbsUp(int thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public void setThumbsDown(int thumbsDown) {
        this.thumbsDown = thumbsDown;
    }

    public void giveThumbUp() {
        thumbsUp++;
    }

    public void giveThumbDown(){
        thumbsDown++;
    }

    @Override
    public String toString() {
        return "UP: " + thumbsUp + ", DOWN: " + thumbsDown;
    }
}
