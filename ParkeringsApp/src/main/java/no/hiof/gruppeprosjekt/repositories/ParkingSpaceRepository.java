package no.hiof.gruppeprosjekt.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import no.hiof.gruppeprosjekt.model.ParkingSpace;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

//Akkurat nå leses det og skrives det i JSON siden vi ikke har noe database ute
public class ParkingSpaceRepository implements IParkingSpaceRepository{
    private ArrayList<ParkingSpace> parkingSpaces = new ArrayList<>();

    public ParkingSpaceRepository() {
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
    public void createParkingSpace(String city, String address, String size_sqm, String price_ph) {
        ParkingSpace space = new ParkingSpace(city, address, Double.parseDouble(size_sqm), Double.parseDouble(price_ph));
        parkingSpaces.add(space);
        writeJson();
    }
}
