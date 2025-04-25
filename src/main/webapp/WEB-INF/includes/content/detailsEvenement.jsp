<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
    <div class="col-md-8">
        <div class="card">
            <div class="card-header bg-primary text-white">
                <h3>${evenement.titre}</h3>
            </div>
            <div class="card-body">
                <div class="row mb-4">
                    <div class="col-md-6">
                        <h5>Informations sur l'événement</h5>
                        <p><strong>Date:</strong> <fmt:formatDate value="${evenement.dateEvenement}" pattern="dd/MM/yyyy" /></p>
                        <p><strong>Organisateur:</strong> ${evenement.utilisateur.nomComplet}</p>
                    </div>
                    <div class="col-md-6">
                        <div class="d-grid gap-2">
                            <a href="${pageContext.request.contextPath}/invitation/envoyer?evenementId=${evenement.id}" class="btn btn-success">Inviter des participants</a>
                        </div>
                    </div>
                </div>
                
                <h5>Cadeau sélectionné</h5>
                <div class="card mb-4">
                    <div class="row g-0">
                        <div class="col-md-4">
                            <img src="${evenement.cadeau.urlPhoto}" class="img-fluid rounded-start" alt="${evenement.cadeau.titre}">
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 class="card-title">${evenement.cadeau.titre}</h5>
                                <p class="card-text"><strong>Prix:</strong> ${evenement.cadeau.prix} €</p>
                            </div>
                        </div>
                    </div>
                </div>
                
                <h5>Participants</h5>
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Participant</th>
                            <th>Statut</th>
                            <th>Contribution</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="invitation" items="${evenement.invitations}">
                            <tr>
                                <td>${invitation.participant.nomComplet}</td>
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
                                <td>${invitation.contribution} €</td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty evenement.invitations}">
                            <tr>
                                <td colspan="3" class="text-center">Aucun participant pour le moment</td>
                            </tr>
                        </c:if>
                    </tbody>
                    <c:if test="${not empty evenement.invitations}">
                        <tfoot>
                            <tr>
                                <td colspan="2" class="text-end"><strong>Total des contributions:</strong></td>
                                <td>
                                    <c:set var="totalContributions" value="0" />
                                    <c:forEach var="invitation" items="${evenement.invitations}">
                                        <c:set var="totalContributions" value="${totalContributions + invitation.contribution}" />
                                    </c:forEach>
                                    ${totalContributions} € / ${evenement.cadeau.prix} €
                                </td>
                            </tr>
                        </tfoot>
                    </c:if>
                </table>
            </div>
            <div class="card-footer">
                <a href="${pageContext.request.contextPath}/evenements" class="btn btn-secondary">Retour aux événements</a>
            </div>
        </div>
    </div>
    
    <div class="col-md-4">
        <div class="card">
            <div class="card-header bg-info text-white">
                <h4>Résumé</h4>
            </div>
            <div class="card-body">
                <c:set var="totalContributions" value="0" />
                <c:forEach var="invitation" items="${evenement.invitations}">
                    <c:set var="totalContributions" value="${totalContributions + invitation.contribution}" />
                </c:forEach>
                
                <c:set var="progress" value="${(totalContributions / evenement.cadeau.prix) * 100}" />
                <c:set var="progress" value="${progress > 100 ? 100 : progress}" />
                
                <h5>Progression</h5>
                <div class="progress mb-3">
                    <div class="progress-bar bg-success" role="progressbar" style="width: ${progress}%" 
                         aria-valuenow="${progress}" aria-valuemin="0" aria-valuemax="100">
                        <fmt:formatNumber value="${progress}" pattern="#0.0" />%
                    </div>
                </div>
                
                <p><strong>Montant collecté:</strong> ${totalContributions} €</p>
                <p><strong>Montant restant:</strong> ${evenement.cadeau.prix - totalContributions > 0 ? evenement.cadeau.prix - totalContributions : 0} €</p>
                
                <c:set var="acceptes" value="0" />
                <c:set var="refuses" value="0" />
                <c:set var="enAttente" value="0" />
                
                <c:forEach var="invitation" items="${evenement.invitations}">
                    <c:choose>
                        <c:when test="${invitation.etat eq 'accepte'}">
                            <c:set var="acceptes" value="${acceptes + 1}" />
                        </c:when>
                        <c:when test="${invitation.etat eq 'refuse'}">
                            <c:set var="refuses" value="${refuses + 1}" />
                        </c:when>
                        <c:otherwise>
                            <c:set var="enAttente" value="${enAttente + 1}" />
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                
                <h5 class="mt-4">Statistiques</h5>
                <p><strong>Participants:</strong> ${acceptes}</p>
                <p><strong>Refus:</strong> ${refuses}</p>
                <p><strong>En attente:</strong> ${enAttente}</p>
            </div>
        </div>
    </div>
</div> 