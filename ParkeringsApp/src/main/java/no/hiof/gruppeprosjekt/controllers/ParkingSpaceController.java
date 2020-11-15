package no.hiof.gruppeprosjekt.controllers;

import io.javalin.http.Context;
import no.hiof.gruppeprosjekt.model.ParkingSpace;
import no.hiof.gruppeprosjekt.repositories.IParkingSpaceRepository;

import java.util.List;
import java.util.Random;

public class ParkingSpaceController {
    private IParkingSpaceRepository ParkingSpaceRepository;

    public ParkingSpaceController(IParkingSpaceRepository parkingSpaceRepository) {
        this.ParkingSpaceRepository = parkingSpaceRepository;
    }

    public void getAllSpaces(Context ctx) {
        List<ParkingSpace> getAllSpace = ParkingSpaceRepository.getAllSpaces();
        ctx.json(getAllSpace);
    }

    public void getSingleSpace(Context ctx) {
        String spaceId = ctx.pathParam("spaceId");
        ParkingSpace getSpace = ParkingSpaceRepository.getSpaceById(Integer.parseInt(spaceId));
        ctx.json(getSpace);
    }

    public void createParkingSpace(Context ctx) {
        String user = ctx.pathParam("userId");
        Random rand = new Random();
        ParkingSpaceRepository.createParkingSpace(rand.nextInt(1000),ctx.formParam("city"), ctx.formParam("address"), ctx.formParam("size_sqm"), ctx.formParam("price_ph"), user);
        ctx.redirect("/app/" + user);
    }

}
