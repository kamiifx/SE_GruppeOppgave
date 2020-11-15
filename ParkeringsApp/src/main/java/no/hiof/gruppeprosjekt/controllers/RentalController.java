package no.hiof.gruppeprosjekt.controllers;

import io.javalin.http.Context;
import no.hiof.gruppeprosjekt.repositories.IRentalRepository;

import java.util.Random;


public class RentalController {
    private IRentalRepository rentalRepository;

    public RentalController(IRentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void createRentalAgreement(Context ctx) {
        Random rand = new Random();

        String userId = ctx.pathParam("userId");
        String spaceId = ctx.pathParam("spaceId");
        int rentalId = rand.nextInt(1000);
        rentalRepository.createRentalAgreement(rentalId, Integer.parseInt(userId), Integer.parseInt(spaceId), Integer.parseInt(ctx.formParam("duration")));
        ctx.redirect("/app/" + userId);
    }

}
