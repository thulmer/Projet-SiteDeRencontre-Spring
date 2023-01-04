package com.example.sitederencontre.service;

import com.example.sitederencontre.entities.Utilisateur;
import com.example.sitederencontre.repos.InteractionRepository;
import com.example.sitederencontre.repos.MatchRepository;
import com.example.sitederencontre.repos.UtilisateurRepository;
import org.slf4j.helpers.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository repo;
    @Autowired
    private InteractionRepository repoInter;
    @Autowired
    private MatchRepository repoMatch;

    public List<Utilisateur> listAll(){
        return (List<Utilisateur> )  repo.findByAdminFalse();
    }

    public List<Utilisateur> listPasEncoreVus(Utilisateur user, Integer age, String localisation){
        return (List<Utilisateur> )  repo.getUtilisateursPasEncoreVus(user, user.isPreference(), user.isSexe(), age, localisation);
    }
    public List<Utilisateur> listMatchs(Utilisateur user){
        return (List<Utilisateur> )  repo.getUtilisateursParMatchs(user);
    }

    public Utilisateur save(Utilisateur utilisateur){
        boolean isUpdatingUser = false;
        if(utilisateur.getId() != null){
            isUpdatingUser =true;
        }
        if(isUpdatingUser){
            //On cherche l'utilisateur qu'on veut editer
            Utilisateur existingUser = repo.findById(utilisateur.getId()).get();
            //S'il le password est vide, cela suppose que l'administrateur souhaite changer le mot de passe
            if (utilisateur.getPassword().isEmpty()) {
                utilisateur.setPassword(existingUser.getPassword());
            }
        }
        return repo.save(utilisateur);
    }

    public boolean identifiantsValides(String email, String password){
        Utilisateur userByIdentifiants = repo.findByEmailAndPassword(email, password);
        if(userByIdentifiants == null) return false;
        return true;
    }

    public Utilisateur findParEmail(String email){return repo.findByEmail(email);}

    public Utilisateur get(Integer id)  {return  repo.findById(id).get();}

    public List<String> afficherLocalisations(){return repo.getLocalisations();}

    public void updateEnLigne(Integer user, boolean enLigne) {
        repo.updateEnLigne(user, enLigne);
    }

    public boolean isOnline(Integer user){
       Utilisateur uti = repo.getById(user);
       return uti.isEnLigne();
    }

    public boolean isEmailUnique(String email, Integer id) {
        Utilisateur userByEmail = repo.findByEmail(email);
        if (userByEmail == null) return true;
        boolean isCreatingNewUser = false;
        if (id == null){
            isCreatingNewUser=true;
        }
        //Si l' id utilisateur n'existe pas mais l' email existe,
        //on retourne false car pas email unique, on ne peut pas creer un nouveau utilisateur
        //dans le mode de création utilisateur
        if(isCreatingNewUser){
            //mais l'email existe, on retourne false car pas unique email
            if (userByEmail != null) return false;
        }else{
            //dans le mode d'edition utilisateur
            //Si l'id existe mais l'id qu'on edite  est différent de celui
            //de l'utilisateur possedant l'email,
            //on retourne false, car on ne peut pas creer un nouveau , puisqu email existe
            if (userByEmail.getId() != id) {
                return false;
            }
        }
        //dans tous les cas on peut creer, editer
        return true;
    }

    public Integer getLastId(){
        Utilisateur lastUtilisateur = repo.findTopByOrderByIdDesc();
        return lastUtilisateur.getId();
    }


    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
