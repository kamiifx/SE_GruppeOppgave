package no.hiof.gruppeprosjekt.repositories;

import no.hiof.gruppeprosjekt.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class AppUserDatabaseTest {

    AppUserDatabase userDB = new AppUserDatabase("jdbc:sqlite:testappdb.sqlite");

    @BeforeEach

    @Test
    public void get_all_correct_users() {
        User ola = new User(1, "Ola", "Nordmann", "passord123", "ola_nordmann@gmail.com");
        userDB.registerUser(ola.getId(), ola.getName(), ola.getLastName(), ola.getPassword(), ola.getMail());

        User kari = new User(2, "Kari", "Nordmann", "drossap", "kari@gmail.com");
        userDB.registerUser(kari.getId(), kari.getName(), kari.getLastName(), kari.getPassword(), kari.getMail());

        /*I databasen har jeg nå brukerne ola og Kari, disse er også brukere som jeg forventer så jeg legger dem til en liste
         * og sammenligner dem med den listen jeg får når jeg kaller på funksjonen som hente ralle brukeren som ligger i databasen*/

        ArrayList<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(ola);
        expectedUsers.add(kari);

        ArrayList<User> actualUsers = new ArrayList<>(userDB.getAllUsers());

        Assert.assertEquals(expectedUsers.toString(), actualUsers.toString());

        userDB.deleteUser("1");
        userDB.deleteUser("2");
    }

    @Test
    public void get_correct_user_by_mail() {
        User ola = new User(1, "Ola", "Nordmann", "passord123", "ola_nordmann@gmail.com");
        userDB.registerUser(ola.getId(), ola.getName(), ola.getLastName(), ola.getPassword(), ola.getMail());

        Assert.assertEquals(ola.getId(), userDB.getUserByMail("ola_nordmann@gmail.com").getId());

        userDB.deleteUser("1");
    }

    @Test
    public void get_nothing_by_getting_user_with_invalid_mail() {
        User ola = new User(1, "Ola", "Nordmann", "passord123", "ola_nordmann@gmail.com");
        userDB.registerUser(ola.getId(), ola.getName(), ola.getLastName(), ola.getPassword(), ola.getMail());

        Assert.assertNull(userDB.getUserByMail("ola_nordmann@hotmail.com"));

        userDB.deleteUser("1");
    }

    @Test
    public void get_correct_user_by_id() {
        User kari = new User(2, "Kari", "Nordmann", "drossap", "kari@gmail.com");
        userDB.registerUser(kari.getId(), kari.getName(), kari.getLastName(), kari.getPassword(), kari.getMail());

        Assert.assertEquals(kari.getName(), userDB.getUserById(2).getName());
        userDB.deleteUser("2");
    }

    @Test
    public void get_nothing_by_getting_user_with_invalid_id() {
        User kari = new User(2, "Kari", "Nordmann", "drossap", "kari@gmail.com");
        userDB.registerUser(kari.getId(), kari.getName(), kari.getLastName(), kari.getPassword(), kari.getMail());

        //Databasen inneholder bare Kari med id 2, jeg vil hente 10 (som ikke finnes) og da får jeg NULL
        Assert.assertNull(userDB.getUserById(10));

        userDB.deleteUser("2");
    }

    @Test
    public void user_logged_in_with_correct_credentials() {
        User kari = new User(2, "Kari", "Nordmann", "drossap", "kari@gmail.com");
        userDB.registerUser(kari.getId(), kari.getName(), kari.getLastName(), kari.getPassword(), kari.getMail());

        String emailInput = "kari@gmail.com";
        String passordInput = "drossap";

        Assert.assertTrue(userDB.loginUser(emailInput, passordInput));

        userDB.deleteUser("2");
    }

    @Test
    public void user_logged_in_with_invalid_credentials() {
        User kari = new User(2, "Kari", "Nordmann", "drossap", "kari@gmail.com");
        userDB.registerUser(kari.getId(), kari.getName(), kari.getLastName(), kari.getPassword(), kari.getMail());

        String emailInput = "kari@gmail.com";
        String passordInput = "FeilPassord"; //Passordet til Kari er "drossap"

        Assert.assertFalse(userDB.loginUser(emailInput, passordInput));

        userDB.deleteUser("2");
    }

    @Test
    public void user_is_registered_and_added_to_db() {
        User ola = new User();
        ola.setId(1);
        ola.setName("Ola");
        ola.setLastName("Nordmann");
        ola.setPassword("passord123");
        ola.setMail("ola_nordmann@gmail.com");

        userDB.registerUser(ola.getId(), ola.getName(), ola.getLastName(), ola.getPassword(), ola.getMail());

        //Brukere sammenlignes med ID (Dette er en attributt som er unik for alle brukere)
        Assert.assertEquals(ola.getId(), userDB.getUserById(1).getId());

        //For alle brukere som legges/registreres inn i databasen blir også slettet slik at neste test ikke møter på samme bruker igjen
        userDB.deleteUser("1");
    }

    @Test
    public void delete_correct_user() {
        User ola = new User(1, "Ola", "Nordmann", "passord123", "ola_nordmann@gmail.com");
        User kari = new User(2, "Kari", "Nordmann", "drossap", "kari@gmail.com");
        userDB.registerUser(ola.getId(), ola.getName(), ola.getLastName(), ola.getPassword(), ola.getMail());
        userDB.registerUser(kari.getId(), kari.getName(), kari.getLastName(), kari.getPassword(), kari.getMail());

        //Ola og kari er nå registrert i databasen. Se på testen "user_is_registered_and_added_to_db()"
        //som bevis på at "registerUser" funksjonen fungerer

        //Forventer at det er 2 brukere i databasen før slettingen
        Assert.assertEquals(2, userDB.getAllUsers().size());

        //Sletter Kari fra systemet
        userDB.deleteUser("2");

        //Forventer nå at det bare er 1 bruker siden vi fjernet en av to brukere
        Assert.assertEquals(1, userDB.getAllUsers().size());


        ArrayList<User> leftUsers = new ArrayList<>(userDB.getAllUsers());
        Assert.assertFalse(leftUsers.contains(kari));

        userDB.deleteUser("1");
    }

    @Test
    public void user_is_updated() {
        User ola = new User(1, "Ola", "Nordmann", "passord123", "ola_nordmann@gmail.com");
        userDB.registerUser(ola.getId(), ola.getName(), ola.getLastName(), ola.getPassword(), ola.getMail());

        userDB.updateUser("1", "Ola", "Nordmann", "drossap", "ola_nordmann@gmail.com");
        Assert.assertEquals("drossap", userDB.getUserById(1).getPassword());

        userDB.deleteUser("1");
    }

}