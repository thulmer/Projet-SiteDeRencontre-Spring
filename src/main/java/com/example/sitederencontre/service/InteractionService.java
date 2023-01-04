package com.example.sitederencontre.service;

import com.example.sitederencontre.entities.Interaction;
import com.example.sitederencontre.entities.Utilisateur;
import com.example.sitederencontre.repos.InteractionRepository;
import com.example.sitederencontre.repos.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class InteractionService {
    @Autowired
    private InteractionRepository repo;

    public boolean rechercherInteractionParIds(Utilisateur swiper, Utilisateur swiped){
        Interaction interactionParIds = repo.findBySwiperAndSwiped(swiper, swiped);
        if(interactionParIds == null){
            return false;
        } else if (!interactionParIds.isLiked()) {
            return false;
        }
        return true;
    }

    public Interaction save(Interaction inter){
        return repo.save(inter);
    }

}
