package com.github.ik024.sunshine;

/**
 * Created by Ismail.Khan2 on 7/4/2016.
 */
public class ForecastItem {
    String item;

    public ForecastItem(){

    }
    public ForecastItem(String item){
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
