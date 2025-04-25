package com.projet.miniprojet2.models;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Représente un événement dans le système.
 * Chaque événement est associé à un utilisateur et peut avoir un cadeau et
 * plusieurs invitations.
 */
@Entity
@Table(name = "Evenement")
public class Evenement {

    /** Identifiant unique pour chaque événement */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Titre de l'événement */
    private String titre;

    /** Date de l'événement */
    @Temporal(TemporalType.DATE)
    private Date dateEvenement;

    /** Utilisateur associé à l'événement */
    @ManyToOne
    @JoinColumn(name = "idUtilisateur")
    private Utilisateur utilisateur;

    /** Cadeau associé à l'événement */
    @ManyToOne
    @JoinColumn(name = "idCadeau")
    private Cadeau cadeau;

    /** Liste des invitations associées à l'événement */
    @OneToMany(mappedBy = "evenement")
    private List<Invitation> invitations;

    // Constructeurs
    /**
     * Constructeur par défaut.
     */
    public Evenement() {
    }

    /**
     * Constructeur avec paramètres.
     * 
     * @param titre         Titre de l'événement
     * @param dateEvenement Date de l'événement
     * @param utilisateur   Utilisateur associé
     * @param cadeau        Cadeau associé
     */
    public Evenement(String titre, Date dateEvenement, Utilisateur utilisateur, Cadeau cadeau) {
        this.titre = titre;
        this.dateEvenement = dateEvenement;
        this.utilisateur = utilisateur;
        this.cadeau = cadeau;
    }

    // Getters et Setters
    /**
     * Retourne l'identifiant de l'événement.
     * 
     * @return l'identifiant
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant de l'événement.
     * 
     * @param id l'identifiant
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retourne le titre de l'événement.
     * 
     * @return le titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Définit le titre de l'événement.
     * 
     * @param titre le titre
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Retourne la date de l'événement.
     * 
     * @return la date
     */
    public Date getDateEvenement() {
        return dateEvenement;
    }

    /**
     * Définit la date de l'événement.
     * 
     * @param dateEvenement la date
     */
    public void setDateEvenement(Date dateEvenement) {
        this.dateEvenement = dateEvenement;
    }

    /**
     * Retourne l'utilisateur associé à l'événement.
     * 
     * @return l'utilisateur
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * Définit l'utilisateur associé à l'événement.
     * 
     * @param utilisateur l'utilisateur
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     * Retourne le cadeau associé à l'événement.
     * 
     * @return le cadeau
     */
    public Cadeau getCadeau() {
        return cadeau;
    }

    /**
     * Définit le cadeau associé à l'événement.
     * 
     * @param cadeau le cadeau
     */
    public void setCadeau(Cadeau cadeau) {
        this.cadeau = cadeau;
    }

    /**
     * Retourne la liste des invitations associées.
     * 
     * @return la liste des invitations
     */
    public List<Invitation> getInvitations() {
        return invitations;
    }

    /**
     * Définit la liste des invitations associées.
     * 
     * @param invitations la liste des invitations
     */
    public void setInvitations(List<Invitation> invitations) {
        this.invitations = invitations;
    }
}