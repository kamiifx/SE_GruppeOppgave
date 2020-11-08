package no.hiof.gruppeprosjekt.repositories;

import no.hiof.gruppeprosjekt.model.User;

import java.util.ArrayList;

public interface IUserRepository {
    ArrayList<User> getAllUsers();
    User getUserByMail(String email);
    User getUserById(int id);


    void loginUser(String email, String password);
    void registerUser(int id,String name,String lastName,String password, String email);
    void deleteUser(String email);
    void updateUser(String name,String lastName,String password, String email);

    void deleteUserById(String userId);
}
