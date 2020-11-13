/*
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
    private String fileName = "jsonUsers";
    private User activeUser;
    private Boolean loggedin = false;

    public AppUserJson(){ readJson(fileName); }

    public void readJson(String filnavn){
        File fileOfJson = new File(filnavn);
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
            */
/*objectMapper.findAndRegisterModules();
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);*//*

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
            if (user.getMail().equals(email)){
                return user;
            }
        }
        return null;
    }


    @Override
    public User getUserById(int id) {
        for (User user : jUser){
            if (user.getId() == id){
                return user;
            }
        }
        return null;
    }

    @Override
    public void loginUser(String email, String password) {
        User user = getUserByMail(email);
        if (user.getMail().equals(email) && user.getPassword().equals(password)){
            activeUser = user;
            loggedin = true;
        }
        activeUser = null;
        loggedin = false;
    }

    @Override
    public void registerUser(int id,String name, String lastName, String password, String email) {
        User registerUser = new User(id,name,lastName,password,email);
        jUser.add(registerUser);
        writeArrayToJson(jUser);
    }

    @Override
    public void deleteUser(String email) {
        jUser.remove(getUserByMail(email));
        writeArrayToJson(jUser);
    }
*/
/*
    @Override
    public void deleteUserById(String userId) {
        User u = getUserById(Integer.parseInt(userId));
        deleteUser(u.getMail());
    }*//*


    @Override
    public void updateUser(String id, String name, String lastName, String password, String email) {
        User user = getUserById(Integer.parseInt(id));
        user.setName(name);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setMail(email);
    }

}
*/
