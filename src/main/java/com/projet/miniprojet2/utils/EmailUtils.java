package com.projet.miniprojet2.utils;

import com.projet.miniprojet2.models.Utilisateur;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailUtils {

    // Variables pour stocker les informations d'identification
    private static String EMAIL_FROM;
    private static String EMAIL_PASSWORD;
    private static String SMTP_HOST;
    private static String SMTP_PORT;

    // Bloc statique pour charger les propriétés au démarrage
    static {
        try {
            loadProperties();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des propriétés email: " + e.getMessage());
        }
    }

    // Méthode pour charger les propriétés depuis le fichier
    private static void loadProperties() throws IOException {
        Properties props = new Properties();
        try (InputStream input = EmailUtils.class.getClassLoader().getResourceAsStream("config/email.properties")) {
            if (input == null) {
                System.err.println("Impossible de trouver config/email.properties");
                return;
            }

            // Chargement des propriétés
            props.load(input);

            // Récupération des valeurs
            EMAIL_FROM = props.getProperty("email.from");
            EMAIL_PASSWORD = props.getProperty("email.password");
            SMTP_HOST = props.getProperty("email.smtp.host");
            SMTP_PORT = props.getProperty("email.smtp.port");
        }
    }

    // Méthode pour envoyer un email générique
    public static void envoyerEmail(String destinataire, String sujet, String contenu) throws MessagingException {
        // Configuration des propriétés pour la connexion SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        // Création d'une session email avec authentification
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, EMAIL_PASSWORD);
            }
        });

        // Création du message email
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL_FROM));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
        message.setSubject(sujet);
        message.setContent(contenu, "text/html; charset=utf-8");

        // Envoi du message
        Transport.send(message);
    }

    // Méthode pour envoyer une invitation à un événement
    public static void envoyerInvitation(Utilisateur destinataire, String titreEvenement, String organisateur,
            Long idEvenement)
            throws MessagingException {
        // Sujet de l'email d'invitation
        String sujet = "Invitation à participer à l'événement : " + titreEvenement;

        // Liens pour accepter ou refuser l'invitation
        String lienAcceptation = "http://localhost:3002/mini_projet2_war_exploded/invitation?action=accepter&id="
                + idEvenement
                + "&idParticipant=" + destinataire.getId();
        String lienRefus = "http://localhost:3002/mini_projet2_war_exploded/invitation?action=refuser&id=" + idEvenement
                + "&idParticipant=" + destinataire.getId();

        // Contenu HTML de l'email d'invitation
        String contenu = "<html><body>"
                + "<h2>Invitation à participer à un cadeau commun</h2>"
                + "<p>" + organisateur + " vous invite à participer à l'achat d'un cadeau pour l'événement : <strong>"
                + titreEvenement + "</strong></p>"
                + "<p>Pour répondre à cette invitation, veuillez cliquer sur l'un des liens suivants :</p>"
                + "<p><a href='" + lienAcceptation + "'>Accepter l'invitation</a></p>"
                + "<p><a href='" + lienRefus + "'>Refuser l'invitation</a></p>"
                + "</body></html>";

        // Envoi de l'email d'invitation
        envoyerEmail(destinataire.getCourriel(), sujet, contenu);
    }

    // Méthode pour envoyer une invitation à un événement avec email en paramètre
    public static void envoyerInvitation(String destinataireEmail, String titreEvenement, String organisateur,
            Long idEvenement)
            throws MessagingException {
        // Sujet de l'email d'invitation
        String sujet = "Invitation à participer à l'événement : " + titreEvenement;

        // Liens pour accepter ou refuser l'invitation
        String lienAcceptation = "http://localhost:3002/mini_projet2_war_exploded/invitation?action=accepter&id="
                + idEvenement;
        String lienRefus = "http://localhost:3002/mini_projet2_war_exploded/invitation?action=refuser&id="
                + idEvenement;

        // Contenu HTML de l'email d'invitation
        String contenu = "<html><body>"
                + "<h2>Invitation à participer à un cadeau commun</h2>"
                + "<p>" + organisateur + " vous invite à participer à l'achat d'un cadeau pour l'événement : <strong>"
                + titreEvenement + "</strong></p>"
                + "<p>Pour répondre à cette invitation, veuillez cliquer sur l'un des liens suivants :</p>"
                + "<p><a href='" + lienAcceptation + "'>Accepter l'invitation</a></p>"
                + "<p><a href='" + lienRefus + "'>Refuser l'invitation</a></p>"
                + "</body></html>";

        // Envoi de l'email d'invitation
        envoyerEmail(destinataireEmail, sujet, contenu);
    }
}