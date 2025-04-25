<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row justify-content-center">
    <div class="col-md-6">
        <div class="card">
            <div class="card-header bg-primary text-white">
                <h3 class="text-center">Connexion</h3>
            </div>
            <div class="card-body">
                <c:if test="${not empty erreur}">
                    <div class="alert alert-danger" role="alert">
                        ${erreur}
                    </div>
                </c:if>
                
                <form action="${pageContext.request.contextPath}/login" method="post">
                    <div class="mb-3">
                        <label for="courriel" class="form-label">Courriel</label>
                        <input type="email" class="form-control" id="courriel" name="courriel" required>
                    </div>
                    <div class="mb-3">
                        <label for="motDePasse" class="form-label">Mot de passe</label>
                        <input type="password" class="form-control" id="motDePasse" name="motDePasse" required>
                    </div>
                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-primary">Se connecter</button>
                    </div>
                </form>
                
                <div class="mt-3 text-center">
                    <p>Pas encore inscrit ? <a href="${pageContext.request.contextPath}/register">Créer un compte</a></p>
                </div>
            </div>
        </div>
    </div>
</div> 