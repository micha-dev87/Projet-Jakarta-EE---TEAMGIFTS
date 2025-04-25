<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row justify-content-center">
    <div class="col-md-8">
        <div class="card">
            <div class="card-header bg-primary text-white">
                <h3>Créer un Événement</h3>
            </div>
            <div class="card-body">
                <c:if test="${not empty erreur}">
                    <div class="alert alert-danger" role="alert">
                        ${erreur}
                    </div>
                </c:if>
                
                <form action="${pageContext.request.contextPath}/evenement/creer" method="post">
                    <div class="mb-3">
                        <label for="titre" class="form-label">Titre de l'événement</label>
                        <input type="text" class="form-control" id="titre" name="titre" required>
                    </div>
                    <div class="mb-3">
                        <label for="dateEvenement" class="form-label">Date de l'événement</label>
                        <input type="date" class="form-control" id="dateEvenement" name="dateEvenement" required>
                    </div>
                    <div class="mb-3">
                        <label for="cadeauId" class="form-label">Cadeau</label>
                        <select class="form-select" id="cadeauId" name="cadeauId" required>
                            <option value="">Sélectionnez un cadeau</option>
                            <c:forEach var="cadeau" items="${cadeaux}">
                                <option value="${cadeau.id}">${cadeau.titre} - ${cadeau.prix} €</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="d-flex justify-content-between">
                        <a href="${pageContext.request.contextPath}/evenements" class="btn btn-secondary">Annuler</a>
                        <button type="submit" class="btn btn-primary">Créer</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div> 