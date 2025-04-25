package com.projet.miniprojet2.dao;

import com.projet.miniprojet2.models.Cadeau;
import jakarta.persistence.EntityManager;
import java.util.List;

public class CadeauDAO {

    /**
     * Sauvegarde un objet Cadeau dans la base de données.
     * Si l'objet n'a pas d'identifiant, il est persisté.
     * Sinon, il est fusionné.
     * 
     * @param cadeau L'objet Cadeau à sauvegarder
     */
    public void save(Cadeau cadeau) {
        EntityManager em = EntityManagerSingleton.getEntityManager();
        try {
            em.getTransaction().begin();
            if (cadeau.getId() == null) {
                em.persist(cadeau); // Persiste un nouvel objet Cadeau
            } else {
                em.merge(cadeau); // Met à jour un objet Cadeau existant
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
     * Trouve un Cadeau par son identifiant.
     * 
     * @param id L'identifiant du Cadeau
     * @return L'objet Cadeau correspondant à l'identifiant
     */
    public Cadeau findById(Long id) {
        EntityManager em = EntityManagerSingleton.getEntityManager();
        return em.find(Cadeau.class, id); // Recherche un Cadeau par son ID
    }

    /**
     * Récupère tous les objets Cadeau de la base de données.
     * 
     * @return Une liste de tous les objets Cadeau
     */
    public List<Cadeau> findAll() {
        EntityManager em = EntityManagerSingleton.getEntityManager();
        return em.createQuery("SELECT c FROM Cadeau c", Cadeau.class).getResultList(); // Exécute une requête pour obtenir tous les Cadeaux
    }

    /**
     * Supprime un objet Cadeau de la base de données.
     * 
     * @param cadeau L'objet Cadeau à supprimer
     */
    public void delete(Cadeau cadeau) {
        EntityManager em = EntityManagerSingleton.getEntityManager();
        try {
            em.getTransaction().begin();
            if (em.contains(cadeau)) {
                em.remove(cadeau); // Supprime l'objet Cadeau s'il est présent dans le contexte de persistance
            } else {
                em.remove(em.merge(cadeau)); // Fusionne et supprime l'objet Cadeau s'il n'est pas présent
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