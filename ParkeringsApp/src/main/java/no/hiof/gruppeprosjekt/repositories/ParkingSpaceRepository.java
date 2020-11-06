package no.hiof.gruppeprosjekt.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import no.hiof.gruppeprosjekt.model.ParkingSpace;
import no.hiof.gruppeprosjekt.model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

//Akkurat nå leses det og skrives det i JSON siden vi ikke har noe database ute
public class ParkingSpaceRepository implements IParkingSpaceRepository{
    private IUserRepository userRepository;
    private ArrayList<ParkingSpace> parkingSpaces = new ArrayList<>();

    public ParkingSpaceRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
        readJson();
        writeJson();
    }

    public void readJson() {
        File fileOfJson = new File("jsonSpaces");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            ParkingSpace[] parkingSpacesArray = objectMapper.readValue(fileOfJson, ParkingSpace[].class);
            Collections.addAll(parkingSpaces, parkingSpacesArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeJson() {
        File fileOfJson = new File("jsonSpaces");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileOfJson, parkingSpaces);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ParkingSpace confirmParking(int spaceId) {
        for(ParkingSpace parkingspace : parkingSpaces){
            if(parkingspace.getSpaceId()== spaceId){
                return parkingspace;
            }
        }
        return null;

    }

    @Override
    public ArrayList<ParkingSpace> getAllUser() {
        //returnerer Arraylisten til parkeringsplassen
        return parkingSpaces;
    }

    @Override
    public void deleteParkingspace(int spaceId) {

    }

    @Override
    public ArrayList<ParkingSpace> getAllSpaces() {
        return(parkingSpaces);
    }

    @Override
    public ParkingSpace getSpaceById(int id) {
        for(ParkingSpace space : parkingSpaces) {
            if(space.getSpaceId() == id) {
                return space;
            }
        }
        return null;
    }

    @Override
    public void createParkingSpace(String city, String address, String size_sqm, String price_ph, String userId) {
        User byUser = userRepository.getUserById(Integer.parseInt(userId));
        ParkingSpace space = new ParkingSpace(city, address, Double.parseDouble(size_sqm), Double.parseDouble(price_ph), byUser);
        parkingSpaces.add(space);
        writeJson();
    }

    @Override
    public void deleteParkingSpace(int spaceId) {
        parkingSpaces.remove(confirmParking(spaceId));
    }


}
