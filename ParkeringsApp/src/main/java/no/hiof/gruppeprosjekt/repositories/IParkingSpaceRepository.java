package no.hiof.gruppeprosjekt.repositories;

import no.hiof.gruppeprosjekt.model.ParkingSpace;
import java.util.ArrayList;

public interface IParkingSpaceRepository {
    ArrayList<ParkingSpace> getAllSpaces();
    ParkingSpace getSpaceById(int id);
    void createParkingSpace(String city, String address, String size_sqm, String price_ph, String userId);
}
