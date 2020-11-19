package no.hiof.gruppeprosjekt.controllers;

import io.javalin.http.Context;
import no.hiof.gruppeprosjekt.repositories.IRentalRepository;

import java.util.Random;


public class RentalController {
    private IRentalRepository rentalRepository;

    public RentalController(IRentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    //KRAV: User.RentParking
    public void createRentalAgreement(Context ctx) {
        Random rand = new Random();
        int rentalId = rand.nextInt(1000);

        String userId = ctx.pathParam("userId");
        if(userId == null) {
            userId = ctx.formParam("userId");
        }

        String spaceId = ctx.pathParam("spaceId");
        if(spaceId == null) {
            spaceId = ctx.formParam("spaceId");
        }

        String duration = ctx.formParam("duration");

        if(duration.equals("")) {
            ctx.status(417);
        }
        else {
            rentalRepository.createRentalAgreement(rentalId, Integer.parseInt(userId), Integer.parseInt(spaceId), Integer.parseInt(duration));
            ctx.status(201);
            ctx.redirect("/app/" + userId);
        }
    }
}
