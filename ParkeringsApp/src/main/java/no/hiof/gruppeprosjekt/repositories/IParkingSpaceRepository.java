package no.hiof.gruppeprosjekt.repositories;

public interface IParkingSpaceRepository {
    void createParkingSpace(String city, String address, String size_sqm, String price_ph);
}
