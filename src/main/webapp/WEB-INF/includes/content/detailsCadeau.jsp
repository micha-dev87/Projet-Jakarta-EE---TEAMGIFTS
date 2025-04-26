<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row justify-content-center">
    <div class="col-md-8">
        <div class="card">
            <div class="card-header bg-primary text-white">
                <h3>Détails du Cadeau</h3>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-6">
                        <c:choose>
                            <c:when test="${cadeau.urlPhoto.contains('https://')}">
                                <img src="${cadeau.urlPhoto}" class="img-fluid rounded" alt="${cadeau.titre}">
                            </c:when>
                            <c:otherwise>
                                <img src="${pageContext.request.contextPath}${cadeau.urlPhoto}" class="img-fluid rounded" alt="${cadeau.titre}">
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-md-6">
                        <h2>${cadeau.titre}</h2>
                        <h4 class="text-primary">${cadeau.prix} $CAD</h4>
                        <hr>
                        <div class="d-grid gap-2 mt-4">
                            <a href="${pageContext.request.contextPath}/evenement/creer?idCadeau=${cadeau.id}" class="btn btn-success">Créer un événement avec ce cadeau</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card-footer">
                <a href="${pageContext.request.contextPath}/cadeaux" class="btn btn-secondary">Retour au catalogue</a>
            </div>
        </div>
        
        <c:if test="${not empty cadeau.evenements}">
            <div class="card mt-4">
                <div class="card-header bg-info text-white">
                    <h4>Événements associés à ce cadeau</h4>
                </div>
                <div class="card-body">
                    <ul class="list-group">
                        <c:forEach var="evenement" items="${cadeau.evenements}">
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                ${evenement.titre}
                                <span class="badge bg-primary rounded-pill">
                                    <c:choose>
                                        <c:when test="${evenement.utilisateur.id eq utilisateur.id}">
                                            <a href="${pageContext.request.contextPath}/evenement/details?id=${evenement.id}" class="text-white text-decoration-none">Voir</a>
                                        </c:when>
                                        <c:otherwise>
                                            Organisé par ${evenement.utilisateur.nomComplet}
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </c:if>
    </div>
</div> 