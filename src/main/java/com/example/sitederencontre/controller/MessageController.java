package com.example.sitederencontre.controller;

import com.example.sitederencontre.entities.Message;
import com.example.sitederencontre.entities.Utilisateur;
import com.example.sitederencontre.service.MatchService;
import com.example.sitederencontre.service.MessageService;
import com.example.sitederencontre.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MessageController {
    @Autowired
    UtilisateurService serviceUtilisateur;
    @Autowired
    MatchService serviceMatch;
    @Autowired
    MessageService serviceMessage;


    @GetMapping("/matchs/chat/{id}")
    public String afficherChat(Model model, @PathVariable(name = "id") Integer id, HttpSession session) {
        Utilisateur user1 = (Utilisateur) session.getAttribute("utilisateur");
        Utilisateur user2 = (Utilisateur) serviceUtilisateur.get(id);
        if(serviceMatch.findMatch(user1, user2)) {
            List<Message> listMessages = serviceMessage.findMessages(user1, user2);
            if (listMessages.isEmpty()) {
                model.addAttribute("messageInfo", "Vous n'avez aucun message avec " + user2.getPrenom() + ", entamez la conversation.");
            } else {
                serviceMessage.updateMessagesVus(user2, user1);
                model.addAttribute("listMessages", listMessages);
            }
            model.addAttribute("message", new Message());
            model.addAttribute("destinataire", user2);
            return "chat";
        }
        return "redirect:/matchs";
    }

    @PostMapping("/matchs/chat/envoyer")
    public String envoyerMessage(Model model, Message message, @RequestParam("destId") Integer destId, HttpSession session) {
        Utilisateur expediteur = (Utilisateur) session.getAttribute("utilisateur");
        Utilisateur destinataire = serviceUtilisateur.get(destId);
        message.setExpediteur(expediteur);
        message.setDestinataire(destinataire);
        serviceMessage.save(message);
        model.addAttribute("id", destId);
        return "redirect:/matchs/chat/" + destId;
    }
}
