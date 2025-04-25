package com.projet.miniprojet2.dao;

import com.projet.miniprojet2.models.Evenement;
import com.projet.miniprojet2.models.Invitation;
import com.projet.miniprojet2.models.Utilisateur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class InvitationDAO {

    /**
     * Sauvegarde un objet Invitation dans la base de données.
     * Si l'objet n'a pas d'identifiant, il est persisté.
     * Sinon, il est fusionné.
     * 
     * @param invitation L'objet Invitation à sauvegarder
     */
    public void save(Invitation invitation) {
        EntityManager em = EntityManagerSingleton.getEntityManager();
        try {
            em.getTransaction().begin();
            if (invitation.getId() == null) {
                em.persist(invitation); // Persiste un nouvel objet Invitation
            } else {
                em.merge(invitation); // Met à jour un objet Invitation existant
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
     * Trouve une Invitation par son identifiant.
     * 
     * @param id L'identifiant de l'Invitation
     * @return L'objet Invitation correspondant à l'identifiant
     */
    public Invitation findById(Long id) {
        EntityManager em = EntityManagerSingleton.getEntityManager();
        return em.find(Invitation.class, id); // Recherche une Invitation par son ID
    }

    /**
     * Récupère toutes les objets Invitation de la base de données.
     * 
     * @return Une liste de tous les objets Invitation
     */
    public List<Invitation> findAll() {
        EntityManager em = EntityManagerSingleton.getEntityManager();
        return em.createQuery("SELECT i FROM Invitation i", Invitation.class).getResultList(); // Exécute une requête pour obtenir toutes les Invitations
    }

    /**
     * Trouve les Invitations associées à un Evenement donné.
     * 
     * @param evenement L'événement dont on veut trouver les Invitations
     * @return Une liste des Invitations associées à l'événement
     */
    public List<Invitation> findByEvenement(Evenement evenement) {
        EntityManager em = EntityManagerSingleton.getEntityManager();
        TypedQuery<Invitation> query = em.createQuery(
                "SELECT i FROM Invitation i WHERE i.evenement = :evenement", Invitation.class);
        query.setParameter("evenement", evenement);
        return query.getResultList(); // Exécute une requête pour obtenir les Invitations par événement
    }

    /**
     * Trouve les Invitations associées à un Participant donné.
     * 
     * @param participant Le participant dont on veut trouver les Invitations
     * @return Une liste des Invitations associées au participant
     */
    public List<Invitation> findByParticipant(Utilisateur participant) {
        EntityManager em = EntityManagerSingleton.getEntityManager();
        TypedQuery<Invitation> query = em.createQuery(
                "SELECT i FROM Invitation i WHERE i.participant = :participant", Invitation.class);
        query.setParameter("participant", participant);
        return query.getResultList(); // Exécute une requête pour obtenir les Invitations par participant
    }

    /**
     * Trouve une Invitation par un Evenement et un Participant donnés.
     * 
     * @param evenement L'événement de l'Invitation
     * @param participant Le participant de l'Invitation
     * @return L'objet Invitation correspondant à l'événement et au participant
     */
    public Invitation findByEvenementAndParticipant(Evenement evenement, Utilisateur participant) {
        EntityManager em = EntityManagerSingleton.getEntityManager();
        TypedQuery<Invitation> query = em.createQuery(
                "SELECT i FROM Invitation i WHERE i.evenement = :evenement AND i.participant = :participant",
                Invitation.class);
        query.setParameter("evenement", evenement);
        query.setParameter("participant", participant);
        List<Invitation> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0); // Retourne la première Invitation trouvée ou null si aucune n'est trouvée
    }

    /**
     * Supprime un objet Invitation de la base de données.
     * 
     * @param invitation L'objet Invitation à supprimer
     */
    public void delete(Invitation invitation) {
        EntityManager em = EntityManagerSingleton.getEntityManager();
        try {
            em.getTransaction().begin();
            if (em.contains(invitation)) {
                em.remove(invitation); // Supprime l'objet Invitation s'il est présent dans le contexte de persistance
            } else {
                em.remove(em.merge(invitation)); // Fusionne et supprime l'objet Invitation s'il n'est pas présent
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