package no.hiof.gruppeprosjekt.controllers;

import io.javalin.http.Context;
import no.hiof.gruppeprosjekt.model.User;
import no.hiof.gruppeprosjekt.repositories.IUserRepository;

import java.util.List;

public class UserController {
    private IUserRepository userJsonRepo;

    public UserController(IUserRepository userJsonRepo){this.userJsonRepo = userJsonRepo;}

    public void getAllUsers(Context context){
        List<User> getAllUser = userJsonRepo.getAllUsers();
        context.json(getAllUser);
    }

    public void registerUser(Context context){
        //String name, String lastName, String password, String email
        String name = context.formParam("name");
        String lastName = context.formParam("lastName");
        String password = context.formParam("password");
        String email = context.formParam("email");
        userJsonRepo.registerUser(name,lastName,password,email);

        context.redirect("/api/users");
    }

}
