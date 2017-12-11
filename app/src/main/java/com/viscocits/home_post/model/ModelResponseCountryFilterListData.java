package com.viscocits.home_post.model;

/**
 * Created by abhi on 07/12/17.
 */

public class ModelResponseCountryFilterListData {

    private int Country_Id;
    private String CountryName;
    private boolean IsActive;

    public int getCountry_Id() {
        return Country_Id;
    }

    public void setCountry_Id(int country_Id) {
        Country_Id = country_Id;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public String toString() {
        return CountryName;
    }
}
