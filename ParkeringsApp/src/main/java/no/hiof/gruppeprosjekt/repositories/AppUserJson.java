package no.hiof.gruppeprosjekt.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import no.hiof.gruppeprosjekt.model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class AppUserJson {
    private ArrayList<User> jUser = new ArrayList<User>();

    public void readJson(){
        File fileOfJson = new File("jsonUsers");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            User[] userArray = objectMapper.readValue(fileOfJson, User[].class);
            Collections.addAll(jUser,userArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeJson() {
        File fileOfJson = new File("jsonUsers");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileOfJson, jUser);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //For Testing av repo bare
    public void writeArrayToJson(ArrayList<User> listsNeeded) {
        File fileOfJson = new File("jsonUsers");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileOfJson, listsNeeded);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
