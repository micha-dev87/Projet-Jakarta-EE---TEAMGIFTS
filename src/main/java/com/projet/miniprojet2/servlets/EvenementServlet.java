package com.projet.miniprojet2.servlets;

import com.projet.miniprojet2.dao.CadeauDAO;
import com.projet.miniprojet2.dao.EvenementDAO;
import com.projet.miniprojet2.models.Cadeau;
import com.projet.miniprojet2.models.Evenement;
import com.projet.miniprojet2.models.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "EvenementServlet", urlPatterns = { "/evenements", "/evenement/creer", "/evenement/details" })
public class EvenementServlet extends HttpServlet {

    // DAO pour interagir avec les événements dans la base de données
    private final EvenementDAO evenementDAO = new EvenementDAO();
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
            case "/evenements":
                listEvenements(request, response);
                break;
            case "/evenement/creer":
                showCreateEvenementForm(request, response);
                break;
            case "/evenement/details":
                showEvenementDetails(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/evenements");
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

        // Gestion de la création d'événement
        if ("/evenement/creer".equals(path)) {
            createEvenement(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/evenements");
        }
    }

    private void listEvenements(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération de l'utilisateur connecté
        Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
        // Récupération de la liste des événements de l'utilisateur
        List<Evenement> evenements = evenementDAO.findByUtilisateur(utilisateur);

        // Mise en place des attributs pour la vue
        request.setAttribute("evenements", evenements);
        request.setAttribute("titre", "Mes Événements");
        request.setAttribute("contenu", "/WEB-INF/includes/content/evenements.jsp");
        request.getRequestDispatcher("/WEB-INF/templates/template.jsp").forward(request, response);
    }

    private void showCreateEvenementForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération de la liste des cadeaux
        List<Cadeau> cadeaux = cadeauDAO.findAll();
        // Mise en place des attributs pour le formulaire de création d'événement
        request.setAttribute("cadeaux", cadeaux);
        request.setAttribute("titre", "Créer un Événement");
        request.setAttribute("contenu", "/WEB-INF/includes/content/creerEvenement.jsp");
        request.getRequestDispatcher("/WEB-INF/templates/template.jsp").forward(request, response);
    }

    private void showEvenementDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération de l'identifiant de l'événement
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/evenements");
            return;
        }

        try {
            Long id = Long.parseLong(idStr);
            Evenement evenement = evenementDAO.findById(id);

            if (evenement == null) {
                response.sendRedirect(request.getContextPath() + "/evenements");
                return;
            }

            // Vérifier si l'utilisateur est l'organisateur de l'événement
            Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
            if (!evenement.getUtilisateur().getId().equals(utilisateur.getId())) {
                response.sendRedirect(request.getContextPath() + "/evenements");
                return;
            }

            // Mise en place des attributs pour la vue des détails de l'événement
            request.setAttribute("evenement", evenement);
            request.setAttribute("titre", "Détails de l'Événement");
            request.setAttribute("contenu", "/WEB-INF/includes/content/detailsEvenement.jsp");
            request.getRequestDispatcher("/WEB-INF/templates/template.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/evenements");
        }
    }

    private void createEvenement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération de l'utilisateur connecté
        Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");

        // Récupération des paramètres du formulaire de création
        String titre = request.getParameter("titre");
        String dateStr = request.getParameter("dateEvenement");
        String cadeauIdStr = request.getParameter("cadeauId");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateEvenement;

        try {
            // Conversion de la date de l'événement
            dateEvenement = sdf.parse(dateStr);
        } catch (ParseException e) {
            // Gestion de l'erreur de format de date
            request.setAttribute("erreur", "Format de date invalide");
            showCreateEvenementForm(request, response);
            return;
        }

        Cadeau cadeau;
        try {
            // Conversion de l'identifiant du cadeau
            Long cadeauId = Long.parseLong(cadeauIdStr);
            cadeau = cadeauDAO.findById(cadeauId);

            if (cadeau == null) {
                // Gestion de l'erreur si le cadeau n'est pas trouvé
                request.setAttribute("erreur", "Cadeau non trouvé");
                showCreateEvenementForm(request, response);
                return;
            }
        } catch (NumberFormatException e) {
            // Gestion de l'erreur de format d'identifiant de cadeau
            request.setAttribute("erreur", "ID de cadeau invalide");
            showCreateEvenementForm(request, response);
            return;
        }

        // Création et sauvegarde du nouvel événement
        Evenement evenement = new Evenement(titre, dateEvenement, utilisateur, cadeau);
        evenementDAO.save(evenement);

        // Redirection vers les détails de l'événement créé
        response.sendRedirect(request.getContextPath() + "/evenement/details?id=" + evenement.getId());
    }
}