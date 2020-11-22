package no.hiof.gruppeprosjekt.repositories;

import no.hiof.gruppeprosjekt.model.ParkingSpace;
import no.hiof.gruppeprosjekt.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ParkingSpaceDatabaseTest {
    //TESTER KLASSEN ParkingSpaceDatabase

    AppUserDatabase userDB = new AppUserDatabase("jdbc:sqlite:testappdb.sqlite");
    ParkingSpaceDatabase parkingDB = new ParkingSpaceDatabase(userDB, "jdbc:sqlite:testappdb.sqlite");

    User ola;
    ParkingSpace rodHerregaard;
    ParkingSpace haldenIshall;

    @BeforeEach
    public void setup() {
        ola = new User(1, "Ola", "Nordmann", "passord123", "ola_nordmann@gmail.com");
        userDB.registerUser(ola.getId(), ola.getName(), ola.getLastName(), ola.getPassword(), ola.getMail());

        rodHerregaard = new ParkingSpace(1,"Halden", "Rod Herregaard", 3, 75, ola);
        haldenIshall = new ParkingSpace(2,"Halden", "Blokkeveien 39", 4, 50, ola);

        parkingDB.createParkingSpace(rodHerregaard.getSpaceId(),rodHerregaard.getCity(), rodHerregaard.getAddress(), Double.toString(rodHerregaard.getSize_sqm()), Double.toString(rodHerregaard.getPrice_ph()), Integer.toString(rodHerregaard.getByUser().getId()));
        parkingDB.createParkingSpace(haldenIshall.getSpaceId(),haldenIshall.getCity(), haldenIshall.getAddress(), Double.toString(haldenIshall.getSize_sqm()), Double.toString(haldenIshall.getPrice_ph()), Integer.toString(haldenIshall.getByUser().getId()));
    }

    @AfterEach
    public void teardown() {
        userDB.deleteUser("1");

        String sql = "DELETE FROM parkingspace";
        String url = "jdbc:sqlite:testappdb.sqlite";
        try {
            Connection connect = DriverManager.getConnection(url);
            PreparedStatement preState = connect.prepareStatement(sql);
            preState.executeUpdate();

            connect.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void get_all_correct_parkingspaces() {
        ArrayList<ParkingSpace> expectedSpaces = new ArrayList<>();
        expectedSpaces.add(rodHerregaard);
        expectedSpaces.add(haldenIshall);

        ArrayList<ParkingSpace> actualSpaces = new ArrayList<>(parkingDB.getAllSpaces());

        Assert.assertEquals(expectedSpaces.toString(), actualSpaces.toString());

    }

    @Test
    public void get_space_by_correct_id() {
        //Rød herregård ID = 1
        Assert.assertEquals(rodHerregaard.getSpaceId(), parkingDB.getSpaceById(1).getSpaceId());
    }

    @Test
    public void get_null_by_invalid_id() {
        //Ingen parkeringsplass har id 200
        Assert.assertNull(parkingDB.getSpaceById(200));
    }

    ////////////////////////////////////
    //Tester kravet: User.PublishSpace//
    ////////////////////////////////////
    @Test
    public void space_is_registered_and_added_to_db() {
        ParkingSpace bakgaarden = new ParkingSpace();
        bakgaarden.setSpaceId(3);
        bakgaarden.setCity("Halden");
        bakgaarden.setAddress("Storgata 22B");
        bakgaarden.setSize_sqm(2);
        bakgaarden.setPrice_ph(40);
        bakgaarden.setByUser(ola);

        parkingDB.createParkingSpace(bakgaarden.getSpaceId(), bakgaarden.getCity(), bakgaarden.getAddress(), Double.toString(bakgaarden.getSize_sqm()), Double.toString(bakgaarden.getPrice_ph()), Integer.toString(bakgaarden.getByUser().getId()));

        Assert.assertEquals(bakgaarden.getSpaceId(), parkingDB.getSpaceById(3).getSpaceId());

        //Plassen slettes ETTER sammenligningen slik at resten av testene ikke må forholde seg til denne plassen også
        String sql = "DELETE FROM parkingspace WHERE spaceid = 3";
        try {
            Connection connect = DriverManager.getConnection("jdbc:sqlite:testappdb.sqlite");
            PreparedStatement preState = connect.prepareStatement(sql);
            preState.executeUpdate();
            connect.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Tester bare at plassen ikke er tilgjengelig etter leie
    @Test
    public void space_is_updated_also_rented_and_is_not_available_now() {
        //Vi ser for oss at en ny bruker leier parkeringsplassen "Halden Ishall". Denne skal da ikke være available, altså false på available
        //Id'en til Halden ishall = 2

        /*Siden sqlite ikke forholder seg til true or false så bruker vi bytes. Byte 0 = false, Byte 1 = true*/

        //Plassen er tilgjengelig
        Assert.assertEquals(parkingDB.getSpaceById(2).isAvailable(), 1);

        //Plassen leies
        parkingDB.updateAvailability(2, (byte) 0);

        //Plassen er ikke tilgjengelig lenger
        Assert.assertEquals(parkingDB.getSpaceById(2).isAvailable(), 0);
    }
}
