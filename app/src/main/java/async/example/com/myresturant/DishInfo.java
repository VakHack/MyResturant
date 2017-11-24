package async.example.com.myresturant;

import android.support.annotation.NonNull;

/**
 * Created by Administrator on 22/11/2017.
 */

public class DishInfo implements Comparable<DishInfo>{

    private String name;
    private String picURL;
    private int price;
    private int calories;
    private boolean isBasePhase;

    DishInfo(){

        name = "default";
        picURL = "https://static.pexels.com/photos/46239/salmon-dish-food-meal-46239.jpeg";
        price = 0;
        isBasePhase = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public boolean isBasePhase() {
        return isBasePhase;
    }

    public void switchPhase() {
        isBasePhase = !isBasePhase;
    }

    @Override
    public int compareTo(@NonNull DishInfo dishInfo) {
        return name.compareTo(dishInfo.getName());
    }
}
