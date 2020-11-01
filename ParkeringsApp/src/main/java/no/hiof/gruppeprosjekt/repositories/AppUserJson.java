package no.hiof.gruppeprosjekt.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import no.hiof.gruppeprosjekt.model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class AppUserJson implements IUserRepository{
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


    @Override
    public ArrayList<User> getAllUsers() {
        return jUser;
    }

    @Override
    public User getUserByMail(String email) {
        for (User user : jUser){
            if (user.getMail() == email){
                return user;
            }
        }
        return null;
    }

    @Override
    public void registerUser(String name, String lastName, String password, String email) {
        User registerUser = new User(name,lastName,password,email);
        jUser.add(registerUser);
    }

    @Override
    public void deleteUser(String email) {
        jUser.remove(getUserByMail(email));
    }

    @Override
    public void updateUser(String name, String lastName, String password, String email) {
        User updateUser = getUserByMail(email);
        updateUser.setName(name);
        updateUser.setLastName(lastName);
        updateUser.setPassword(password);
        updateUser.setMail(password);
    }
}
