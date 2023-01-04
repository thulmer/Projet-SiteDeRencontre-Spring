package com.example.sitederencontre.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "interaction")
public class Interaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "swiper", nullable = false)
    private Utilisateur swiper;
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "swiped", nullable = false)
    private Utilisateur swiped;
    @Column(columnDefinition = "tinyint(1) default '0'")
    private boolean liked;

    public Interaction(int id, Utilisateur swiper, Utilisateur swiped, boolean liked) {
        this.id = id;
        this.swiper = swiper;
        this.swiped = swiped;
        this.liked = liked;
    }

    public Interaction(Utilisateur swiper, Utilisateur swiped, boolean liked) {
        this.swiper = swiper;
        this.swiped = swiped;
        this.liked = liked;
    }

    public Interaction() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getSwiper() {
        return swiper;
    }

    public void setSwiper(Utilisateur swiper) {
        this.swiper = swiper;
    }

    public Utilisateur getSwiped() {
        return swiped;
    }

    public void setSwiped(Utilisateur swiped) {
        this.swiped = swiped;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    @Override
    public String toString() {
        return "Interaction{" + "id=" + id + ", swiper=" + swiper + ", swiped=" + swiped + ", liked=" + liked + '}';
    }

}
