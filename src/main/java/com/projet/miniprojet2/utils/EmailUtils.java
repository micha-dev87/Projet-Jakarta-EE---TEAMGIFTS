package com.projet.miniprojet2.utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtils {

    // Adresse email de l'expéditeur
    private static final String EMAIL_FROM = "teamgifts@example.com";
    // Mot de passe de l'adresse email de l'expéditeur
    private static final String EMAIL_PASSWORD = "motdepasse";
    // Hôte SMTP pour l'envoi des emails
    private static final String SMTP_HOST = "smtp.example.com";
    // Port SMTP utilisé pour l'envoi des emails
    private static final String SMTP_PORT = "587";

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
    public static void envoyerInvitation(String destinataire, String titreEvenement, String organisateur,
            Long idEvenement)
            throws MessagingException {
        // Sujet de l'email d'invitation
        String sujet = "Invitation à participer à l'événement : " + titreEvenement;

        // Liens pour accepter ou refuser l'invitation
        String lienAcceptation = "http://localhost:8080/mini-projet2/invitation?action=accepter&id=" + idEvenement;
        String lienRefus = "http://localhost:8080/mini-projet2/invitation?action=refuser&id=" + idEvenement;

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
        envoyerEmail(destinataire, sujet, contenu);
    }
}