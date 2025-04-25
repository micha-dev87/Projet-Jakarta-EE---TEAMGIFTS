package com.projet.miniprojet2.servlets;

import com.projet.miniprojet2.dao.CadeauDAO;
import com.projet.miniprojet2.models.Cadeau;
import com.projet.miniprojet2.models.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "CadeauServlet", urlPatterns = { "/cadeaux", "/cadeau/creer", "/cadeau/details" })
public class CadeauServlet extends HttpServlet {

    // DAO pour interagir avec les cadeaux dans la base de données
    private final CadeauDAO cadeauDAO = new CadeauDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Vérification de la session utilisateur
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Récupération du chemin de la requête
        String path = request.getServletPath();

        // Gestion des différentes actions en fonction du chemin
        switch (path) {
            case "/cadeaux":
                listCadeaux(request, response);
                break;
            case "/cadeau/creer":
                showCreateCadeauForm(request, response);
                break;
            case "/cadeau/details":
                showCadeauDetails(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/cadeaux");
                break;
        }
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

        // Récupération du chemin de la requête
        String path = request.getServletPath();

        // Gestion de la création de cadeau
        if ("/cadeau/creer".equals(path)) {
            createCadeau(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/cadeaux");
        }
    }

    private void listCadeaux(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération de la liste des cadeaux
        List<Cadeau> cadeaux = cadeauDAO.findAll();
        request.setAttribute("cadeaux", cadeaux);
        request.setAttribute("titre", "Catalogue de Cadeaux");
        request.setAttribute("contenu", "/WEB-INF/includes/content/cadeaux.jsp");
        request.getRequestDispatcher("/WEB-INF/templates/template.jsp").forward(request, response);
    }

    private void showCreateCadeauForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Préparation du formulaire de création de cadeau
        request.setAttribute("titre", "Ajouter un Cadeau");
        request.setAttribute("contenu", "/WEB-INF/includes/content/creerCadeau.jsp");
        request.getRequestDispatcher("/WEB-INF/templates/template.jsp").forward(request, response);
    }

    private void showCadeauDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération de l'identifiant du cadeau
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cadeaux");
            return;
        }

        try {
            Long id = Long.parseLong(idStr);
            Cadeau cadeau = cadeauDAO.findById(id);

            if (cadeau == null) {
                response.sendRedirect(request.getContextPath() + "/cadeaux");
                return;
            }

            // Affichage des détails du cadeau
            request.setAttribute("cadeau", cadeau);
            request.setAttribute("titre", "Détails du Cadeau");
            request.setAttribute("contenu", "/WEB-INF/includes/content/detailsCadeau.jsp");
            request.getRequestDispatcher("/WEB-INF/templates/template.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/cadeaux");
        }
    }

    private void createCadeau(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération des paramètres du formulaire de création
        String titre = request.getParameter("titre");
        String prixStr = request.getParameter("prix");
        String urlPhoto = request.getParameter("urlPhoto");

        try {
            BigDecimal prix = new BigDecimal(prixStr);

            // Création et sauvegarde du nouveau cadeau
            Cadeau cadeau = new Cadeau(titre, prix, urlPhoto);
            cadeauDAO.save(cadeau);

            response.sendRedirect(request.getContextPath() + "/cadeaux");

        } catch (NumberFormatException e) {
            // Gestion de l'erreur de format de prix
            request.setAttribute("erreur", "Format de prix invalide");
            request.setAttribute("titre", "Ajouter un Cadeau");
            request.setAttribute("contenu", "/WEB-INF/includes/content/creerCadeau.jsp");
            request.getRequestDispatcher("/WEB-INF/templates/template.jsp").forward(request, response);
        }
    }
}