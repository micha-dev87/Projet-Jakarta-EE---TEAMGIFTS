package com.projet.miniprojet2.servlets;

import com.projet.miniprojet2.dao.UtilisateurDAO;
import com.projet.miniprojet2.models.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AuthServlet", urlPatterns = { "/login", "/register", "/logout" })
public class AuthServlet extends HttpServlet {

    // DAO pour interagir avec les utilisateurs dans la base de données
    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération du chemin de la requête
        String path = request.getServletPath();

        switch (path) {
            case "/login":
                // Préparation de la page de connexion
                request.setAttribute("titre", "Connexion");
                request.setAttribute("contenu", "/WEB-INF/includes/content/login.jsp");
                request.getRequestDispatcher("/WEB-INF/templates/template.jsp").forward(request, response);
                break;
            case "/register":
                // Préparation de la page d'inscription
                request.setAttribute("titre", "Inscription");
                request.setAttribute("contenu", "/WEB-INF/includes/content/register.jsp");
                request.getRequestDispatcher("/WEB-INF/templates/template.jsp").forward(request, response);
                break;
            case "/logout":
                // Gestion de la déconnexion
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
                response.sendRedirect(request.getContextPath() + "/login");
                break;
            default:
                // Redirection par défaut vers la page de connexion
                response.sendRedirect(request.getContextPath() + "/login");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération du chemin de la requête
        String path = request.getServletPath();

        switch (path) {
            case "/login":
                // Gestion de la connexion
                handleLogin(request, response);
                break;
            case "/register":
                // Gestion de l'inscription
                handleRegister(request, response);
                break;
            default:
                // Redirection par défaut vers la page de connexion
                response.sendRedirect(request.getContextPath() + "/login");
                break;
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // Récupération des paramètres de connexion
        String courriel = request.getParameter("courriel");
        String motDePasse = request.getParameter("motDePasse");

        // Recherche de l'utilisateur dans la base de données
        Utilisateur utilisateur = utilisateurDAO.findByCourrielAndMotDePasse(courriel, motDePasse);

        if (utilisateur != null) {
            // Connexion réussie, création de la session utilisateur
            HttpSession session = request.getSession();
            session.setAttribute("utilisateur", utilisateur);
            response.sendRedirect(request.getContextPath() + "/evenements");
        } else {
            // Connexion échouée, affichage d'un message d'erreur
            request.setAttribute("erreur", "Courriel ou mot de passe invalide");
            request.setAttribute("titre", "Connexion");
            request.setAttribute("contenu", "/WEB-INF/includes/content/login.jsp");
            request.getRequestDispatcher("/WEB-INF/templates/template.jsp").forward(request, response);
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // Récupération des paramètres d'inscription
        String nomComplet = request.getParameter("nomComplet");
        String courriel = request.getParameter("courriel");
        String motDePasse = request.getParameter("motDePasse");

        // Vérification de l'existence de l'utilisateur
        Utilisateur existingUser = utilisateurDAO.findByCourriel(courriel);

        if (existingUser != null) {
            // L'utilisateur existe déjà, affichage d'un message d'erreur
            request.setAttribute("erreur", "Ce courriel existe déjà");
            request.setAttribute("titre", "Inscription");
            request.setAttribute("contenu", "/WEB-INF/includes/content/register.jsp");
            request.getRequestDispatcher("/WEB-INF/templates/template.jsp").forward(request, response);
            return;
        }

        // Création d'un nouvel utilisateur
        Utilisateur newUser = new Utilisateur(nomComplet, courriel, motDePasse);
        utilisateurDAO.save(newUser);

        // Création de la session pour le nouvel utilisateur
        HttpSession session = request.getSession();
        session.setAttribute("utilisateur", newUser);

        // Redirection vers la page des événements
        response.sendRedirect(request.getContextPath() + "/evenements");
    }
}