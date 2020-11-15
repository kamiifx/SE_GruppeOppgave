package no.hiof.gruppeprosjekt.repositories;

import no.hiof.gruppeprosjekt.model.ParkingSpace;
import no.hiof.gruppeprosjekt.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class RentalDatabaseTest {
    //Alt kobles til test-databasen "testappdb.sqlite"
    AppUserDatabase userDB = new AppUserDatabase("jdbc:sqlite:testappdb.sqlite");
    ParkingSpaceDatabase parkingDB = new ParkingSpaceDatabase(userDB, "jdbc:sqlite:testappdb.sqlite");
    RentalDatabase rentalDB = new RentalDatabase(userDB, parkingDB, "jdbc:sqlite:testappdb.sqlite");

    User kari;
    User ola;
    ParkingSpace haldenIshall;

    @BeforeEach
    public void setup() {
        //Brukere Ola og Kari er i systemet
        ola = new User(1, "Ola", "Nordmann", "passord123", "ola_nordmann@gmail.com");
        kari = new User(2, "Kari", "Nordmann", "drossap", "kari@gmail.com");

        userDB.registerUser(ola.getId(), ola.getName(), ola.getLastName(), ola.getPassword(), ola.getMail());
        userDB.registerUser(kari.getId(), kari.getName(), kari.getLastName(), kari.getPassword(), kari.getMail());

        //Plassen Halden Ishall er tilgjengelig for leie. Denne plassen er lagt ut av Ola
        haldenIshall = new ParkingSpace(2,"Halden", "Blokkeveien 39", 4, 50, ola);
        parkingDB.createParkingSpace(haldenIshall.getSpaceId(),haldenIshall.getCity(), haldenIshall.getAddress(), Double.toString(haldenIshall.getSize_sqm()), Double.toString(haldenIshall.getPrice_ph()), Integer.toString(haldenIshall.getByUser().getId()));
    }

    @Test
    void createRentalAgreement_success() throws SQLException {
        //Situasjon-beskrivelse: Kari ønsker å leie en parkeringsplass ved ishallen for en hockey-kamp.
        //Dermed leier hun plassen "haldenIshall" for 3 timer via systemet.

        //Selve leieavtalen får en id for å hente den ut dersom det skulle være nødvendig. Denne id'en er unik og autentisk av den grunn.
        //I avtalen refereres det til brukerens ID og til plassens ID, og starttid er klokkeslettet på da bestillingen var satt inn (I prototypen regnes den som aktiv da)

        //Avtalen lages og legges inn i databasen (for testens skyld legger vi inn en id på 1.)
        rentalDB.createRentalAgreement(1,2, 2, 3);

        //Henter plassen fra databasen
        String sql = "SELECT * FROM rental WHERE rentalid = 1";

        //Tilordner informasjon til variabler for sammenligning senere
        ResultSet rs = null;
        int userID = 0;
        int spaceID = 0;
        int duration = 0;
        int rentalid = 0;

        try {
            Connection connect = DriverManager.getConnection("jdbc:sqlite:testappdb.sqlite");
            PreparedStatement preState = connect.prepareStatement(sql);
            rs = preState.executeQuery();
            while(rs.next()) {
                userID = rs.getInt("userid");
                spaceID = rs.getInt("spaceid");
                duration = rs.getInt("duration");
                rentalid = rs.getInt("rentalid");
            }
            connect.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        //For å bevise at riktig avtale er hentet fra databasen vil id, bruker-id, parkeringsplass-id og lengde hentes inn.
        //Siden vi ikke kan forutsi NÅR testen kjøres vil vi ikke kunne vise en sammenligning av starttimen, men denne kan sees manuelt i databasen
        //"testappdb.sqlite" dersom ønsket

        //Ved å sammenligne disse verdiene kan vi bevise at leie-avtalen har blitt opprettet og lagt inn i systemet

        //Sammenligner brukere
        //Kari sin ID = 2
        Assert.assertEquals(2, userID);

        //Sammenligner plass
        //haldenIshall sin ID = 2
        Assert.assertEquals(2, spaceID);

        //Sammenligner lengde på parkeringen (Ovenfor forklarer vi at den varer i 3 timer)
        Assert.assertEquals(3, duration);

        //Sammenligner id på leie-avtalen (For å holde det simpelt for testen så er id på avtalen 1)
        Assert.assertEquals(1, rentalid);

        //Viser at etter leie så blir plassen utilgjengelig
        Assert.assertEquals(0, parkingDB.getSpaceById(2).isAvailable());

        //Etter sammenligningen så settes parkeringsplassen "haldenIshall" til 1 (tilgjengelig) for at det ikke skal være noe
        //Kræsj med de andre testene
        parkingDB.updateAvailability(2, (byte) 1);
    }
}

