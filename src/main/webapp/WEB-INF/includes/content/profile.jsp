<%@ page contentType="text/html; charset=UTF-8"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row justify-content-center">
    <div class="col-md-8">
        <div class="card">
            <div class="card-header bg-primary text-white">
                <h3>Mon Profil</h3>
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
                
                <form action="${pageContext.request.contextPath}/profile" method="post">
                    <div class="mb-3">
                        <label for="nomComplet" class="form-label">Nom complet</label>
                        <input type="text" class="form-control" id="nomComplet" name="nomComplet" value="${utilisateur.nomComplet}" required>
                    </div>
                    <div class="mb-3">
                        <label for="courriel" class="form-label">Courriel</label>
                        <input type="email" class="form-control" id="courriel" name="courriel" value="${utilisateur.courriel}" required>
                    </div>
                    <div class="mb-3">
                        <label for="motDePasse" class="form-label">Nouveau mot de passe (laisser vide pour conserver l'actuel)</label>
                        <input type="password" class="form-control" id="motDePasse" name="motDePasse">
                    </div>
                    <button type="submit" class="btn btn-primary">Mettre à jour</button>
                </form>
            </div>
        </div>
    </div>
</div> 