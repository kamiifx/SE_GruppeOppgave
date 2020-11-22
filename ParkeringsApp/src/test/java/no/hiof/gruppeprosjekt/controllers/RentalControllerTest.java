package no.hiof.gruppeprosjekt.controllers;

import io.javalin.http.Context;
import no.hiof.gruppeprosjekt.repositories.AppUserDatabase;
import no.hiof.gruppeprosjekt.repositories.ParkingSpaceDatabase;
import no.hiof.gruppeprosjekt.repositories.RentalDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class RentalControllerTest {
    //Tester klassen RentalController

    private AppUserDatabase userDBrepo;
    private ParkingSpaceDatabase parkingDBrepo;
    private RentalDatabase rentalDBrepo;
    private RentalController rentalController;

    @Mock
    private Context ctx = mock(Context.class);

    @BeforeEach
    public void setup() {
        userDBrepo = new AppUserDatabase("jdbc:sqlite:testappdb.sqlite");
        parkingDBrepo = new ParkingSpaceDatabase(userDBrepo,"jdbc:sqlite:testappdb.sqlite");
        rentalDBrepo = new RentalDatabase(userDBrepo, parkingDBrepo, "jdbc:sqlite:testappdb.sqlite");
        rentalController = new RentalController(rentalDBrepo);

        //Ola legger ut en plass og leier den
        //Det høres ikke helt logisk ut, men for å holde
        //testen simpelt så lager vi bare en plass og en bruker for en leie-avtale
        userDBrepo.registerUser(300, "Ole", "Olsen", "ole123", "ole@gmail.com");
        parkingDBrepo.createParkingSpace(450, "Halden", "Oskleiva 10", "3", "50", "300");
    }

    @AfterEach
    public void teardown() {
        userDBrepo.deleteUser("300");

        String sql = "DELETE FROM rental ";
        String sql2 = "DELETE FROM parkingspace";
        String url = "jdbc:sqlite:testappdb.sqlite";
        try {
            Connection connect = DriverManager.getConnection(url);
            PreparedStatement preState = connect.prepareStatement(sql);
            PreparedStatement preState2 = connect.prepareStatement(sql2);
            preState.executeUpdate();
            preState2.executeUpdate();

            connect.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    ///////////////////////////////////
    //Tester kravet: User.RentParking//
    ///////////////////////////////////
    @Test
    public void create_a_rental_agreement_success() {
        when(ctx.formParam("userId")).thenReturn("300");
        when(ctx.formParam("spaceId")).thenReturn("450");
        when(ctx.formParam("duration")).thenReturn("2");
        rentalController.createRentalAgreement(ctx);
        verify(ctx).status(201);  //CREATED
    }

    ///////////////////////////////////
    //Tester kravet: User.RentParking//
    ///////////////////////////////////
    @Test
    public void create_a_rental_agreement_fails_because_no_duration() {
        when(ctx.formParam("userId")).thenReturn("300");
        when(ctx.formParam("spaceId")).thenReturn("450");
        when(ctx.formParam("duration")).thenReturn(""); //Ingen bestemt lengde på parkeringen
        rentalController.createRentalAgreement(ctx);
        verify(ctx).status(417);  //EXPECTATION FAILED
    }


}
