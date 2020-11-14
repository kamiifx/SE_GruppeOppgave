package no.hiof.gruppeprosjekt.repositories;

import no.hiof.gruppeprosjekt.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class AppUserDatabaseTest {

    AppUserDatabase userDB = new AppUserDatabase("jdbc:sqlite:testappdb.sqlite");
    User ola;
    User kari;

    @BeforeEach
    public void setup() {
        //Databasen skal alltid inneholde brukerne Ola og Kari

        ola = new User(1, "Ola", "Nordmann", "passord123", "ola_nordmann@gmail.com");
        kari = new User(2, "Kari", "Nordmann", "drossap", "kari@gmail.com");

        //Registreringsfunksjonen testes lenger nede
        userDB.registerUser(ola.getId(), ola.getName(), ola.getLastName(), ola.getPassword(), ola.getMail());
        userDB.registerUser(kari.getId(), kari.getName(), kari.getLastName(), kari.getPassword(), kari.getMail());
    }
    @Test
    public void get_all_correct_users() {
        //Alle brukere som jeg forventer er Ola og Kari (siden dem alltid er i DB)
        ArrayList<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(ola);
        expectedUsers.add(kari);

        //Henter faktisk alle brukere i databasen
        ArrayList<User> actualUsers = new ArrayList<>(userDB.getAllUsers());

        //Gjør om arrayane til strenger og sammenligner dem
        Assert.assertEquals(expectedUsers.toString(), actualUsers.toString());
    }

    @Test
    public void get_correct_user_by_mail() {
        //Sammenligner ID for å sjekke om begge brukere er det samme. Siden ID er noe som er unikt for alle brukere
        //godkjenner vi at denne sammenligningen er autentisk
        Assert.assertEquals(ola.getId(), userDB.getUserByMail("ola_nordmann@gmail.com").getId());
    }

    @Test
    public void get_nothing_by_getting_user_with_invalid_mail() {
        //Ingen i databasen har denne eposten
        Assert.assertNull(userDB.getUserByMail("ola_nordmann@hotmail.com"));
    }

    @Test
    public void get_correct_user_by_id() {
        //Kari sin ID = 2
        //Sammenligningen foregår vet at ID'en til Kari som opprettes sammenlignes med ID'en som vi får fra databasen
        Assert.assertEquals(kari.getId(), userDB.getUserById(2).getId());
    }

    @Test
    public void get_nothing_by_getting_user_with_invalid_id() {
        //Ingen i databasen har ID = 10
        Assert.assertNull(userDB.getUserById(10));

    }

    @Test
    public void user_logged_in_with_correct_credentials() {
        //Innlogging til Kari

        String emailInput = "kari@gmail.com";
        String passordInput = "drossap";

        Assert.assertTrue(userDB.loginUser(emailInput, passordInput));
    }

    @Test
    public void user_logged_in_with_invalid_password() {
        String emailInput = "kari@gmail.com";
        String passordInput = "FeilPassord"; //Passordet til Kari er "drossap"

        Assert.assertFalse(userDB.loginUser(emailInput, passordInput));

        //Funksjonen for å logge inn en bruker vil alltid hente en bruker ved å kalle funksjonen "getUserByMail()".
        //I testen "get_nothing_by_getting_user_with_invalid_mail()" vises det at ingen brukere hentes dersom
        //eposten ikke samsvarer med noen sine i systemet, dermed vil ingen logges inn ved å skrive inn feil epost
    }

    @Test
    public void user_is_registered_and_added_to_db() {
        User ahreketil = new User();
        ahreketil.setId(3);
        ahreketil.setName("Ahre-Ketil");
        ahreketil.setLastName("Lillehagen");
        ahreketil.setPassword("ffk123");
        ahreketil.setMail("ahre_ketil_lillehagen@hotmail.com");

        userDB.registerUser(ahreketil.getId(), ahreketil.getName(), ahreketil.getLastName(), ahreketil.getPassword(), ahreketil.getMail());

        //ID er unike attributter i databasen, dermed brukes dette for sammenligningen av brukere
        Assert.assertEquals(ahreketil.getId(), userDB.getUserById(3).getId());

        //Ahre Ketil slettes fra systemet og vil ikke være med videre på testene, slettingen gjøres etter sammenligningen
        //Testing for sletting gjøres nedenfor
        userDB.deleteUser("3");
    }

    @Test
    public void delete_correct_user() {
        //Det er 2 brukere i databasen nå "Ola" og "Kari"

        //Forventer at det er 2 brukere i databasen før slettingen
        Assert.assertEquals(2, userDB.getAllUsers().size());

        //Sletter Kari fra systemet
        userDB.deleteUser("2");

        //Forventer nå at det bare er 1 bruker siden vi fjernet en av to brukere
        Assert.assertEquals(1, userDB.getAllUsers().size());

        //Gjenstående brukere hentes
        ArrayList<User> leftUsers = new ArrayList<>(userDB.getAllUsers());
        //Kari er ikke en av de som gjenstår
        Assert.assertFalse(leftUsers.contains(kari));
    }

    @Test
    public void user_is_updated() {
        User halvorsen = new User(22, "Ola", "Halvorsen", "halvorsen..", "halvorsen@live.no");
        userDB.registerUser(halvorsen.getId(), halvorsen.getName(), halvorsen.getLastName(), halvorsen.getPassword(), halvorsen.getMail());

        //Vi oppdaterer Halvorsen til Preben Lohrengren
        userDB.updateUser("22", "Preben", "Lohrengren", "drossap", "preben@gmail.com");
        Assert.assertEquals("Preben", userDB.getUserById(22).getName());
        Assert.assertEquals("Lohrengren", userDB.getUserById(22).getLastName());
        Assert.assertEquals("drossap", userDB.getUserById(22).getPassword());
        Assert.assertEquals("preben@gmail.com", userDB.getUserById(22).getMail());

        userDB.deleteUser("22");
    }

}