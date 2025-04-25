package com.projet.miniprojet2.dao;

import com.projet.miniprojet2.models.Evenement;
import com.projet.miniprojet2.models.Utilisateur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class EvenementDAO {

    /**
     * Sauvegarde un objet Evenement dans la base de données.
     * Si l'objet n'a pas d'identifiant, il est persisté.
     * Sinon, il est fusionné.
     * 
     * @param evenement L'objet Evenement à sauvegarder
     */
    public void save(Evenement evenement) {
        EntityManager em = EntityManagerSingleton.getEntityManager();
        try {
            em.getTransaction().begin();
            if (evenement.getId() == null) {
                em.persist(evenement); // Persiste un nouvel objet Evenement
            } else {
                em.merge(evenement); // Met à jour un objet Evenement existant
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Annule la transaction en cas d'erreur
            }
            throw e;
        }
    }

    /**
     * Trouve un Evenement par son identifiant.
     * 
     * @param id L'identifiant de l'Evenement
     * @return L'objet Evenement correspondant à l'identifiant
     */
    public Evenement findById(Long id) {
        EntityManager em = EntityManagerSingleton.getEntityManager();
        return em.find(Evenement.class, id); // Recherche un Evenement par son ID
    }

    /**
     * Récupère tous les objets Evenement de la base de données.
     * 
     * @return Une liste de tous les objets Evenement
     */
    public List<Evenement> findAll() {
        EntityManager em = EntityManagerSingleton.getEntityManager();
        return em.createQuery("SELECT e FROM Evenement e", Evenement.class).getResultList(); // Exécute une requête pour obtenir tous les Evenements
    }

    /**
     * Trouve les Evenements associés à un Utilisateur donné.
     * 
     * @param utilisateur L'utilisateur dont on veut trouver les Evenements
     * @return Une liste des Evenements associés à l'utilisateur
     */
    public List<Evenement> findByUtilisateur(Utilisateur utilisateur) {
        EntityManager em = EntityManagerSingleton.getEntityManager();
        TypedQuery<Evenement> query = em.createQuery(
                "SELECT e FROM Evenement e WHERE e.utilisateur = :utilisateur", Evenement.class);
        query.setParameter("utilisateur", utilisateur);
        return query.getResultList(); // Exécute une requête pour obtenir les Evenements par utilisateur
    }

    /**
     * Supprime un objet Evenement de la base de données.
     * 
     * @param evenement L'objet Evenement à supprimer
     */
    public void delete(Evenement evenement) {
        EntityManager em = EntityManagerSingleton.getEntityManager();
        try {
            em.getTransaction().begin();
            if (em.contains(evenement)) {
                em.remove(evenement); // Supprime l'objet Evenement s'il est présent dans le contexte de persistance
            } else {
                em.remove(em.merge(evenement)); // Fusionne et supprime l'objet Evenement s'il n'est pas présent
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Annule la transaction en cas d'erreur
            }
            throw e;
        }
    }
}