package com.projet.miniprojet2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Singleton pour gérer les instances d'EntityManager.
 * Cette classe permet de centraliser la création et la gestion de
 * l'EntityManager
 * utilisé par toute l'application, assurant ainsi qu'une seule instance est
 * partagée entre tous les DAO.
 */
public class EntityManagerSingleton {
    /** Factory pour créer des instances d'EntityManager */
    private static EntityManagerFactory emf;

    /** Instance unique d'EntityManager partagée dans l'application */
    private static EntityManager em;

    /**
     * Constructeur privé pour empêcher l'instanciation externe.
     * Pattern Singleton.
     */
    private EntityManagerSingleton() {
    }

    /**
     * Récupère l'instance unique d'EntityManager.
     * Si l'EntityManager n'existe pas ou est fermé, en crée un nouveau.
     * Si l'EntityManagerFactory n'existe pas ou est fermée, en crée une nouvelle.
     * 
     * @return L'instance unique d'EntityManager
     */
    public static EntityManager getEntityManager() {
        if (em == null || !em.isOpen()) {
            if (emf == null || !emf.isOpen()) {
                // Crée une nouvelle factory en utilisant l'unité de persistance définie dans
                // persistence.xml
                emf = Persistence.createEntityManagerFactory("teamgiftsPU");
            }
            em = emf.createEntityManager();
        }
        return em;
    }

    /**
     * Ferme l'EntityManager s'il est ouvert.
     * Cette méthode devrait être appelée lors de la fin d'une opération
     * pour libérer les ressources.
     */
    public static void closeEntityManager() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    /**
     * Ferme l'EntityManager et l'EntityManagerFactory.
     * Cette méthode devrait être appelée lors de l'arrêt de l'application
     * pour libérer toutes les ressources JPA.
     */
    public static void closeEntityManagerFactory() {
        closeEntityManager();
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}