package no.hiof.gruppeprosjekt.repositories;

import no.hiof.gruppeprosjekt.model.User;
import java.util.ArrayList;

public class AppTestUserRepo {
    private ArrayList<User> oUser = new ArrayList<>();

    public AppTestUserRepo(){
        oUser.add(arne);
        oUser.add(geir);
        oUser.add(kristin);
        AppUserJson userJson = new AppUserJson();
        userJson.writeArrayToJson(oUser);
        userJson.readJson();
    }

    User arne = new User("Arne","Nordmann","12345","arne@email.no");
    User geir = new User("Geir","Steinberg","halla123","geir@email.no");
    User kristin = new User("Kristin","Olavsen","hei123","kristin@email.no");
}
