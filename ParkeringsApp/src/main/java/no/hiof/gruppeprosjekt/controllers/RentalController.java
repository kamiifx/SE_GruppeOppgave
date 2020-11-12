package no.hiof.gruppeprosjekt.controllers;

import io.javalin.http.Context;
import no.hiof.gruppeprosjekt.repositories.IRentalRepository;


public class RentalController {
    private IRentalRepository rentalRepository;

    public RentalController(IRentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void createRentalAgreement(Context ctx) {
        String userId = ctx.pathParam("userId");
        String spaceId = ctx.pathParam("spaceId");
        rentalRepository.createRentalAgreement(Integer.parseInt(userId), Integer.parseInt(spaceId), Integer.parseInt(ctx.formParam("duration")));
        ctx.redirect("/app/" + userId);
    }

}
