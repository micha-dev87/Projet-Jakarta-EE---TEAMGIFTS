<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row justify-content-center">
    <div class="col-md-8">
        <div class="card">
            <div class="card-header bg-primary text-white">
                <h3>Contribuer à l'achat du cadeau</h3>
            </div>
            <div class="card-body">
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
                
                <div class="mb-4">
                    <h5>Informations sur l'événement</h5>
                    <p><strong>Titre:</strong> ${invitation.evenement.titre}</p>
                    <p><strong>Date:</strong> <fmt:formatDate value="${invitation.evenement.dateEvenement}" pattern="dd/MM/yyyy" /></p>
                    <p><strong>Organisateur:</strong> ${invitation.evenement.utilisateur.nomComplet}</p>
                </div>
                
                <div class="mb-4">
                    <h5>Informations sur le cadeau</h5>
                    <div class="row">
                        <div class="col-md-4">
                            <img src="${invitation.evenement.cadeau.urlPhoto}" class="img-fluid rounded" alt="${invitation.evenement.cadeau.titre}">
                        </div>
                        <div class="col-md-8">
                            <p><strong>Cadeau:</strong> ${invitation.evenement.cadeau.titre}</p>
                            <p><strong>Prix:</strong> ${invitation.evenement.cadeau.prix} €</p>
                            
                            <c:set var="totalContributions" value="0" />
                            <c:forEach var="inv" items="${invitation.evenement.invitations}">
                                <c:set var="totalContributions" value="${totalContributions + inv.contribution}" />
                            </c:forEach>
                            
                            <p><strong>Montant collecté:</strong> ${totalContributions} € (${Math.round((totalContributions / invitation.evenement.cadeau.prix) * 100)}%)</p>
                            <p><strong>Montant restant:</strong> ${invitation.evenement.cadeau.prix - totalContributions > 0 ? invitation.evenement.cadeau.prix - totalContributions : 0} €</p>
                            
                            <div class="progress mb-3">
                                <c:set var="progress" value="${(totalContributions / invitation.evenement.cadeau.prix) * 100}" />
                                <c:set var="progress" value="${progress > 100 ? 100 : progress}" />
                                <div class="progress-bar bg-success" role="progressbar" style="width: ${progress}%" 
                                     aria-valuenow="${progress}" aria-valuemin="0" aria-valuemax="100">
                                    <fmt:formatNumber value="${progress}" pattern="#0.0" />%
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                
                <form action="${pageContext.request.contextPath}/invitation/contribuer" method="post">
                    <input type="hidden" name="id" value="${invitation.id}">
                    
                    <div class="mb-3">
                        <label for="contribution" class="form-label">Montant de votre contribution ($CAD)</label>
                        <input type="number" step="0.01" min="0" class="form-control" id="contribution" name="contribution" 
                               value="${invitation.contribution > 0 ? invitation.contribution : ''}" required>
                    </div>
                    
                    <div class="d-flex justify-content-between">
                        <a href="${pageContext.request.contextPath}/invitations" class="btn btn-secondary">Retour aux invitations</a>
                        <button type="submit" class="btn btn-primary">Enregistrer ma contribution</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div> 