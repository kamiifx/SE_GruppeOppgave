package no.hiof.gruppeprosjekt;

import no.hiof.gruppeprosjekt.model.User;
import no.hiof.gruppeprosjekt.repositories.AppUserDatabase;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class AppUserDatabaseTest {
    AppUserDatabase userDB = new AppUserDatabase();

    @Test
    public void user_is_registered_and_added_to_db() {
        User ola = new User();
        ola.setId(10);
        ola.setName("Ola");
        ola.setLastName("Nordmannnn");
        ola.setPassword("drossap");
        ola.setMail("ola_nordmann@gmail.com");

        //Brukerne sammenlignes med ID (Dette er en attributt som er unik for alle brukere)
        userDB.registerUser(ola.getId(), ola.getName(), ola.getLastName(), ola.getPassword(), ola.getMail());
        Assert.assertEquals(ola.getId(), userDB.getUserById(10).getId());
    }
}