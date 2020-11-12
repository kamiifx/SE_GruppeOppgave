package no.hiof.gruppeprosjekt.model;

import java.util.Random;

public class ParkingSpace {
    private String city;
    private String address;
    private double size_sqm;
    private double price_ph;
    private int spaceId;
    private User byUser;
    byte available;

    Random rand = new Random();

    public ParkingSpace(String city, String address, double size_sqm, double price_ph, User byUser) {
        this.city = city;
        this.address = address;
        this.size_sqm = size_sqm;
        this.price_ph = price_ph;
        this.byUser = byUser;
        spaceId = rand.nextInt(1000);
        this.available = 1;
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

    public User getByUser() {
        return byUser;
    }

    public void setByUser(User byUser) {
        this.byUser = byUser;
    }

    public byte isAvailable() {
        return available;
    }

    public void setAvailable(byte available) {
        this.available = available;
    }

    public byte getAvailable() {
        return available;
    }

    @Override
    public String toString() {
        return "ParkingSpace{" +
                "city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", size_sqm=" + size_sqm +
                ", price_ph=" + price_ph +
                ", spaceId=" + spaceId +
                ", byUser=" + byUser +
                '}';
    }
}
