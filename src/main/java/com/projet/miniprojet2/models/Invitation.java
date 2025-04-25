package com.projet.miniprojet2.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Représente une invitation à un événement.
 * Chaque invitation est associée à un événement et à un utilisateur invité.
 */
@Entity
@Table(name = "Invitation")
public class Invitation {

    /** Identifiant unique pour chaque invitation */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Événement associé à l'invitation */
    @ManyToOne
    @JoinColumn(name = "idEvenement")
    private Evenement evenement;

    /** Utilisateur invité */
    @ManyToOne
    @JoinColumn(name = "idParticipant")
    private Utilisateur participant;

    /** Statut de l'invitation (acceptée, refusée, en attente) */
    private String etat;
    private BigDecimal contribution;

    // Constructeurs
    /**
     * Constructeur par défaut.
     */
    public Invitation() {
    }

    /**
     * Constructeur avec paramètres.
     * 
     * @param evenement    Événement associé
     * @param participant  Utilisateur invité
     * @param etat         Statut de l'invitation
     * @param contribution Contribution associée à l'invitation
     */
    public Invitation(Evenement evenement, Utilisateur participant, String etat, BigDecimal contribution) {
        this.evenement = evenement;
        this.participant = participant;
        this.etat = etat;
        this.contribution = contribution;
    }

    // Getters et Setters
    /**
     * Retourne l'identifiant de l'invitation.
     * 
     * @return l'identifiant
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant de l'invitation.
     * 
     * @param id l'identifiant
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retourne l'événement associé à l'invitation.
     * 
     * @return l'événement
     */
    public Evenement getEvenement() {
        return evenement;
    }

    /**
     * Définit l'événement associé à l'invitation.
     * 
     * @param evenement l'événement
     */
    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    /**
     * Retourne l'utilisateur invité.
     * 
     * @return l'utilisateur
     */
    public Utilisateur getParticipant() {
        return participant;
    }

    /**
     * Définit l'utilisateur invité.
     * 
     * @param participant l'utilisateur
     */
    public void setParticipant(Utilisateur participant) {
        this.participant = participant;
    }

    /**
     * Retourne le statut de l'invitation.
     * 
     * @return le statut
     */
    public String getEtat() {
        return etat;
    }

    /**
     * Définit le statut de l'invitation.
     * 
     * @param etat le statut
     */
    public void setEtat(String etat) {
        this.etat = etat;
    }

    public BigDecimal getContribution() {
        return contribution;
    }

    public void setContribution(BigDecimal contribution) {
        this.contribution = contribution;
    }
}