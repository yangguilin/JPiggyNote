package com.ygl.piggynote.bean;

/**
 * Created by yanggavin on 14-7-17.
 */
public class CityBean {

    private int id;
    private String name;
    private String countrycode;
    private String district;
    private int population;

    public CityBean(){

    }

    public void setId(int id){ this.id = id; }
    public int getId(){ return this.id; }

    public void setName(String name){ this.name = name; }
    public String getName(){ return this.name; }

    public void setCountrycode(String countryCode){ this.countrycode = countryCode; }
    public String getCountrycode(){ return this.countrycode; }

    public void setDistrict(String district){ this.district = district; }
    public String getDistrict(){ return this.district; }

    public void setPopulation(int population){ this.population = population; }
    public int getPopulation(){ return this.population; }
}
