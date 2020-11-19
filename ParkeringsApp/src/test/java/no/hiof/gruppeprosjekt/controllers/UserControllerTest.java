package no.hiof.gruppeprosjekt.controllers;

import io.javalin.http.Context;
import no.hiof.gruppeprosjekt.model.User;
import no.hiof.gruppeprosjekt.repositories.AppUserDatabase;
import org.junit.Before;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static org.mockito.Mockito.*;

class UserControllerTest {
    Context ctx;
    AppUserDatabase userDBrepo;
    UserController userController;

    @BeforeEach
    public void setup() {
        ctx = mock(Context.class);
        userDBrepo = new AppUserDatabase("jdbc:sqlite:testappdb.sqlite");
        userController = new UserController(userDBrepo);

        userDBrepo.registerUser(500, "Ola", "Nordmann", "drossap123", "ola_nordmann@gmail.com");
    }

    @AfterEach
    public void teardown() {
        userDBrepo.deleteUser("500");
    }

    //////////////////////////////////
    //Tester kravet: Register.AsUser//
    //////////////////////////////////
    @Test
    public void user_is_registered() {
        when(ctx.formParam("name")).thenReturn("test");
        when(ctx.formParam("lastName")).thenReturn("testesen");
        when(ctx.formParam("password")).thenReturn("drossap");
        when(ctx.formParam("email")).thenReturn("test@gmail.com");
        userController.registerUser(ctx);
        verify(ctx).status(201);  //CREATED

        //Etter sammenligningen slettes brukeren
        User user = userDBrepo.getUserByMail("test@gmail.com");
        userDBrepo.deleteUser(Integer.toString(user.getId()));
    }

    //////////////////////////////////
    //Tester kravet: Register.AsUser//
    //////////////////////////////////
    @Test
    public void user_is_not_registered_because_a_field_is_missing() {
        //Bytt gjerne andre returnverdier til en tom string
        when(ctx.formParam("name")).thenReturn("test");
        when(ctx.formParam("lastName")).thenReturn("testesen");
        when(ctx.formParam("password")).thenReturn(""); //Passord er tomt
        when(ctx.formParam("email")).thenReturn("test@gmail.com");
        userController.registerUser(ctx);
        verify(ctx).status(417);  //EXPECTATION FAILED)
    }


    @Test
    public void user_login_correct_credentials() {
        when(ctx.formParam("email")).thenReturn("ola_nordmann@gmail.com");
        when(ctx.formParam("password")).thenReturn("drossap123");
        userController.loginUser(ctx);
        verify(ctx).status(200);  //OK
    }

    @Test
    public void user_login_invalid_password(){
        when(ctx.formParam("email")).thenReturn("ola_nordmann@gmail.com");
        when(ctx.formParam("password")).thenReturn("passord123"); //FEIL PASSORD
        userController.loginUser(ctx);
        verify(ctx).status(401);  //UNAUTHORIZED
    }

    @Test
    public void user_login_invalid_email_causing_nullpointerexception(){
        when(ctx.formParam("email")).thenReturn("ola_nordmann@live.com"); //Ingen i systemet har denne eposten. Da får vi NPE
        when(ctx.formParam("password")).thenReturn("drossap123");

        //Forventer at vi får en NullPointerException
        Assertions.assertThrows(NullPointerException.class, () -> {
            userController.loginUser(ctx);
        });
    }

    @Test
    public void get_single_user_correct() {
        when(ctx.formParam("userId")).thenReturn("500");
        userController.getSingleUser(ctx);
        verify(ctx).status(200);  //OK
    }

    @Test
    public void get_single_user_fail() {
        when(ctx.formParam("userId")).thenReturn("1"); //Denne brukeren finnes ikke og vil kaste en NPE

        //Forventer at vi får NullPointerException
        Assertions.assertThrows(NullPointerException.class, () -> {
            userController.getSingleUser(ctx);
        });
    }

    @Before
    public void create_user_for_change() {
        //Denne brukeren lages for å bli endret
        userDBrepo.registerUser(5, "Preben", "Andersen", "test123", "preben@gmail.com");

    }

    /////////////////////////////////////////
    //Tester kravet: User.ChangeInformation//
    /////////////////////////////////////////
    @Test
    public void update_user() {
        when(ctx.formParam("userId")).thenReturn("5");
        when(ctx.formParam("first_name")).thenReturn("IkkePreben");
        when(ctx.formParam("last_name")).thenReturn("IkkeAndersen");
        when(ctx.formParam("password")).thenReturn("drossap123");
        when(ctx.formParam("email")).thenReturn("test@gmail.com");
        userController.updateUser(ctx);
        verify(ctx).status(200); //OK
    }

    //Denne brukeren lages bare for å bli slettet i testen "delete_user"
    @Before
    public void create_new_user() {
        userDBrepo.registerUser(20, "Kent", "Rogersen", "kentisbentis123", "kent_rogersen@gmail.com");
    }

    //////////////////////////////
    //Tester kravet: Delete.User//
    //////////////////////////////
    @Test
    public void delete_user() {
        when(ctx.formParam("userId")).thenReturn("20");
        userController.deleteUser(ctx);
        verify(ctx).status(200);  //OK
    }

    //Hent alle brukere
    @Test
    public void get_all_users_success() {
        userController.getAllUsers(ctx);
        verify(ctx).status(200);  //OK
    }

    @Test
    public void get_all_users_fail() {
        String sql = "DELETE FROM users ";
        String url = "jdbc:sqlite:testappdb.sqlite";
        try {
            Connection connect = DriverManager.getConnection(url);
            PreparedStatement preState = connect.prepareStatement(sql);
            preState.executeUpdate();

            connect.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        userController.getAllUsers(ctx);
        verify(ctx).status(404);  //NOT FOUND
    }
}