package no.hiof.gruppeprosjekt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Time;
import java.util.Random;

public class ParkingSpace {
    private String city;
    private String address;
    private double size_sqm;
    private double price_ph;
    private int spaceId;
    //Skal se på muligheter for tid

    Random rand = new Random();

    public ParkingSpace(String city, String address, double size_sqm, double price_ph) {
        this.city = city;
        this.address = address;
        this.size_sqm = size_sqm;
        this.price_ph = price_ph;
        spaceId = rand.nextInt(1000);
    }

    //Tom konstruktør for lesing av .json filer
    public ParkingSpace() {

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getSize_sqm() {
        return size_sqm;
    }

    public void setSize_sqm(double size_sqm) {
        this.size_sqm = size_sqm;
    }

    public double getPrice_ph() {
        return price_ph;
    }

    public void setPrice_ph(double price_ph) {
        this.price_ph = price_ph;
    }

    public int getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(int spaceId) {
        this.spaceId = spaceId;
    }
}
