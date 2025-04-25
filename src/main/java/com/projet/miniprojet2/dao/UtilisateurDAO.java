package com.projet.miniprojet2.dao;

import com.projet.miniprojet2.models.Utilisateur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class UtilisateurDAO {

    /**
     * Sauvegarde un objet Utilisateur dans la base de données.
     * Si l'objet n'a pas d'identifiant, il est persisté.
     * Sinon, il est fusionné.
     * 
     * @param utilisateur L'objet Utilisateur à sauvegarder
     */
    public void save(Utilisateur utilisateur) {
        EntityManager em = EntityManagerSingleton.getEntityManager();
        try {
            em.getTransaction().begin();
            if (utilisateur.getId() == null) {
                em.persist(utilisateur); // Persiste un nouvel objet Utilisateur
            } else {
                em.merge(utilisateur); // Met à jour un objet Utilisateur existant
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
     * Trouve un Utilisateur par son identifiant.
     * 
     * @param id L'identifiant de l'Utilisateur
     * @return L'objet Utilisateur correspondant à l'identifiant
     */
    public Utilisateur findById(Long id) {
        EntityManager em = EntityManagerSingleton.getEntityManager();
        return em.find(Utilisateur.class, id); // Recherche un Utilisateur par son ID
    }

    /**
     * Récupère tous les objets Utilisateur de la base de données.
     * 
     * @return Une liste de tous les objets Utilisateur
     */
    public List<Utilisateur> findAll() {
        EntityManager em = EntityManagerSingleton.getEntityManager();
        return em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList(); // Exécute une requête pour obtenir tous les Utilisateurs
    }

    /**
     * Supprime un objet Utilisateur de la base de données.
     * 
     * @param utilisateur L'objet Utilisateur à supprimer
     */
    public void delete(Utilisateur utilisateur) {
        EntityManager em = EntityManagerSingleton.getEntityManager();
        try {
            em.getTransaction().begin();
            if (em.contains(utilisateur)) {
                em.remove(utilisateur); // Supprime l'objet Utilisateur s'il est présent dans le contexte de persistance
            } else {
                em.remove(em.merge(utilisateur)); // Fusionne et supprime l'objet Utilisateur s'il n'est pas présent
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
     * Trouve un Utilisateur par son courriel.
     * 
     * @param courriel Le courriel de l'Utilisateur
     * @return L'objet Utilisateur correspondant au courriel
     */
    public Utilisateur findByCourriel(String courriel) {
        EntityManager em = EntityManagerSingleton.getEntityManager();
        TypedQuery<Utilisateur> query = em.createQuery(
                "SELECT u FROM Utilisateur u WHERE u.courriel = :courriel", Utilisateur.class);
        query.setParameter("courriel", courriel);
        try {
            return query.getSingleResult(); // Exécute une requête pour obtenir un Utilisateur par courriel
        } catch (NoResultException e) {
            return null; // Retourne null si aucun résultat n'est trouvé
        }
    }

    /**
     * Trouve un Utilisateur par son courriel et son mot de passe.
     * 
     * @param courriel Le courriel de l'Utilisateur
     * @param motDePasse Le mot de passe de l'Utilisateur
     * @return L'objet Utilisateur correspondant au courriel et au mot de passe
     */
    public Utilisateur findByCourrielAndMotDePasse(String courriel, String motDePasse) {
        EntityManager em = EntityManagerSingleton.getEntityManager();
        TypedQuery<Utilisateur> query = em.createQuery(
                "SELECT u FROM Utilisateur u WHERE u.courriel = :courriel AND u.motDePasse = :motDePasse",
                Utilisateur.class);
        query.setParameter("courriel", courriel);
        query.setParameter("motDePasse", motDePasse);
        try {
            return query.getSingleResult(); // Exécute une requête pour obtenir un Utilisateur par courriel et mot de passe
        } catch (NoResultException e) {
            return null; // Retourne null si aucun résultat n'est trouvé
        }
    }
}