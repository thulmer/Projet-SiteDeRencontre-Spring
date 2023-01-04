package com.example.sitederencontre;

import com.example.sitederencontre.entities.Utilisateur;
import com.example.sitederencontre.repos.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UtilisateurRepositoryTest {
    @Autowired
    private UtilisateurRepository repo;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateFirstUtilisateur() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPrenom("Test");
        utilisateur.setAge(24);
        utilisateur.setLocalisation("MONTREAL");
        utilisateur.setSexe(false);
        utilisateur.setPreference(true);
        utilisateur.setEmail("test@test.com");
        utilisateur.setPassword("test123");
        Utilisateur utilisateurEntregistre = repo.save(utilisateur);
        assertThat(utilisateurEntregistre).isNotNull();
        assertThat(utilisateurEntregistre.getId()).isGreaterThan(0);

    }

    @Test
    public void testCreateResteUtilisateur(){
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPrenom("Martine");
        utilisateur.setAge(45);
        utilisateur.setLocalisation("QUEBEC");
        utilisateur.setSexe(true);
        utilisateur.setPreference(false);
        utilisateur.setEmail("martine@test.com");
        utilisateur.setPassword("martine123");
        utilisateur.setPrivilege(true);
        repo.save(utilisateur);

        Utilisateur utilisateurAdmin = new Utilisateur();
        utilisateurAdmin.setPrenom("Admin");
        utilisateurAdmin.setAge(80);
        utilisateurAdmin.setLocalisation("TERRE");
        utilisateurAdmin.setSexe(false);
        utilisateurAdmin.setPreference(false);
        utilisateurAdmin.setEmail("admin@test.com");
        utilisateurAdmin.setPassword("admin");
        utilisateurAdmin.setAdmin(true);
        repo.save(utilisateurAdmin);

    }

    @Test
    public void testUpdateEnligne() {
        Utilisateur utilisateurConnecte = repo.findByEmail("will@gmail.com");
        repo.updateEnLigne(utilisateurConnecte.getId(), true);
    }
}
