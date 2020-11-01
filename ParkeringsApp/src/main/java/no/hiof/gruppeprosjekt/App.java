package no.hiof.gruppeprosjekt;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.plugin.rendering.vue.VueComponent;
import no.hiof.gruppeprosjekt.controllers.UserController;
import no.hiof.gruppeprosjekt.repositories.AppUserJson;
import org.jetbrains.annotations.NotNull;


public class App {
    public static void main(String[] args) {
        AppUserJson userJson = new AppUserJson();
        AppUserJson userJsonRepo = new AppUserJson();
        UserController userController = new UserController(userJsonRepo);


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
    }
}
