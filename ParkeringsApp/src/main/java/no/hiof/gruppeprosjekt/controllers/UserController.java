package no.hiof.gruppeprosjekt.controllers;

import no.hiof.gruppeprosjekt.repositories.IUserRepository;

public class UserController {
    private IUserRepository userJsonRepo;

    public UserController(IUserRepository userJsonRepo){this.userJsonRepo = userJsonRepo;}


}
