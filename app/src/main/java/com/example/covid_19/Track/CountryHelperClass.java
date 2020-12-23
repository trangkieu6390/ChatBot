package com.example.covid_19.Track;

public class CountryHelperClass {
    private String country, NewConfirmed, TotalConfirmed, NewDeaths, TotalDeaths, NewRecovered, TotalRecovered, Date;
    public CountryHelperClass(){}

    public CountryHelperClass(String country, String NewConfirmed, String TotalConfirmed, String NewDeaths,
                              String TotalDeaths, String NewRecovered, String TotalRecovered, String Date){
        this.country = country;
        this.NewConfirmed = NewConfirmed;
        this.TotalConfirmed = TotalConfirmed;
        this.NewDeaths = NewDeaths;
        this.TotalDeaths = TotalDeaths;
        this.NewRecovered = NewRecovered;
        this.TotalRecovered = TotalRecovered;
        this.Date = Date;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public String getCountry() {
        return country;
    }

    public String getNewConfirmed() {
        return NewConfirmed;
    }

    public String getTotalConfirmed() {
        return TotalConfirmed;
    }

    public String getNewDeaths() {
        return NewDeaths;
    }

    public String getTotalDeaths() {
        return TotalDeaths;
    }

    public String getNewRecovered() {
        return NewRecovered;
    }

    public String getTotalRecovered() {
        return TotalRecovered;
    }

    public String getDate() {
        return Date;
    }

    public void setNewConfirmed(String newConfirmed) {
        NewConfirmed = newConfirmed;
    }

    public void setTotalConfirmed(String totalConfirmed) {
        TotalConfirmed = totalConfirmed;
    }

    public void setNewDeaths(String newDeaths) {
        NewDeaths = newDeaths;
    }

    public void setTotalDeaths(String totalDeaths) {
        TotalDeaths = totalDeaths;
    }

    public void setNewRecovered(String newRecovered) {
        NewRecovered = newRecovered;
    }

    public void setTotalRecovered(String totalRecovered) {
        TotalRecovered = totalRecovered;
    }

    public void setDate(String date) {
        Date = date;
    }
}
