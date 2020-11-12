package no.hiof.gruppeprosjekt.repositories;
import no.hiof.gruppeprosjekt.App;
import no.hiof.gruppeprosjekt.model.User;

import java.sql.*;
import java.util.ArrayList;

public class AppUserDatabase implements IUserRepository {
    private User activeUser;
    private Boolean loggedin = false;
    String url = "jdbc:sqlite:appdb.sqlite";

    public AppUserDatabase() {
        createUserTable();
    }

    public void createUserTable(){
        String userSql = "CREATE TABLE IF NOT EXISTS users (\n"
                + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL,\n"
                + " lastname text NOT NULL,\n"
                + " password text NOT NULL, \n"
                + " email text NOT NULL \n"
                + ");";

        try {
            Connection connect = DriverManager.getConnection(url);
            Statement state = connect.createStatement();
            state.execute(userSql);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        ArrayList<User> users = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                User operator = new User();
                operator.setId(rs.getInt("id"));
                operator.setName(rs.getString("name"));
                operator.setLastName(rs.getString("lastname"));
                operator.setPassword(rs.getString("password"));
                operator.setMail(rs.getString("email"));
                users.add(operator);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    @Override
    public User getUserByMail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try {
            Connection connect = DriverManager.getConnection(url);
            PreparedStatement preState = connect.prepareStatement(sql);
            preState.setString(1, email);
            ResultSet rs = preState.executeQuery();

            User operator = new User();
            operator.setId(rs.getInt("id"));
            operator.setName(rs.getString("name"));
            operator.setLastName(rs.getString("lastname"));
            operator.setPassword(rs.getString("password"));
            operator.setMail(rs.getString("email"));
            connect.close();
            return operator;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try {
            Connection connect = DriverManager.getConnection(url);
            PreparedStatement preState = connect.prepareStatement(sql);
            preState.setDouble(1, id);
            ResultSet rs = preState.executeQuery();

            User operator = new User();
            operator.setId(rs.getInt("id"));
            operator.setName(rs.getString("name"));
            operator.setLastName(rs.getString("lastname"));
            operator.setPassword(rs.getString("password"));
            operator.setMail(rs.getString("email"));
            connect.close();
            return operator;
        }catch (SQLException e){
            System.out.println(e.getMessage());
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
    public void registerUser(int id, String name, String lastName, String password, String email) {
        String sql = "INSERT INTO users VALUES(?,?,?,?,?)";

        try {
            Connection connect = DriverManager.getConnection(url);
            PreparedStatement preState = connect.prepareStatement(sql);
            preState.setInt(1,id);
            preState.setString(2,name);
            preState.setString(3,lastName);
            preState.setString(4,password);
            preState.setString(5,email);
            preState.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteUser(String userId) {
        String sql = "DELETE FROM users WHERE id = ?";

        try {
            Connection connect = DriverManager.getConnection(url);
            PreparedStatement preState = connect.prepareStatement(sql);
            preState.setInt(1, Integer.parseInt(userId));
            preState.executeUpdate();

            connect.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateUser(String id, String name, String lastName, String password, String email) {
        String sql = "UPDATE users SET name = ?, lastname = ?, password = ?, email = ? WHERE id = ?";
        try {
            Connection connect = DriverManager.getConnection(url);
            PreparedStatement preState = connect.prepareStatement(sql);
            preState.setString(1, name);
            preState.setString(2, lastName);
            preState.setString(3, password);
            preState.setString(4, email);
            preState.setInt(5, Integer.parseInt(id));
            preState.executeUpdate();

            connect.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


}
