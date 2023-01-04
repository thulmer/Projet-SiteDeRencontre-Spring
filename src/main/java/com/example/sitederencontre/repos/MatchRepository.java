package com.example.sitederencontre.repos;

import com.example.sitederencontre.entities.Match;
import com.example.sitederencontre.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {
    public void deleteByIduser1AndIduser2(Utilisateur iduser1, Utilisateur iduser2);


    public Match findByIduser1AndIduser2(Utilisateur iduser1, Utilisateur iduser2);

    @Modifying
    @Query("update Match m set m.vu = :vu where m.iduser1 = :id")
    void updateMatchVus(@Param(value = "id") Utilisateur id, @Param(value = "vu") boolean vu);

    public int countByIduser1AndVuFalse(Utilisateur iduser1);
}