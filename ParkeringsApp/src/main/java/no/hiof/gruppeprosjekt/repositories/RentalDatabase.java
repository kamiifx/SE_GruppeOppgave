package no.hiof.gruppeprosjekt.repositories;

import jdk.vm.ci.meta.Local;
import no.hiof.gruppeprosjekt.model.ParkingSpace;
import no.hiof.gruppeprosjekt.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class RentalDatabase implements IRentalRepository{
    private IUserRepository userRepository;
    private IParkingSpaceRepository parkingSpaceRepository;
    String url;

    public RentalDatabase(IUserRepository userRepo, IParkingSpaceRepository parkingSpaceRepo, String url) {
        this.userRepository = userRepo;
        this.parkingSpaceRepository = parkingSpaceRepo;
        this.url = url;
        createRentalTable();
    }

    private void createRentalTable(){
        String parkingSql = "CREATE TABLE IF NOT EXISTS rental (\n"
                + " userid integer NOT NULL,\n"
                + " spaceid integer NOT NULL,\n"
                + " starttime  NOT NULL, \n"
                + " duration text NOT NULL,\n"
                + " rentalid integer NOT NULL PRIMARY KEY\n"
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
    public void createRentalAgreement(int rentalId,int userId, int spaceId, int duration) {
        User user = userRepository.getUserById(userId);

        ParkingSpace space = parkingSpaceRepository.getSpaceById(spaceId);
        parkingSpaceRepository.updateAvailability(space.getSpaceId(), (byte) 0);

        LocalTime startTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        String sql = "INSERT INTO rental VALUES(?,?,?,?,?)";

        try {
            Connection connect = DriverManager.getConnection(url);
            PreparedStatement preState = connect.prepareStatement(sql);
            preState.setInt(1,user.getId());
            preState.setInt(2,space.getSpaceId());
            preState.setString(3, startTime.format(formatter));
            preState.setInt(4, duration);
            preState.setInt(5, rentalId);
            preState.executeUpdate();
            connect.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        //Ved bruk av større servere kunne vi ha implementert en "sjekk" som kan sjekke tiden nå med start-tid + duration.
        //Dersom tiden har oversteget denne tiden ville plassens availability blitt endret til 1 og endret i databasen igjen
    }
}
