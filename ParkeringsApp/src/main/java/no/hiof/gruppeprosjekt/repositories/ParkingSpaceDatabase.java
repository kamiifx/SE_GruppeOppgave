package no.hiof.gruppeprosjekt.repositories;

import no.hiof.gruppeprosjekt.model.ParkingSpace;
import no.hiof.gruppeprosjekt.model.User;

import java.sql.*;
import java.util.ArrayList;

public class ParkingSpaceDatabase implements IParkingSpaceRepository {
    private IUserRepository userRepository;
    String url;

    public ParkingSpaceDatabase(IUserRepository userRepository, String url) {
        this.userRepository = userRepository;
        this.url = url;
        createParkingSpaceTable();
    }

    private void createParkingSpaceTable(){
        String parkingSql = "CREATE TABLE IF NOT EXISTS parkingspace (\n"
                + " spaceid INT PRIMARY KEY,\n"
                + " city text NOT NULL,\n"
                + " address text NOT NULL,\n"
                + " sizesqm double NOT NULL, \n"
                + " priceph double NOT NULL, \n"
                + " byuser INT NOT NULL, \n"
                + " available BIT NOT NULL \n"
                + ");";
        try {
            Connection connect = DriverManager.getConnection(url);
            Statement state = connect.createStatement();
            state.execute(parkingSql);
            connect.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<ParkingSpace> getAllSpaces() {
        String sql = "SELECT * FROM parkingspace";
        ArrayList<ParkingSpace> parkingSpaces = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                ParkingSpace operator = new ParkingSpace();
                operator.setSpaceId(rs.getInt("spaceid"));
                operator.setCity(rs.getString("city"));
                operator.setAddress(rs.getString("address"));
                operator.setSize_sqm(rs.getDouble("sizesqm"));
                operator.setPrice_ph(rs.getDouble("priceph"));
                operator.setByUser(userRepository.getUserById(rs.getInt("byuser")));
                operator.setAvailable(rs.getByte("available"));
                if(operator.getAvailable() == 1) {
                    parkingSpaces.add(operator);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return parkingSpaces;
    }

    @Override
    public ParkingSpace getSpaceById(int id) {
        String sql = "SELECT * FROM parkingspace WHERE spaceid = ?";

        try {
            Connection connect = DriverManager.getConnection(url);
            PreparedStatement preState = connect.prepareStatement(sql);
            preState.setDouble(1, id);
            ResultSet rs = preState.executeQuery();

            ParkingSpace operator = new ParkingSpace();
            operator.setSpaceId(rs.getInt("spaceid"));
            operator.setCity(rs.getString("city"));
            operator.setAddress(rs.getString("address"));
            operator.setSize_sqm(rs.getDouble("sizesqm"));
            operator.setPrice_ph(rs.getDouble("priceph"));
            operator.setByUser(userRepository.getUserById(rs.getInt("byuser")));
            operator.setAvailable(rs.getByte("available"));
            connect.close();
            return operator;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //KRAV: User.PublishSpace
    @Override
    public void createParkingSpace(int spaceId, String city, String address, String size_sqm, String price_ph, String userId) {
        User byUser = userRepository.getUserById(Integer.parseInt(userId));
        String sql = "INSERT INTO parkingspace VALUES(?,?,?,?,?,?,?)";

        try {
            Connection connect = DriverManager.getConnection(url);
            PreparedStatement preState = connect.prepareStatement(sql);
            preState.setInt(1,spaceId);
            preState.setString(2,city);
            preState.setString(3,address);
            preState.setDouble(4,Double.parseDouble(size_sqm));
            preState.setDouble(5,Double.parseDouble(price_ph));
            preState.setInt(6,byUser.getId());
            preState.setByte(7, (byte) 1);
            preState.executeUpdate();
            connect.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateAvailability(int id, byte notAvailable) {
        ParkingSpace space = getSpaceById(id);
        space.setAvailable(notAvailable);

        String sql = "UPDATE parkingspace SET available = ? WHERE spaceid = ?";

        try {
            Connection connect = DriverManager.getConnection(url);
            PreparedStatement preState = connect.prepareStatement(sql);
            preState.setInt(1, notAvailable);
            preState.setInt(2, id);
            preState.executeUpdate();
            connect.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
