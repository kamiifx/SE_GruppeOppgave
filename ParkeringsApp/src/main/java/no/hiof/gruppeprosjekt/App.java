package no.hiof.gruppeprosjekt;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.plugin.rendering.vue.VueComponent;
import no.hiof.gruppeprosjekt.controllers.ParkingSpaceController;
import no.hiof.gruppeprosjekt.controllers.RentalController;
import no.hiof.gruppeprosjekt.controllers.UserController;
import no.hiof.gruppeprosjekt.repositories.AppUserDatabase;
import no.hiof.gruppeprosjekt.repositories.ParkingSpaceDatabase;
import no.hiof.gruppeprosjekt.repositories.RentalDatabase;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;


public class App {
    public static void main(String[] args) throws ParseException {
        //Repo + controller for brukere
        AppUserDatabase userDBRepo = new AppUserDatabase("jdbc:sqlite:appdb.sqlite");
        UserController userController = new UserController(userDBRepo);

        //Repo + controller for parkeringsplasser
        ParkingSpaceDatabase parkingSpaceRepository = new ParkingSpaceDatabase(userDBRepo, "jdbc:sqlite:appdb.sqlite");
        ParkingSpaceController parkingSpaceController = new ParkingSpaceController(parkingSpaceRepository);

        //Repo + controller for leie-avtaler
        RentalDatabase rentalDatabase = new RentalDatabase(userDBRepo, parkingSpaceRepository, "jdbc:sqlite:appdb.sqlite");
        RentalController rentalController = new RentalController(rentalDatabase);

        String url = "jdbc:sqlite:appdb.sqlite";
        connectDB(url);

        Javalin app = Javalin.create().start(7000);
        app.config.enableWebjars();

        app.get("/", new VueComponent("<app-login><app-login>"));
        app.get("/api/users", userController::getAllUsers);
        app.post("/api/login", userController::loginUser);
        app.post("/api/register", userController::registerUser);
        app.get("/app/:userId",new VueComponent("app"));
        app.get("/api/users/:userId", userController::getSingleUser);
        app.get("/app/:userId/publish-parkingspace", new VueComponent("publish-parkingspace"));
        app.post("/api/app/:userId/publish_parkingspace", parkingSpaceController::createParkingSpace);
        app.get("/app/:userId/user-update", new VueComponent("user-update"));
        app.post("/api/:userId/user-update", userController::updateUser);
        app.get("/api/parking-spaces", parkingSpaceController::getAllSpaces);
        app.get("/app/:userId/parkingspaces", new VueComponent("parking-spaces-overview"));
        app.get("/api/parking-spaces/:spaceId", parkingSpaceController::getSingleSpace);
        app.get("/app/:userId/parkingspaces/:spaceId", new VueComponent("parking-space-detail"));
        app.post("/api/app/:userId/parkingspaces/:spaceId/rentspace", rentalController::createRentalAgreement);
        app.get("/api/:userId/delete", userController::deleteUser);
    }

    public static void connectDB(String url) {
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
}
