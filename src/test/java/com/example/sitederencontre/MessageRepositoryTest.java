package com.example.sitederencontre;

import com.example.sitederencontre.entities.Message;
import com.example.sitederencontre.entities.Utilisateur;
import com.example.sitederencontre.repos.MessageRepository;
import com.example.sitederencontre.repos.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class MessageRepositoryTest {
    @Autowired
    private MessageRepository repo;
    @Autowired
    private UtilisateurRepository repoUser;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateMessage() {
        Utilisateur user1 = repoUser.findById(1).get();
        Utilisateur user2 = repoUser.findById(17).get();
        System.out.print(new Date());
        System.out.print(LocalDateTime.now());

        Message message = new Message();
        message.setExpediteur(user2);
        message.setDestinataire(user1);
        message.setContenu("Test contenu");

        Message messageEntregistre = repo.save(message);
        assertThat(messageEntregistre).isNotNull();
        assertThat(messageEntregistre.getId()).isGreaterThan(0);



    }
    @Test
    public void testFindPasVu() {
        Utilisateur user1 = repoUser.findById(1).get();
        Utilisateur user2 = repoUser.findById(17).get();

        List<Message> listMesssagesPasVus = repo.findByExpediteurAndDestinataireAndVuFalse(user1, user2);
        System.out.println(listMesssagesPasVus);




    }

}