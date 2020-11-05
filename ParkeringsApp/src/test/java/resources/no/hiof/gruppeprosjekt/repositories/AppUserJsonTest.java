package no.hiof.gruppeprosjekt.repositories;

import no.hiof.gruppeprosjekt.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppUserJsonTest {
    User user;
    @BeforeEach
    public void setup(){
        user = new User();
    }

    @Test
    void TestUserlogin(String email, String password) {
        boolean emailTrue = false;
        boolean passwordTrue = false;
        if (email.equals(user.getMail()) && password.equals(user.getPassword())){
            emailTrue = true;
            passwordTrue = true;
        }
        assertTrue(emailTrue);
    }

}