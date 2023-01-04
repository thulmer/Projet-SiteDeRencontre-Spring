package com.example.sitederencontre.rest;

import com.example.sitederencontre.entities.Utilisateur;
import com.example.sitederencontre.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UtilisateurRestController {
    @Autowired
    UtilisateurService service;
    //RESTFul webservice avec l'uri ci-dessous
    @PostMapping("/utilisateurs/check_identifiants")
    public String verifierIdentifiants(@Param("email") String email, @Param("password") String password) {
        return service.identifiantsValides(email, password) ? "OK" : "Invalides";
    }

    @PostMapping("/utilisateurs/check_email")
    public String verifierEmail(@Param("email") String email, @Param("id") Integer id) {
        return service.isEmailUnique(email, id) ? "OK" : "Doublon";
    }

    @PostMapping("/utilisateurs/check_online")
    public String verifierOnlineMatchs(@Param("idUser") Integer idUser) {
        return service.isOnline(idUser) ? "Online" : "Offline";
    }


}

