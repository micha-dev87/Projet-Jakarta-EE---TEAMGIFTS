<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2>Mes Événements</h2>
    <a href="${pageContext.request.contextPath}/evenement/creer" class="btn btn-primary">Créer un événement</a>
</div>

<c:if test="${empty evenements}">
    <div class="alert alert-info">
        Vous n'avez pas encore créé d'événement.
    </div>
</c:if>

<c:if test="${not empty evenements}">
    <div class="row row-cols-1 row-cols-md-3 g-4">
        <c:forEach var="evenement" items="${evenements}">
            <div class="col">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title">${evenement.titre}</h5>
                        <p class="card-text">
                            <strong>Date:</strong> <fmt:formatDate value="${evenement.dateEvenement}" pattern="dd/MM/yyyy" />
                        </p>
                        <p class="card-text">
                            <strong>Cadeau:</strong> ${evenement.cadeau.titre}
                        </p>
                        <p class="card-text">
                            <strong>Prix:</strong> ${evenement.cadeau.prix} €
                        </p>
                    </div>
                    <div class="card-footer">
                        <div class="d-flex justify-content-between">
                            <a href="${pageContext.request.contextPath}/evenement/details?id=${evenement.id}" class="btn btn-outline-primary btn-sm">Détails</a>
                            <a href="${pageContext.request.contextPath}/invitation/envoyer?evenementId=${evenement.id}" class="btn btn-outline-success btn-sm">Inviter</a>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</c:if> 