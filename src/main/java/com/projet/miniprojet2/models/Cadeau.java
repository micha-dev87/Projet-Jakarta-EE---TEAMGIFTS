package com.projet.miniprojet2.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Représente un cadeau dans le système.
 * Chaque cadeau peut être associé à plusieurs événements.
 */
@Entity
@Table(name = "Cadeau")
public class Cadeau {

    /** Identifiant unique pour chaque cadeau */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Titre du cadeau */
    private String titre;

    /** Prix du cadeau */
    private BigDecimal prix;

    /** URL de la photo du cadeau */
    private String urlPhoto;

    /** Liste des événements associés à ce cadeau */
    @OneToMany(mappedBy = "cadeau")
    private List<Evenement> evenements;

    // Constructeurs
    /**
     * Constructeur par défaut.
     */
    public Cadeau() {
    }

    /**
     * Constructeur avec paramètres.
     * 
     * @param titre    Titre du cadeau
     * @param prix     Prix du cadeau
     * @param urlPhoto URL de la photo du cadeau
     */
    public Cadeau(String titre, BigDecimal prix, String urlPhoto) {
        this.titre = titre;
        this.prix = prix;
        this.urlPhoto = urlPhoto;
    }

    // Getters et Setters
    /**
     * Retourne l'identifiant du cadeau.
     * 
     * @return l'identifiant
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant du cadeau.
     * 
     * @param id l'identifiant
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retourne le titre du cadeau.
     * 
     * @return le titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Définit le titre du cadeau.
     * 
     * @param titre le titre
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Retourne le prix du cadeau.
     * 
     * @return le prix
     */
    public BigDecimal getPrix() {
        return prix;
    }

    /**
     * Définit le prix du cadeau.
     * 
     * @param prix le prix
     */
    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    /**
     * Retourne l'URL de la photo du cadeau.
     * 
     * @return l'URL de la photo
     */
    public String getUrlPhoto() {
        return urlPhoto;
    }

    /**
     * Définit l'URL de la photo du cadeau.
     * 
     * @param urlPhoto l'URL de la photo
     */
    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    /**
     * Retourne la liste des événements associés.
     * 
     * @return la liste des événements
     */
    public List<Evenement> getEvenements() {
        return evenements;
    }

    /**
     * Définit la liste des événements associés.
     * 
     * @param evenements la liste des événements
     */
    public void setEvenements(List<Evenement> evenements) {
        this.evenements = evenements;
    }
}