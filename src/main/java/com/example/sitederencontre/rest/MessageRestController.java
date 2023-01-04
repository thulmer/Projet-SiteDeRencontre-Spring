package com.example.sitederencontre.rest;

import com.example.sitederencontre.entities.Message;
import com.example.sitederencontre.entities.Utilisateur;
import com.example.sitederencontre.service.MatchService;
import com.example.sitederencontre.service.MessageService;
import com.example.sitederencontre.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class MessageRestController {
    @Autowired
    MessageService service;
    @Autowired
    UtilisateurService serviceUSer;
    //RESTFul webservice avec l'uri ci-dessous
    @PostMapping("/match/chat/check_messages_match")
    public ModelAndView verifierMessages(HttpSession session, @Param("idExp") Integer idExp) {
        ModelAndView modelAndView = new ModelAndView("fragments/messages :: nouveauxMessages");
        Utilisateur destinataire = (Utilisateur) session.getAttribute("utilisateur");
        Utilisateur expediteur = serviceUSer.get(idExp);
        List<Message> listMesssagesPasVus = service.findMessagesPasVus(expediteur, destinataire);
        service.updateMessagesVus(expediteur, destinataire);
        modelAndView.addObject("listNouveauxMessages", listMesssagesPasVus);

        return modelAndView;
    }
    @PostMapping("/match/check_notifications_messages")
    public int verifierNotificationsMessages(HttpSession session, @Param("idExp") Integer idExp) {
        Utilisateur destinataire = (Utilisateur) session.getAttribute("utilisateur");
        Utilisateur expediteur = serviceUSer.get(idExp);
        int nombre = 0;
        if(destinataire != null && expediteur !=null) nombre = service.getNombreMessagesPasVus(expediteur, destinataire);
        return nombre;
    }


}

