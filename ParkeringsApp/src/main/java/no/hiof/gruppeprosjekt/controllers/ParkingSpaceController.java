package no.hiof.gruppeprosjekt.controllers;

import io.javalin.http.Context;
import no.hiof.gruppeprosjekt.model.ParkingSpace;
import no.hiof.gruppeprosjekt.repositories.IParkingSpaceRepository;

import java.util.List;

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
        ParkingSpaceRepository.createParkingSpace(ctx.formParam("city"), ctx.formParam("address"), ctx.formParam("size_sqm"), ctx.formParam("price_ph"), user);
        ctx.redirect("/app/" + user + "/parkingspaces");
    }

    public void deleteUser(Context ctx) {
        String user = ctx.pathParam("userId");
        ParkingSpaceRepository.deleteUser(user);
        ctx.redirect("/");
    }
}
