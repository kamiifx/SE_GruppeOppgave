package no.hiof.gruppeprosjekt.controllers;

import io.javalin.http.Context;
import no.hiof.gruppeprosjekt.model.User;
import no.hiof.gruppeprosjekt.repositories.IUserRepository;

import java.util.List;
import java.util.Random;

public class UserController {
    private IUserRepository userRepo;

    public UserController(IUserRepository userRepo){this.userRepo = userRepo;}

    public void getAllUsers(Context context){
        List<User> getAllUser = userRepo.getAllUsers();
        context.json(getAllUser);
    }

    public void registerUser(Context context){
        Random rand = new Random();
        //String name, String lastName, String password, String email
        String name = context.formParam("name");
        String lastName = context.formParam("lastName");
        String password = context.formParam("password");
        String email = context.formParam("email");
        int id = rand.nextInt(100);
        userRepo.registerUser(id,name,lastName,password,email);

        context.redirect("/app/" + id);
    }

    public void loginUser(Context context){
        String email = context.formParam("email");
        String password = context.formParam("password");
        int id = userRepo.getUserByMail(email).getId();

        if(userRepo.loginUser(email, password)) {
            context.redirect("/app/" + id);
        } else context.redirect("/");

    }

    public void getSingleUser(Context context){
        String userId = context.pathParam("userId");
        User getUser = userRepo.getUserById(Integer.parseInt(userId));
        context.json(getUser);
    }

    public void updateUser(Context context) {
        String id = context.pathParam("userId");
        String name = context.formParam("first_name");
        String lastName = context.formParam("last_name");
        String password = context.formParam("password");
        String email = context.formParam("email");

        userRepo.updateUser(id, name, lastName, password, email);

        context.redirect("/app/" + id);
    }

    public void deleteUser(Context ctx) {
        String user = ctx.pathParam("userId");
        userRepo.deleteUser(user);
        ctx.redirect("/");
    }
}
