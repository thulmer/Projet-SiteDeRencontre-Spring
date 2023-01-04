package com.example.sitederencontre.service;

import com.example.sitederencontre.entities.Match;
import com.example.sitederencontre.entities.Utilisateur;
import com.example.sitederencontre.repos.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MatchService {
    @Autowired
    private MatchRepository repo;

    public Match save(Match match){
        return repo.save(match);
    }

    public void delete(Utilisateur iduser1, Utilisateur iduser2){
        repo.deleteByIduser1AndIduser2(iduser1, iduser2);
        repo.deleteByIduser1AndIduser2(iduser2, iduser1);

    }

    public boolean findMatch(Utilisateur iduser1, Utilisateur iduser2) {
        Match match = repo.findByIduser1AndIduser2(iduser1, iduser2);
        return  match != null;
    }

    public void updateMatchsVus(Utilisateur user1, boolean vu) {
        repo.updateMatchVus(user1, vu);
    }

    public int getNombreMatchsPasVus(Utilisateur user1) {
        int nombre = repo.countByIduser1AndVuFalse(user1);
        return nombre;
    }
}
