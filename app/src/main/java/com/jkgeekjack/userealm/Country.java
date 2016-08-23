package com.jkgeekjack.userealm;

import io.realm.RealmObject;

/**
 * Created by Administrator on 2016/6/30.
 */
public class Country extends RealmObject {
    private String name;
    private int population;

    public Country() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
