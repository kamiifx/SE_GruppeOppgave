package no.hiof.gruppeprosjekt.controllers;

import io.javalin.http.Context;
import no.hiof.gruppeprosjekt.repositories.IParkingSpaceRepository;

public class ParkingSpaceController {
    private IParkingSpaceRepository ParkingSpaceRepository;

    public ParkingSpaceController(IParkingSpaceRepository parkingSpaceRepository) {
        this.ParkingSpaceRepository = parkingSpaceRepository;
    }

    public void createParkingSpace(Context ctx) {
        String user = ctx.pathParam("userId");
        ParkingSpaceRepository.createParkingSpace(ctx.formParam("city"), ctx.formParam("address"), ctx.formParam("size_sqm"), ctx.formParam("price_ph"), user);
    }
}
