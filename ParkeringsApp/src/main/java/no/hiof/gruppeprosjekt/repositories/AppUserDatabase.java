package no.hiof.gruppeprosjekt.repositories;
import java.sql.*;

public class AppUserDatabase {
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

}
