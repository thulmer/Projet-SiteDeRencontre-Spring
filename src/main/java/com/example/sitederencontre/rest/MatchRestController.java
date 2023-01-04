package com.example.sitederencontre.rest;

import com.example.sitederencontre.entities.Utilisateur;
import com.example.sitederencontre.service.MatchService;
import com.example.sitederencontre.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class MatchRestController {
    @Autowired
    MatchService service;
    //RESTFul webservice avec l'uri ci-dessous
    @PostMapping("/utilisateurs/check_notifications_match")
    public int verifierNotificationsMatchs(HttpSession session) {
        Utilisateur uti = (Utilisateur) session.getAttribute("utilisateur");
        int nombre = 0;
        if(uti != null) nombre = service.getNombreMatchsPasVus(uti);
        return nombre;
    }
}

