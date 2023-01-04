package com.example.sitederencontre.controller;

import com.example.sitederencontre.entities.Utilisateur;
import com.example.sitederencontre.service.MatchService;
import com.example.sitederencontre.service.MessageService;
import com.example.sitederencontre.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MatchController {
    @Autowired
    UtilisateurService serviceUtilisateur;
    @Autowired
    MatchService serviceMatch;
    @Autowired
    MessageService serviceMessage;

    @GetMapping("/matchs")
    public String afficherMatchs(Model model, HttpSession session) {
        Utilisateur uti = (Utilisateur) session.getAttribute("utilisateur");
        List<Utilisateur> listUtilisateurs = serviceUtilisateur.listMatchs(uti);
        serviceMatch.updateMatchsVus(uti, true);
        model.addAttribute("listUtilisateurs", listUtilisateurs);
        if (listUtilisateurs.isEmpty()) {
            model.addAttribute("message", "Pas encore de matchs :( !");
        }
        return "utilisateurs-matchs";
    }

    @GetMapping("/matchs/delete/{id}")
    public String surppimerMatch(Model model, @PathVariable(name = "id") Integer id, HttpSession session) {
        Utilisateur user1 = (Utilisateur) session.getAttribute("utilisateur");
        Utilisateur user2 = serviceUtilisateur.get(id);
        serviceMatch.delete(user1, user2);
        return "redirect:/matchs";
    }
}
