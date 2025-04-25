package com.projet.miniprojet2.servlets;

import com.projet.miniprojet2.dao.EvenementDAO;
import com.projet.miniprojet2.dao.InvitationDAO;
import com.projet.miniprojet2.dao.UtilisateurDAO;
import com.projet.miniprojet2.models.Evenement;
import com.projet.miniprojet2.models.Invitation;
import com.projet.miniprojet2.models.Utilisateur;
import com.projet.miniprojet2.utils.EmailUtils;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "InvitationServlet", urlPatterns = { "/invitations", "/invitation/envoyer", "/invitation", "/invitation/contribuer" })
public class InvitationServlet extends HttpServlet {

    // DAO pour interagir avec les invitations dans la base de données
    private final InvitationDAO invitationDAO = new InvitationDAO();
    // DAO pour interagir avec les événements dans la base de données
    private final EvenementDAO evenementDAO = new EvenementDAO();
    // DAO pour interagir avec les utilisateurs dans la base de données
    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération du chemin de la requête
        String path = request.getServletPath();

        // Gestion des différentes actions en fonction du chemin
        switch (path) {
            case "/invitations":
                listInvitations(request, response);
                break;
            case "/invitation/envoyer":
                showEnvoyerInvitationForm(request, response);
                break;
            case "/invitation":
                handleInvitationResponse(request, response);
                break;
            case "/invitation/contribuer":
                showContribuerForm(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/evenements");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération du chemin de la requête
        String path = request.getServletPath();

        // Gestion des actions POST en fonction du chemin
        switch (path) {
            case "/invitation/envoyer":
                envoyerInvitation(request, response);
                break;
            case "/invitation/contribuer":
                contribuer(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/evenements");
                break;
        }
    }

    private void listInvitations(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Vérification de la session utilisateur
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Récupération de l'utilisateur connecté
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        // Récupération des invitations pour l'utilisateur
        List<Invitation> invitations = invitationDAO.findByParticipant(utilisateur);

        // Configuration des attributs de la requête pour l'affichage
        request.setAttribute("invitations", invitations);
        request.setAttribute("titre", "Mes Invitations");
        request.setAttribute("contenu", "/WEB-INF/includes/content/invitations.jsp");
        request.getRequestDispatcher("/WEB-INF/templates/template.jsp").forward(request, response);
    }

    private void showEnvoyerInvitationForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Vérification de la session utilisateur
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Récupération de l'identifiant de l'événement
        String evenementIdStr = request.getParameter("evenementId");
        if (evenementIdStr == null || evenementIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/evenements");
            return;
        }

        try {
            Long evenementId = Long.parseLong(evenementIdStr);
            Evenement evenement = evenementDAO.findById(evenementId);

            if (evenement == null) {
                response.sendRedirect(request.getContextPath() + "/evenements");
                return;
            }

            // Vérifier si l'utilisateur est l'organisateur de l'événement
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            if (!evenement.getUtilisateur().getId().equals(utilisateur.getId())) {
                response.sendRedirect(request.getContextPath() + "/evenements");
                return;
            }

            // Configuration des attributs de la requête pour l'affichage
            request.setAttribute("evenement", evenement);
            request.setAttribute("titre", "Envoyer une Invitation");
            request.setAttribute("contenu", "/WEB-INF/includes/content/envoyerInvitation.jsp");
            request.getRequestDispatcher("/WEB-INF/templates/template.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/evenements");
        }
    }

    private void envoyerInvitation(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Vérification de la session utilisateur
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Récupération des paramètres de la requête
        String evenementIdStr = request.getParameter("evenementId");
        String nomParticipant = request.getParameter("nomParticipant");
        String courrielParticipant = request.getParameter("courrielParticipant");
     

        if (evenementIdStr == null || evenementIdStr.isEmpty() || courrielParticipant == null
                || courrielParticipant.isEmpty() || nomParticipant == null || nomParticipant.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/evenements");
            return;
        }

        try {
            // Conversion de l'identifiant de l'événement en long
            Long evenementId = Long.parseLong(evenementIdStr);
            // Récupération de l'événement correspondant à l'identifiant
            Evenement evenement = evenementDAO.findById(evenementId);

            // Vérifier si l'événement existe
            if (evenement == null) {
                response.sendRedirect(request.getContextPath() + "/evenements");
                return;
            }

            // Vérifier si l'utilisateur est l'organisateur de l'événement
            Utilisateur organisateur = (Utilisateur) session.getAttribute("utilisateur");
            if (!evenement.getUtilisateur().getId().equals(organisateur.getId())) {
                response.sendRedirect(request.getContextPath() + "/evenements");
                return;
            }

            // Vérifier si le participant existe, sinon le créer
            Utilisateur participant = utilisateurDAO.findByCourriel(courrielParticipant);
            if (participant == null) {
                participant = new Utilisateur(nomParticipant, courrielParticipant, "password123");
                utilisateurDAO.save(participant);
            }

            // Vérifier si une invitation existe déjà
            Invitation existingInvitation = invitationDAO.findByEvenementAndParticipant(evenement, participant);
            if (existingInvitation != null) {
                request.setAttribute("erreur", "Une invitation a déjà été envoyée à ce participant");
                request.setAttribute("evenement", evenement);
                request.setAttribute("titre", "Envoyer une Invitation");
                request.setAttribute("contenu", "/WEB-INF/includes/content/envoyerInvitation.jsp");
                request.getRequestDispatcher("/WEB-INF/templates/template.jsp").forward(request, response);
                return;
            }

            // Créer et enregistrer l'invitation
            Invitation invitation = new Invitation(evenement, participant, "en_attente", BigDecimal.ZERO);
            invitationDAO.save(invitation);

            // Envoyer l'email d'invitation
            try {
                EmailUtils.envoyerInvitation(
                        participant.getCourriel(),
                        evenement.getTitre(),
                        organisateur.getNomComplet(),
                        evenement.getId());

                request.setAttribute("message", "Invitation envoyée avec succès");
                request.setAttribute("evenement", evenement);
                request.setAttribute("titre", "Envoyer une Invitation");
                request.setAttribute("contenu", "/WEB-INF/includes/content/envoyerInvitation.jsp");
                request.getRequestDispatcher("/WEB-INF/templates/template.jsp").forward(request, response);

            } catch (MessagingException e) {
                request.setAttribute("erreur", "Erreur lors de l'envoi de l'email: " + e.getMessage());
                request.setAttribute("evenement", evenement);
                request.setAttribute("titre", "Envoyer une Invitation");
                request.setAttribute("contenu", "/WEB-INF/includes/content/envoyerInvitation.jsp");
                request.getRequestDispatcher("/WEB-INF/templates/template.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/evenements");
        }
    }

    private void handleInvitationResponse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération des paramètres de la requête
        String action = request.getParameter("action");
        String idStr = request.getParameter("id");

        if (action == null || idStr == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            Long evenementId = Long.parseLong(idStr);
            Evenement evenement = evenementDAO.findById(evenementId);

            if (evenement == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Si l'utilisateur n'est pas connecté, le rediriger vers la page de connexion
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("utilisateur") == null) {
                // Stocker les paramètres dans la session pour les récupérer après la connexion
                session = request.getSession(true);
                session.setAttribute("redirectAction", action);
                session.setAttribute("redirectEvenementId", evenementId);

                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Récupération de l'utilisateur connecté
            Utilisateur participant = (Utilisateur) session.getAttribute("utilisateur");
            // Récupération de l'invitation pour l'utilisateur et l'événement
            Invitation invitation = invitationDAO.findByEvenementAndParticipant(evenement, participant);

            if (invitation == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Gestion de la réponse à l'invitation
            if ("accepter".equals(action)) {
                invitation.setEtat("accepte");
                invitationDAO.save(invitation);
                response.sendRedirect(request.getContextPath() + "/invitation/contribuer?id=" + invitation.getId());
            } else if ("refuser".equals(action)) {
                invitation.setEtat("refuse");
                invitationDAO.save(invitation);
                request.setAttribute("message", "Vous avez refusé l'invitation.");
                request.setAttribute("titre", "Réponse à l'invitation");
                request.setAttribute("contenu", "/WEB-INF/includes/content/reponseInvitation.jsp");
                request.getRequestDispatcher("/WEB-INF/templates/template.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    private void showContribuerForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Vérification de la session utilisateur
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Récupération de l'identifiant de l'invitation
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/invitations");
            return;
        }

        try {
            Long id = Long.parseLong(idStr);
            Invitation invitation = invitationDAO.findById(id);

            if (invitation == null || !"accepte".equals(invitation.getEtat())) {
                response.sendRedirect(request.getContextPath() + "/invitations");
                return;
            }

            // Vérification de l'utilisateur connecté
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            if (!invitation.getParticipant().getId().equals(utilisateur.getId())) {
                response.sendRedirect(request.getContextPath() + "/invitations");
                return;
            }

            // Configuration des attributs de la requête pour l'affichage
            request.setAttribute("invitation", invitation);
            request.setAttribute("titre", "Contribuer à l'événement");
            request.setAttribute("contenu", "/WEB-INF/includes/content/contribuer.jsp");
            request.getRequestDispatcher("/WEB-INF/templates/template.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/invitations");
        }
    }

    private void contribuer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Vérification de la session utilisateur
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Récupération des paramètres de la requête
        String idStr = request.getParameter("id");
        String contributionStr = request.getParameter("contribution");

        if (idStr == null || idStr.isEmpty() || contributionStr == null || contributionStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/invitations");
            return;
        }

        try {
            Long id = Long.parseLong(idStr);
            BigDecimal contribution = new BigDecimal(contributionStr);

            // Vérification de la validité de la contribution
            if (contribution.compareTo(BigDecimal.ZERO) <= 0) {
                request.setAttribute("erreur", "La contribution doit être positive");
                showContribuerForm(request, response);
                return;
            }

            Invitation invitation = invitationDAO.findById(id);

            if (invitation == null || !"accepte".equals(invitation.getEtat())) {
                response.sendRedirect(request.getContextPath() + "/invitations");
                return;
            }

            // Vérification de l'utilisateur connecté
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            if (!invitation.getParticipant().getId().equals(utilisateur.getId())) {
                response.sendRedirect(request.getContextPath() + "/invitations");
                return;
            }

            // Enregistrement de la contribution
            invitation.setContribution(contribution);
            invitationDAO.save(invitation);

            // Configuration des attributs de la requête pour l'affichage
            request.setAttribute("message", "Contribution enregistrée avec succès");
            request.setAttribute("invitation", invitation);
            request.setAttribute("titre", "Contribuer à l'événement");
            request.setAttribute("contenu", "/WEB-INF/includes/content/contribuer.jsp");
            request.getRequestDispatcher("/WEB-INF/templates/template.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("erreur", "Format de contribution invalide");
            showContribuerForm(request, response);
        }
    }
}