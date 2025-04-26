<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2>Catalogue de Cadeaux</h2>
    <a href="${pageContext.request.contextPath}/cadeau/creer" class="btn btn-primary">Ajouter un cadeau</a>
</div>

<c:if test="${empty cadeaux}">
    <div class="alert alert-info">
        Aucun cadeau n'est disponible dans le catalogue pour le moment.
    </div>
</c:if>

<c:if test="${not empty cadeaux}">
    <div class="row row-cols-1 row-cols-md-3 g-4">
        <c:forEach var="cadeau" items="${cadeaux}">
            <div class="col">
                <div class="card h-100">
          
                    <c:choose>
                        <c:when test="${cadeau.urlPhoto.contains('https://')}">
                            <img src="${cadeau.urlPhoto}" class="card-img-top" alt="${cadeau.titre}" style="height: 200px; object-fit: cover;">
                        </c:when>
                        <c:otherwise>
                            <img src="${pageContext.request.contextPath}${cadeau.urlPhoto}" class="card-img-top" alt="${cadeau.titre}" style="height: 200px; object-fit: cover;">
                        </c:otherwise>
                    </c:choose>
                    <div class="card-body">
                        <h5 class="card-title">${cadeau.titre}</h5>
                        <p class="card-text">
                            <strong>Prix:</strong> ${cadeau.prix} $CAD
                        </p>
                    </div>
                    <div class="card-footer">
                        <a href="${pageContext.request.contextPath}/cadeau/details?id=${cadeau.id}" class="btn btn-outline-primary btn-sm">Détails</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</c:if> 