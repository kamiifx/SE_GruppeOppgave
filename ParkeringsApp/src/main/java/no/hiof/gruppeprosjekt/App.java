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
        /*AppUserJson userJson = new AppUserJson();
        AppUserJson userJsonRepo = new AppUserJson();*/
        AppUserDatabase userDBRepo = new AppUserDatabase();
        UserController userController = new UserController(userDBRepo);

        //Repo + controller for publisering av parkeringsplass
        ParkingSpaceDatabase parkingSpaceRepository = new ParkingSpaceDatabase(userDBRepo);
        ParkingSpaceController parkingSpaceController = new ParkingSpaceController(parkingSpaceRepository);

        RentalDatabase rentalDatabase = new RentalDatabase(userDBRepo, parkingSpaceRepository);
        RentalController rentalController = new RentalController(rentalDatabase);

        //KOBLE TIL DATABASE
        String url = "jdbc:sqlite:appdb.sqlite";
        connectDB(url);

        Javalin app = Javalin.create().start(7000);
        app.config.enableWebjars();

        //login side
        app.get("/", new VueComponent("<app-login><app-login>"));
        app.get("/api/users", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                userController.getAllUsers(ctx);
            }
        });
        app.post("/api/login", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                userController.loginUser(ctx);
            }
        });
        app.post("/api/register", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                userController.registerUser(ctx);
            }
        });

        //app user Page
        app.get("/app/:userId",new VueComponent("app"));
        app.get("/api/users/:userId", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                userController.getSingleUser(ctx);
            }
        });
        //Side for publisering av parkeringsplasser
        app.get("/app/:userId/publish-parkingspace", new VueComponent("publish-parkingspace"));
        app.post("/api/app/:userId/publish_parkingspace", parkingSpaceController::createParkingSpace);

        app.get("/app/:userId/user-update", new VueComponent("user-update"));
        app.post("/api/:userId/user-update", userController::updateUser);

        //Handler for å hente alle parkeringsplasser
        app.get("/api/parking-spaces", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                parkingSpaceController.getAllSpaces(ctx);
            }
        });
        app.get("/app/:userId/parkingspaces", new VueComponent("parking-spaces-overview"));

        //Handler for å hente en spesifikk parkeringsplass
        app.get("/api/parking-spaces/:spaceId", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                parkingSpaceController.getSingleSpace(ctx);
            }
        });
        app.get("/app/:userId/parkingspaces/:spaceId", new VueComponent("parking-space-detail"));
        app.post("/api/app/:userId/parkingspaces/:spaceId/rentspace", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                rentalController.createRentalAgreement(ctx);
            }
        });

        app.get("/api/:userId/delete", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                userController.deleteUser(ctx);
            }
        });
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
