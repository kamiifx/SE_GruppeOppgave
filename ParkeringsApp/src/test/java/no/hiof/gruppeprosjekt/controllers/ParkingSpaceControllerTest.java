package no.hiof.gruppeprosjekt.controllers;

import io.javalin.http.Context;
import no.hiof.gruppeprosjekt.repositories.AppUserDatabase;
import no.hiof.gruppeprosjekt.repositories.ParkingSpaceDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static org.mockito.Mockito.*;

class ParkingSpaceControllerTest {
    private AppUserDatabase userDBrepo;
    private ParkingSpaceDatabase parkingDBrepo;
    private ParkingSpaceController parkingController;

    @Mock
    private Context ctx = mock(Context.class);

    @BeforeEach
    public void setup() {
        userDBrepo = new AppUserDatabase("jdbc:sqlite:testappdb.sqlite");
        parkingDBrepo = new ParkingSpaceDatabase(userDBrepo,"jdbc:sqlite:testappdb.sqlite");
        parkingController = new ParkingSpaceController(parkingDBrepo);

        //Tanja kommer til å legge ut alt for denne testen
        userDBrepo.registerUser(200, "Tanja", "Gaup", "tanja123", "tanja_gaup@gmail.com");
        parkingDBrepo.createParkingSpace(150, "Halden", "Generalgata 7", "100", "150", "200");
    }

    @AfterEach
    public void teardown() {
        userDBrepo.deleteUser("200");

        String sql = "DELETE FROM parkingspace WHERE byuser = 200";
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
    public void get_all_spaces_success() {
        parkingController.getAllSpaces(ctx);
        verify(ctx).status(200);  //OK
    }

    @Test //FEIL HER
    public void get_all_spaces_fail() {
        //Sørger for at det ikke er noen plasser
        String sql = "DELETE FROM parkingspace ";
        String url = "jdbc:sqlite:testappdb.sqlite";
        try {
            Connection connect = DriverManager.getConnection(url);
            PreparedStatement preState = connect.prepareStatement(sql);
            preState.executeUpdate();

            connect.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        parkingController.getAllSpaces(ctx);
        verify(ctx).status(404);  //NOT FOUND
    }

    @Test
    public void get_single_space_success() {
        when(ctx.formParam("spaceId")).thenReturn("150");
        parkingController.getSingleSpace(ctx);
        verify(ctx).status(200);  //OK
    }

    @Test
    public void get_single_space_fail() {
        when(ctx.formParam("spaceId")).thenReturn("99");

        //Forventer at vi får en NullPointerException når vi prøver å hente en plass som ikke finnes
        Assertions.assertThrows(NullPointerException.class, () -> {
            parkingController.getSingleSpace(ctx);
        });
    }

    @Test
    public void create_parkingSpace_success(){
        when(ctx.formParam("userId")).thenReturn("200");

        when(ctx.formParam("city")).thenReturn("Halden");
        when(ctx.formParam("address")).thenReturn("Generalveien 27");
        when(ctx.formParam("size_sqm")).thenReturn("4");
        when(ctx.formParam("price_ph")).thenReturn("100");
        parkingController.createParkingSpace(ctx);
        verify(ctx).status(201);  //CREATED
    }

    @Test
    public void create_parkingSpace_fail_because_of_missing_field() {
        when(ctx.formParam("userId")).thenReturn("200");

        //Bytt gjerne andre retur-verdier til en tom string for å sjekke alle feltene
        when(ctx.formParam("city")).thenReturn("Halden");
        when(ctx.formParam("address")).thenReturn(""); //Ingen adresse
        when(ctx.formParam("size_sqm")).thenReturn("3");
        when(ctx.formParam("price_ph")).thenReturn("120");
        parkingController.createParkingSpace(ctx);
        verify(ctx).status(417);  //EXPECTATION FAILED
    }

}