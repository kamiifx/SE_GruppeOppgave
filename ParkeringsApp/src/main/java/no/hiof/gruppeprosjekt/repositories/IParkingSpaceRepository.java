package no.hiof.gruppeprosjekt.repositories;

import no.hiof.gruppeprosjekt.model.ParkingSpace;
<<<<<<< HEAD

import java.util.ArrayList;

public interface IParkingSpaceRepository {
    ArrayList<ParkingSpace> getAllUser();


    void deleteParkingspace (int spaceId);
    ParkingSpace confirmParking (int spaceId);



=======
import java.util.ArrayList;

public interface IParkingSpaceRepository {
    ArrayList<ParkingSpace> getAllSpaces();
    ParkingSpace getSpaceById(int id);
>>>>>>> main
    void createParkingSpace(String city, String address, String size_sqm, String price_ph, String userId);
    void deleteParkingSpace(int spaceId);
}
