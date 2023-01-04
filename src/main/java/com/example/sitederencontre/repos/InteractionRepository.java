package com.example.sitederencontre.repos;

import com.example.sitederencontre.entities.Interaction;
import com.example.sitederencontre.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Integer> {

    public Interaction findBySwiperAndSwiped(Utilisateur swiper, Utilisateur swiped);

}
