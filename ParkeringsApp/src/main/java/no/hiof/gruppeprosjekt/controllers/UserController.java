package no.hiof.gruppeprosjekt.controllers;

import io.javalin.http.Context;
import no.hiof.gruppeprosjekt.model.User;
import no.hiof.gruppeprosjekt.repositories.IUserRepository;

import java.util.List;
import java.util.Random;

public class UserController {
    private IUserRepository userRepo;

    public UserController(IUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public void getAllUsers(Context context){
        List<User> getAllUser = userRepo.getAllUsers();
        context.json(getAllUser);
        if(getAllUser.isEmpty()) {
            context.status(404);
        } else {
            context.status(200);
        }
    }

    public void registerUser(Context context){
        Random rand = new Random();
        String name = context.formParam("name");
        String lastName = context.formParam("lastName");
        String password = context.formParam("password");
        String email = context.formParam("email");
        int id = rand.nextInt(100);
        if(name.equals("") || lastName.equals("") || password.equals("") || email.equals("")) {
            context.status(417);
        }

        else {
            userRepo.registerUser(id,name,lastName,password,email);
            context.status(201);
            context.redirect("/app/" + id);
        }
    }

    public void loginUser(Context context){
        String email = context.formParam("email");
        String password = context.formParam("password");
        int id = userRepo.getUserByMail(email).getId();

        if(userRepo.loginUser(email, password)) {
            context.status(200);
            context.redirect("/app/" + id);
        } else  {
            context.status(401);
            context.redirect("/");
        }

    }

    public void getSingleUser(Context context){
        String userId = context.pathParam("userId");

        if (userId == null)
            userId = context.formParam("userId");

        User getUser = userRepo.getUserById(Integer.parseInt(userId));
        if(Integer.parseInt(userId) == getUser.getId()) {
            context.status(200);
            context.json(getUser);
        }
    }

    public void updateUser(Context context) {
        String id = context.pathParam("userId");

        if (id == null)
            id = context.formParam("userId");

        String name = context.formParam("first_name");
        String lastName = context.formParam("last_name");
        String password = context.formParam("password");
        String email = context.formParam("email");
        userRepo.updateUser(id, name, lastName, password, email);
        context.status(200);
        context.redirect("/app/" + id);
    }

    public void deleteUser(Context context) {
        String user = context.pathParam("userId");

        if (user == null)
            user = context.formParam("userId");

        userRepo.deleteUser(user);
        context.status(200);
        context.redirect("/");
    }
}
