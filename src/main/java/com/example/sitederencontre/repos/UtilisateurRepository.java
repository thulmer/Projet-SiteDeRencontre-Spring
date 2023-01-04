package com.example.sitederencontre.repos;

import com.example.sitederencontre.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    public List<Utilisateur> findByAdminFalse();

    public Utilisateur findByEmailAndPassword(String email, String password);

    @Query("SELECT u FROM Utilisateur u WHERE u.email= :email AND u.password= :password")
    public Utilisateur getByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    public Utilisateur findByEmail(String email);

    public Utilisateur findTopByOrderByIdDesc();


    @Query("SELECT u FROM Utilisateur u WHERE u.id not in (select swiped from Interaction i where i.swiper= :user) AND u.id!= :user AND u.admin = false"
            + " AND u.sexe = :prefUser"
            + " AND u.preference = :sexeUser"
            + " AND age < IFNULL(:age, 100)"
            + " AND localisation = IFNULL(:localisation, localisation)"    )
    public List<Utilisateur> getUtilisateursPasEncoreVus(@Param("user") Utilisateur user, @Param("prefUser") boolean prefUser, @Param("sexeUser") boolean sexeUser, @Param("age") Integer age, @Param("localisation") String localisation);

    @Query("SELECT DISTINCT localisation FROM Utilisateur")
    public List<String> getLocalisations();

    @Query("SELECT iduser2 FROM Match m WHERE m.iduser1 = :user")
    public List<Utilisateur> getUtilisateursParMatchs(@Param("user") Utilisateur user);

    @Modifying
    @Query("UPDATE Utilisateur u SET u.enLigne= :enLigne WHERE u.id= :user")
    void updateEnLigne(@Param(value = "user") Integer user, @Param(value = "enLigne") boolean enLigne);
}
