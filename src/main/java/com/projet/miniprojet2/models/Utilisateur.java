package com.projet.miniprojet2.models;

import jakarta.persistence.*;
import java.util.List;

/**
 * Représente un utilisateur dans le système.
 * Chaque utilisateur peut organiser plusieurs événements et recevoir plusieurs
 * invitations.
 */
@Entity
@Table(name = "Utilisateur")
public class Utilisateur {

    /** Identifiant unique pour chaque utilisateur */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nom complet de l'utilisateur */
    private String nomComplet;

    /** Adresse courriel de l'utilisateur */
    private String courriel;

    /** Mot de passe de l'utilisateur */
    private String motDePasse;

    /** Liste des événements organisés par l'utilisateur */
    @OneToMany(mappedBy = "utilisateur")
    private List<Evenement> evenements;

    /** Liste des invitations reçues par l'utilisateur */
    @OneToMany(mappedBy = "participant")
    private List<Invitation> invitations;

    // Constructeurs
    /**
     * Constructeur par défaut.
     */
    public Utilisateur() {
    }

    /**
     * Constructeur avec paramètres.
     * 
     * @param nomComplet Nom complet de l'utilisateur
     * @param courriel   Adresse courriel de l'utilisateur
     * @param motDePasse Mot de passe de l'utilisateur
     */
    public Utilisateur(String nomComplet, String courriel, String motDePasse) {
        this.nomComplet = nomComplet;
        this.courriel = courriel;
        this.motDePasse = motDePasse;
    }

    // Getters et Setters
    /**
     * Retourne l'identifiant de l'utilisateur.
     * 
     * @return l'identifiant
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant de l'utilisateur.
     * 
     * @param id l'identifiant
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retourne le nom complet de l'utilisateur.
     * 
     * @return le nom complet
     */
    public String getNomComplet() {
        return nomComplet;
    }

    /**
     * Définit le nom complet de l'utilisateur.
     * 
     * @param nomComplet le nom complet
     */
    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    /**
     * Retourne l'adresse courriel de l'utilisateur.
     * 
     * @return l'adresse courriel
     */
    public String getCourriel() {
        return courriel;
    }

    /**
     * Définit l'adresse courriel de l'utilisateur.
     * 
     * @param courriel l'adresse courriel
     */
    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    /**
     * Retourne le mot de passe de l'utilisateur.
     * 
     * @return le mot de passe
     */
    public String getMotDePasse() {
        return motDePasse;
    }

    /**
     * Définit le mot de passe de l'utilisateur.
     * 
     * @param motDePasse le mot de passe
     */
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    /**
     * Retourne la liste des événements organisés par l'utilisateur.
     * 
     * @return la liste des événements
     */
    public List<Evenement> getEvenements() {
        return evenements;
    }

    /**
     * Définit la liste des événements organisés par l'utilisateur.
     * 
     * @param evenements la liste des événements
     */
    public void setEvenements(List<Evenement> evenements) {
        this.evenements = evenements;
    }

    /**
     * Retourne la liste des invitations reçues par l'utilisateur.
     * 
     * @return la liste des invitations
     */
    public List<Invitation> getInvitations() {
        return invitations;
    }

    /**
     * Définit la liste des invitations reçues par l'utilisateur.
     * 
     * @param invitations la liste des invitations
     */
    public void setInvitations(List<Invitation> invitations) {
        this.invitations = invitations;
    }
}