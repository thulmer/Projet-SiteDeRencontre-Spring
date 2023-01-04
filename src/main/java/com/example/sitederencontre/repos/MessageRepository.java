package com.example.sitederencontre.repos;

import com.example.sitederencontre.entities.Message;
import com.example.sitederencontre.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("SELECT m FROM Message m WHERE ((m.expediteur= :iduser1 AND m.destinataire= :iduser2) OR (m.expediteur= :iduser2 AND m.destinataire= :iduser1)) ORDER BY id")
    public List<Message> findMessages(@Param("iduser1") Utilisateur iduser1, @Param("iduser2") Utilisateur iduser2);

    @Modifying
    @Query("update Message m set m.vu = :vu where m.expediteur = :idExp AND m.destinataire = :idDest")
    void updateMessagesVus(@Param(value = "idExp") Utilisateur idExp, @Param(value = "idDest") Utilisateur idDest , @Param(value = "vu") boolean vu);

    public int countByExpediteurAndDestinataireAndVuFalse(Utilisateur iduser1, Utilisateur iduser2);

    public List<Message> findByExpediteurAndDestinataireAndVuFalse(Utilisateur iduser1, Utilisateur iduser2);




}