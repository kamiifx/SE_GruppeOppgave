package no.hiof.gruppeprosjekt;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.plugin.rendering.vue.VueComponent;
import org.jetbrains.annotations.NotNull;


public class App {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);
        app.config.enableWebjars();



        //login side
        app.get("/", new VueComponent("app-login"));
    }
}
