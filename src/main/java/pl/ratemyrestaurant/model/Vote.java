package pl.ratemyrestaurant.model;

public class Vote {

    private String id;
    private boolean good;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isGood() {
        return good;
    }

    public void setGood(boolean good) {
        this.good = good;
    }

    public Vote() {

    }
}
