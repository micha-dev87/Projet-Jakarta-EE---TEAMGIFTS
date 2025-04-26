<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row justify-content-center">
    <div class="col-md-8">
        <div class="card">
            <div class="card-header bg-primary text-white">
                <h3>Envoyer une Invitation</h3>
            </div>
            <div class="card-body">
                <div class="alert alert-info mb-4">
                    <h5>Informations sur l'événement</h5>
                    <p><strong>Titre:</strong> ${evenement.titre}</p>
                    <p><strong>Date:</strong> <fmt:formatDate value="${evenement.dateEvenement}" pattern="dd/MM/yyyy" /></p>
                    <p><strong>Cadeau:</strong> ${evenement.cadeau.titre}</p>
                    <p><strong>Prix:</strong> ${evenement.cadeau.prix} $CAD</p>
                </div>
                
                <c:if test="${not empty message}">
                    <div class="alert alert-success" role="alert">
                        ${message}
                    </div>
                </c:if>
                <c:if test="${not empty erreur}">
                    <div class="alert alert-danger" role="alert">
                        ${erreur}
                    </div>
                </c:if>
                
                <form action="${pageContext.request.contextPath}/invitation/envoyer" method="post">
                    <input type="hidden" name="evenementId" value="${evenement.id}">

                    <div class="mb-3">      
                        <label for="nomParticipant" class="form-label">Nom du participant</label>
                        <input type="text" class="form-control" id="nomParticipant" name="nomParticipant" required>
                    </div>
                    
                    <div class="mb-3">
                        <label for="courrielParticipant" class="form-label">Courriel du participant</label>
                        <input type="email" class="form-control" id="courrielParticipant" name="courrielParticipant" required>
                        <div class="form-text">Si le participant n'a pas encore de compte, un compte sera créé automatiquement.</div>
                    </div>
                    
                    <div class="d-flex justify-content-between">
                        <a href="${pageContext.request.contextPath}/evenement/details?id=${evenement.id}" class="btn btn-secondary">Retour aux détails</a>
                        <button type="submit" class="btn btn-primary">Envoyer l'invitation</button>
                    </div>
                </form>
            </div>
        </div>
        
        <c:if test="${not empty evenement.invitations}">
            <div class="card mt-4">
                <div class="card-header bg-info text-white">
                    <h4>Invitations déjà envoyées</h4>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>Participant</th>
                                    <th>Courriel</th>
                                    <th>Statut</th>
                                    <th>Contribution</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="invitation" items="${evenement.invitations}">
                                    <tr>
                                        <td>${invitation.participant.nomComplet}</td>
                                        <td>${invitation.participant.courriel}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${invitation.etat eq 'accepte'}">
                                                    <span class="badge bg-success">Accepté</span>
                                                </c:when>
                                                <c:when test="${invitation.etat eq 'refuse'}">
                                                    <span class="badge bg-danger">Refusé</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-warning text-dark">En attente</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${invitation.contribution} $CAD</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/invitation/supprimer?id=${invitation.id}" class="btn btn-danger">Supprimer</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </c:if>
    </div>
</div> 