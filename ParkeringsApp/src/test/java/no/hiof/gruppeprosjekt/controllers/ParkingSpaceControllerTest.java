package no.hiof.gruppeprosjekt.controllers;

import io.javalin.core.validation.JavalinValidation;
import io.javalin.core.validation.Validator;
import io.javalin.http.Context;
import no.hiof.gruppeprosjekt.model.User;
import no.hiof.gruppeprosjekt.repositories.AppUserDatabase;
import no.hiof.gruppeprosjekt.repositories.ParkingSpaceDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.misusing.WrongTypeOfReturnValue;
import org.mockito.junit.MockitoJUnit;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkingSpaceControllerTest {
    private AppUserDatabase userDBrepo;
    private ParkingSpaceDatabase parkingDBrepo;
    private ParkingSpaceController parkingController;
    @Mock
    private Context ctx = mock(Context.class);


    @BeforeEach
    public void setup() {
        userDBrepo = new AppUserDatabase("jdbc:sqlite:appdb.sqlite");
        parkingDBrepo = new ParkingSpaceDatabase(userDBrepo,"jdbc:sqlite:appdb.sqlite");
        parkingController = new ParkingSpaceController(parkingDBrepo);

        userDBrepo.registerUser(200, "Tanja", "Gaup", "tanja123", "tanja_gaup@gmail.com");
    }

    @AfterEach
    public void teardown() {
        userDBrepo.deleteUser("200");

        String sql = "DELETE FROM parkingspace WHERE byuser = 200";
        String url = "jdbc:sqlite:appdb.sqlite";
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
    public void create_parkingSpace_success(){
        when(ctx.formParam("userId")).thenReturn("200");

        when(ctx.formParam("city")).thenReturn("Halden");
        when(ctx.formParam("address")).thenReturn("Generalveien 27");
        when(ctx.formParam("size_sqm")).thenReturn("4");
        when(ctx.formParam("price_ph")).thenReturn("100");
        parkingController.createParkingSpace(ctx);
        verify(ctx).status(201);
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
        verify(ctx).status(417);
    }

}