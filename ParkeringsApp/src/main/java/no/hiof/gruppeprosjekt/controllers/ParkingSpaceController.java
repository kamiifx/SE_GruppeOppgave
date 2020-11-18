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
        if(getAllSpace.isEmpty()) {
            ctx.status(404);
        } else {
            ctx.status(200);
        }
    }

    public void getSingleSpace(Context ctx) {
        String spaceId = ctx.pathParam("spaceId");

        if(spaceId == null)
            spaceId = ctx.formParam("spaceId");

        ParkingSpace getSpace = ParkingSpaceRepository.getSpaceById(Integer.parseInt(spaceId));
        if(Integer.parseInt(spaceId) == getSpace.getSpaceId()) {
            ctx.status(200);
            ctx.json(getSpace);
        }

    }

    public void createParkingSpace(Context ctx) {
        String user = ctx.pathParam("userId");

        if (user == null)
            user = ctx.formParam("userId");

        Random rand = new Random();
        String city = ctx.formParam("city");
        String address = ctx.formParam("address");
        String size_sqm = ctx.formParam("size_sqm");
        String price_ph = ctx.formParam("price_ph");

        if(city.equals("") || address.equals("") || size_sqm.equals("") || price_ph.equals("")) {
            ctx.status(417);
        }
        else {
            ParkingSpaceRepository.createParkingSpace(rand.nextInt(1000),city, address, size_sqm, price_ph, user);
            ctx.status(201);
            ctx.redirect("/app/" + user);
        }
    }

}
