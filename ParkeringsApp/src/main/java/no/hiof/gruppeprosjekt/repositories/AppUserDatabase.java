package no.hiof.gruppeprosjekt.repositories;
import no.hiof.gruppeprosjekt.model.User;

import java.sql.*;
import java.util.ArrayList;

public class AppUserDatabase implements IUserRepository {
    String url = "jdbc:sqlite:userDatabase.sqlite";

    public AppUserDatabase(){createUserTable();}

    public void connectDatabase(){
        Connection connect = null;
        try {
            connect = DriverManager.getConnection(url);
            System.out.println("Database connected");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try{
                if (connect != null){
                    connect.close();
                }
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }


    public void createUserTable(){
        String userSql = "CREATE TABLE IF NOT EXISTS users (\n"
                        + " id integer PRIMARY KEY,\n"
                        + " name text NOT NULL,\n"
                        + " lastname text NOT NULL,\n"
                        + " password text NOT NULL \n"
                        + ");";

        try {
            Connection connect = DriverManager.getConnection(url);
            Statement state = connect.createStatement();
            state.execute(userSql);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void insertUser(int id,String name, String lastName, String password, String email){
        String userInsert = "INSERT INTO users(id,name,lastname,password,email)(VALUES?,?,?,?,?)";

        try {
            Connection connect = DriverManager.getConnection(url);
            PreparedStatement preState = connect.prepareStatement(userInsert);
            preState.setInt(1,id);
            preState.setString(2,name);
            preState.setString(3,lastName);
            preState.setString(4,password);
            preState.setString(5,email);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return null;
    }

    @Override
    public User getUserByMail(String email) {
        return null;
    }

    @Override
    public User getUserById(int id) {
        return null;
    }

    @Override
    public void loginUser(String email, String password) {

    }

    @Override
    public void registerUser(int id, String name, String lastName, String password, String email) {
        insertUser(id,name,lastName,password,email);
    }

    @Override
    public void deleteUser(String email) {

    }

    @Override
    public void updateUser(String name, String lastName, String password, String email) {

    }

    @Override
    public void deleteUserById(String userId) {

    }

    @Override
    public void updateUser(String id, String name, String lastName, String password, String email) {

    }
}
