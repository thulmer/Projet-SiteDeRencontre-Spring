package com.example.sitederencontre.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "matchs")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "iduser1", nullable = false)
    private Utilisateur iduser1;
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "iduser2", nullable = false)
    private Utilisateur iduser2;
    @Column(nullable = false, columnDefinition = "tinyint(1) unsigned default '0'")
    private boolean vu;

    public Match(Utilisateur iduser1, Utilisateur iduser2) {
        this.iduser1 = iduser1;
        this.iduser2 = iduser2;
    }

    public Match() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getIduser1() {
        return iduser1;
    }

    public void setIduser1(Utilisateur iduser1) {
        this.iduser1 = iduser1;
    }

    public Utilisateur getIduser2() {
        return iduser2;
    }

    public void setIduser2(Utilisateur iduser2) {
        this.iduser2 = iduser2;
    }

    public boolean isVu() {
        return vu;
    }

    public void setVu(boolean vu) {
        this.vu = vu;
    }

    @Override
    public String toString() {
        return "Match{" + "iduser1=" + iduser1 + ", iduser2=" + iduser2 + '}';
    }



}
