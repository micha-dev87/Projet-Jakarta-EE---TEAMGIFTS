<%@ page contentType="text/html; charset=UTF-8"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h2>Mes Invitations</h2>

<c:if test="${empty invitations}">
    <div class="alert alert-info mt-4">
        Vous n'avez pas encore reçu d'invitation.
    </div>
</c:if>

<c:if test="${not empty invitations}">
    <div class="row mt-4">
        <div class="col-md-12">
            <ul class="nav nav-tabs" id="invitationTabs" role="tablist">
                <li class="nav-item" role="presentation">
                    <button class="nav-link active" id="pending-tab" data-bs-toggle="tab" data-bs-target="#pending" type="button" role="tab" aria-controls="pending" aria-selected="true">
                        En attente
                    </button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="accepted-tab" data-bs-toggle="tab" data-bs-target="#accepted" type="button" role="tab" aria-controls="accepted" aria-selected="false">
                        Acceptées
                    </button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="declined-tab" data-bs-toggle="tab" data-bs-target="#declined" type="button" role="tab" aria-controls="declined" aria-selected="false">
                        Refusées
                    </button>
                </li>
            </ul>
            
            <div class="tab-content" id="invitationTabsContent">
                <div class="tab-pane fade show active" id="pending" role="tabpanel" aria-labelledby="pending-tab">
                    <div class="table-responsive mt-3">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>Événement</th>
                                    <th>Date</th>
                                    <th>Organisateur</th>
                                    <th>Cadeau</th>
                                    <th>Prix</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="pendingFound" value="false" />
                                <c:forEach var="invitation" items="${invitations}">
                                    <c:if test="${invitation.etat ne 'accepte' && invitation.etat ne 'refuse'}">
                                        <c:set var="pendingFound" value="true" />
                                        <tr>
                                            <td>${invitation.evenement.titre}</td>
                                            <td><fmt:formatDate value="${invitation.evenement.dateEvenement}" pattern="dd/MM/yyyy" /></td>
                                            <td>${invitation.evenement.utilisateur.nomComplet}</td>
                                            <td>${invitation.evenement.cadeau.titre}</td>
                                            <td>${invitation.evenement.cadeau.prix} €</td>
                                            <td>
                                                <div class="btn-group" role="group">
                                                    <a href="${pageContext.request.contextPath}/invitation?action=accepter&id=${invitation.evenement.id}" class="btn btn-success btn-sm">Accepter</a>
                                                    <a href="${pageContext.request.contextPath}/invitation?action=refuser&id=${invitation.evenement.id}" class="btn btn-danger btn-sm">Refuser</a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                                
                                <c:if test="${!pendingFound}">
                                    <tr>
                                        <td colspan="6" class="text-center">Aucune invitation en attente.</td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
                
                <div class="tab-pane fade" id="accepted" role="tabpanel" aria-labelledby="accepted-tab">
                    <div class="table-responsive mt-3">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>Événement</th>
                                    <th>Date</th>
                                    <th>Organisateur</th>
                                    <th>Cadeau</th>
                                    <th>Prix</th>
                                    <th>Ma contribution</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="acceptedFound" value="false" />
                                <c:forEach var="invitation" items="${invitations}">
                                    <c:if test="${invitation.etat eq 'accepte'}">
                                        <c:set var="acceptedFound" value="true" />
                                        <tr>
                                            <td>${invitation.evenement.titre}</td>
                                            <td><fmt:formatDate value="${invitation.evenement.dateEvenement}" pattern="dd/MM/yyyy" /></td>
                                            <td>${invitation.evenement.utilisateur.nomComplet}</td>
                                            <td>${invitation.evenement.cadeau.titre}</td>
                                            <td>${invitation.evenement.cadeau.prix} €</td>
                                            <td>${invitation.contribution} €</td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/invitation/contribuer?id=${invitation.id}" class="btn btn-primary btn-sm">Contribuer</a>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                                
                                <c:if test="${!acceptedFound}">
                                    <tr>
                                        <td colspan="7" class="text-center">Aucune invitation acceptée.</td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
                
                <div class="tab-pane fade" id="declined" role="tabpanel" aria-labelledby="declined-tab">
                    <div class="table-responsive mt-3">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>Événement</th>
                                    <th>Date</th>
                                    <th>Organisateur</th>
                                    <th>Cadeau</th>
                                    <th>Prix</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="declinedFound" value="false" />
                                <c:forEach var="invitation" items="${invitations}">
                                    <c:if test="${invitation.etat eq 'refuse'}">
                                        <c:set var="declinedFound" value="true" />
                                        <tr>
                                            <td>${invitation.evenement.titre}</td>
                                            <td><fmt:formatDate value="${invitation.evenement.dateEvenement}" pattern="dd/MM/yyyy" /></td>
                                            <td>${invitation.evenement.utilisateur.nomComplet}</td>
                                            <td>${invitation.evenement.cadeau.titre}</td>
                                            <td>${invitation.evenement.cadeau.prix} €</td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/invitation?action=accepter&id=${invitation.evenement.id}" class="btn btn-outline-success btn-sm">Changer d'avis</a>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                                
                                <c:if test="${!declinedFound}">
                                    <tr>
                                        <td colspan="6" class="text-center">Aucune invitation refusée.</td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if> 