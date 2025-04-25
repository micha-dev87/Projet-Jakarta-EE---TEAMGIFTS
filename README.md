# Projet Jakarta EE - TEAMGIFTS

## Description

"TeamGifts" est une application web qui permet l'organisation collective de cadeaux. Un utilisateur peut s'inscrire, créer un événement pour offrir un cadeau à quelqu'un, sélectionner ce cadeau depuis un catalogue, envoyer des invitations par courriel aux participants potentiels qui peuvent accepter ou refuser l'invitation, puis contribuer financièrement à l'achat du cadeau selon le montant de leur choix.

Le projet est développé avec Jakarta EE, utilisant les technologies Servlet, JSP et JPA avec une base de données MySQL pour le stockage des données.

## Caractéristiques

- Basé sur Jakarta EE
- Utilise Hibernate pour la persistance des données
- Interface utilisateur avec Bootstrap et jQuery
- Support JSTL pour les pages JSP
- Fonctionnalité d'envoi d'emails avec JavaMail

## Prérequis

- JDK 23
- Apache Tomcat 10+
- MySQL Server
- Maven

## Configuration et déploiement

### Configuration de la base de données

1. Créez une base de données MySQL pour le projet
2. Configurez les informations de connexion dans le fichier de configuration approprié

### Compilation du projet

```bash
mvn clean package
```

### Déploiement avec Tomcat

1. Copiez le fichier WAR généré (`target/mini-projet2-1.0-SNAPSHOT.war`) dans le répertoire `webapps` de Tomcat
2. Ou utilisez le plugin Maven pour Tomcat:

```bash
mvn tomcat7:deploy
```

### Déploiement depuis IntelliJ IDEA

1. Allez dans le menu "Run" > "Edit Configurations"
2. Cliquez sur le "+" et sélectionnez "Tomcat Server" > "Local"
3. Configurez votre serveur Tomcat dans l'onglet "Server"
4. Dans l'onglet "Deployment", ajoutez l'artifact WAR du projet
5. Définissez le chemin de contexte (par exemple: `/mini-projet2`)
6. Cliquez sur "OK" et exécutez la configuration

## Accès à l'application

Une fois déployée, l'application sera accessible à l'adresse:

```
http://localhost:8080/mini-projet2
```

## Auteur

Michel Ange Tamgho Fogue
