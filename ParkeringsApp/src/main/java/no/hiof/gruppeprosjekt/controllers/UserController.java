package no.hiof.gruppeprosjekt.controllers;

import io.javalin.http.Context;
import no.hiof.gruppeprosjekt.model.User;
import no.hiof.gruppeprosjekt.repositories.IUserRepository;

import java.util.List;
import java.util.Random;

public class UserController {
    private IUserRepository userJsonRepo;

    public UserController(IUserRepository userJsonRepo){this.userJsonRepo = userJsonRepo;}

    public void getAllUsers(Context context){
        List<User> getAllUser = userJsonRepo.getAllUsers();
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
        userJsonRepo.registerUser(id,name,lastName,password,email);

        context.redirect("/api/users");
    }

    public void loginUser(Context context){
        String email = context.formParam("email");
        int id = userJsonRepo.getUserByMail(email).getId();


        context.redirect("/app/" + id);
    }

}
