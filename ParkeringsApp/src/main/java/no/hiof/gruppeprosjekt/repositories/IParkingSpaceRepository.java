package no.hiof.gruppeprosjekt.repositories;

public interface IParkingSpaceRepository {
    void createParkingSpace(String city, String address, double size_sqm, double price_ph);
}
