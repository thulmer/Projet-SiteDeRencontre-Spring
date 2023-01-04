package com.example.sitederencontre.service;

import com.example.sitederencontre.entities.Match;
import com.example.sitederencontre.entities.Message;
import com.example.sitederencontre.entities.Utilisateur;
import com.example.sitederencontre.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MessageService {
    @Autowired
    private MessageRepository repo;

    public Message save(Message message){
        return repo.save(message);
    }

    public List<Message> findMessages(Utilisateur iduser1, Utilisateur iduser2) {
        return (List<Message> )  repo.findMessages(iduser1, iduser2);
    }

    public void updateMessagesVus(Utilisateur user1, Utilisateur user2) {
        repo.updateMessagesVus(user1, user2, true);
    }

    public int getNombreMessagesPasVus(Utilisateur user1, Utilisateur user2) {
        int nombre = repo.countByExpediteurAndDestinataireAndVuFalse(user1, user2);
        return nombre;
    }

    public List<Message> findMessagesPasVus(Utilisateur iduser1, Utilisateur iduser2) {
        return (List<Message> )  repo.findByExpediteurAndDestinataireAndVuFalse(iduser1, iduser2);
    }
}
