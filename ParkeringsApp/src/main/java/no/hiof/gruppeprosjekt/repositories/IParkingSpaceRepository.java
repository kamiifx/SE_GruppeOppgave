package no.hiof.gruppeprosjekt.repositories;

import no.hiof.gruppeprosjekt.model.ParkingSpace;

import java.util.ArrayList;

public interface IParkingSpaceRepository {
    ArrayList<ParkingSpace> getAllUser();


    void deleteParkingspace (int spaceId);
    ParkingSpace confirmParking (int spaceId);



    void createParkingSpace(String city, String address, String size_sqm, String price_ph, String userId);
    void deleteParkingSpace(int spaceId);
}
