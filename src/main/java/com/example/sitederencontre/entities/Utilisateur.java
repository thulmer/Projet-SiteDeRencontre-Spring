package com.example.sitederencontre.entities;

import javax.persistence.*;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "prenom", length = 45, nullable = false)
    private String prenom;
    @Column(name = "age", nullable = false)
    private int age;
    @Column(name = "localisation", length = 60, nullable = false)
    private String localisation;
    @Column(name = "sexe", nullable = false, columnDefinition = "tinyint(1) unsigned default '0'")
    private boolean sexe;
    @Column(name = "preference", nullable = false, columnDefinition = "tinyint(1) unsigned default '0'")
    private boolean preference;
    @Column(name = "description", length = 500)
    private String description;
    private String imgLink;
    @Column(name = "email", length = 120, unique = true, nullable = false)
    private String email;
    @Column(name = "password", length = 120, nullable = false)
    private String password;
    @Column(nullable = false, columnDefinition = "tinyint(1) unsigned default '0'")
    private boolean privilege;
    @Column(nullable = false, columnDefinition = "tinyint(1) unsigned default '0'")
    private boolean admin;
    @Column(nullable = false, columnDefinition = "tinyint(1) unsigned default '0'")
    private boolean enLigne;
    @Column(nullable = false, columnDefinition = "tinyint(1) unsigned default '0'")
    private boolean recevoirEmails;

    public Utilisateur(int id, String prenom, int age, String localisation, boolean sexe, boolean preference, String imgLink, String email, String password, boolean privilege, boolean admin) {
        this.id = id;
        this.prenom = prenom;
        this.age = age;
        this.localisation = localisation;
        this.sexe = sexe;
        this.preference = preference;
        this.imgLink = imgLink;
        this.email = email;
        this.password = password;
        this.privilege = privilege;
        this.admin = admin;
    }

    public Utilisateur(int id, String prenom, int age, String localisation, boolean sexe, boolean preference, String description, String imgLink, String email, String password, boolean privilege, boolean admin, boolean enLigne) {
        this.id = id;
        this.prenom = prenom;
        this.age = age;
        this.localisation = localisation;
        this.sexe = sexe;
        this.preference = preference;
        this.description = description;
        this.imgLink = imgLink;
        this.email = email;
        this.password = password;
        this.privilege = privilege;
        this.admin = admin;
        this.enLigne = enLigne;

    }

    public Utilisateur(int id, String prenom, int age, String localisation, boolean sexe, boolean preference, String imgLink, String email, String password, boolean privilege) {
        this.id = id;
        this.prenom = prenom;
        this.age = age;
        this.localisation = localisation;
        this.sexe = sexe;
        this.preference = preference;
        this.imgLink = imgLink;
        this.email = email;
        this.password = password;
        this.privilege = privilege;
    }

    public Utilisateur(int id, String prenom, int age, String localisation, boolean sexe, boolean preference, String description, String imgLink, String email, String password, boolean privilege, boolean enLigne) {
        this.id = id;
        this.prenom = prenom;
        this.age = age;
        this.localisation = localisation;
        this.sexe = sexe;
        this.preference = preference;
        this.description = description;
        this.imgLink = imgLink;
        this.email = email;
        this.password = password;
        this.privilege = privilege;
        this.enLigne = enLigne;
    }

    public Utilisateur() {
    }

    public Utilisateur(int id, String prenom, int age, String localisation, boolean sexe, boolean preference, String email, String password, boolean privilege, boolean admin) {
        this.id = id;
        this.prenom = prenom;
        this.age = age;
        this.localisation = localisation;
        this.sexe = sexe;
        this.preference = preference;
        this.email = email;
        this.password = password;
        this.privilege = privilege;
        this.admin = admin;
    }

    public Utilisateur(int id, String prenom, int age, String localisation, boolean sexe, boolean preference, String email, String password, boolean privilege) {
        this.id = id;
        this.prenom = prenom;
        this.age = age;
        this.localisation = localisation;
        this.sexe = sexe;
        this.preference = preference;
        this.email = email;
        this.password = password;
        this.privilege = privilege;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public boolean isSexe() {
        return sexe;
    }

    public void setSexe(boolean sexe) {
        this.sexe = sexe;
    }

    public boolean isPreference() {
        return preference;
    }

    public void setPreference(boolean preference) {
        this.preference = preference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPrivilege() {
        return privilege;
    }

    public void setPrivilege(boolean privilege) {
        this.privilege = privilege;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isEnLigne() {
        return enLigne;
    }

    public void setEnLigne(boolean enLigne) {
        this.enLigne = enLigne;
    }

    public boolean isRecevoirEmails() {
        return recevoirEmails;
    }

    public void setRecevoirEmails(boolean recevoirEmails) {
        this.recevoirEmails = recevoirEmails;
    }

    @Override
    public String toString() {
        return "Utilisateur{" + "id=" + id + ", prenom=" + prenom + ", age=" + age + ", localisation=" + localisation + ", sexe=" + sexe + ", preference=" + preference + ", imgLink=" + imgLink + ", email=" + email + ", password=" + password + ", privilege=" + privilege + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Utilisateur other = (Utilisateur) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }


}
