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

@WebServlet(name = "ProfileServlet", urlPatterns = { "/profile" })
public class ProfileServlet extends HttpServlet {

    // DAO pour interagir avec les utilisateurs dans la base de données
    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Vérification de la session utilisateur
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Configuration des attributs de la requête pour l'affichage du profil
        request.setAttribute("titre", "Mon Profil");
        request.setAttribute("contenu", "/WEB-INF/includes/content/profile.jsp");
        request.getRequestDispatcher("/WEB-INF/templates/template.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Vérification de la session utilisateur
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Récupération de l'utilisateur connecté
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        // Récupération des paramètres de la requête
        String nomComplet = request.getParameter("nomComplet");
        String courriel = request.getParameter("courriel");
        String motDePasse = request.getParameter("motDePasse");

        // Si le courriel a changé, vérifier qu'il est unique
        if (!utilisateur.getCourriel().equals(courriel)) {
            Utilisateur existingUser = utilisateurDAO.findByCourriel(courriel);
            if (existingUser != null && !existingUser.getId().equals(utilisateur.getId())) {
                // Si le courriel est déjà utilisé, afficher un message d'erreur
                request.setAttribute("erreur", "Ce courriel est déjà utilisé par un autre compte");
                request.setAttribute("titre", "Mon Profil");
                request.setAttribute("contenu", "/WEB-INF/includes/content/profile.jsp");
                request.getRequestDispatcher("/WEB-INF/templates/template.jsp").forward(request, response);
                return;
            }
        }

        // Mise à jour des informations de l'utilisateur
        utilisateur.setNomComplet(nomComplet);
        utilisateur.setCourriel(courriel);

        // Mise à jour du mot de passe uniquement s'il a été fourni
        if (motDePasse != null && !motDePasse.isEmpty()) {
            utilisateur.setMotDePasse(motDePasse);
        }

        // Sauvegarde des modifications de l'utilisateur dans la base de données
        utilisateurDAO.save(utilisateur);

        // Mettre à jour l'utilisateur en session
        session.setAttribute("utilisateur", utilisateur);

        // Afficher un message de succès après la mise à jour du profil
        request.setAttribute("message", "Profil mis à jour avec succès");
        request.setAttribute("titre", "Mon Profil");
        request.setAttribute("contenu", "/WEB-INF/includes/content/profile.jsp");
        request.getRequestDispatcher("/WEB-INF/templates/template.jsp").forward(request, response);
    }
}