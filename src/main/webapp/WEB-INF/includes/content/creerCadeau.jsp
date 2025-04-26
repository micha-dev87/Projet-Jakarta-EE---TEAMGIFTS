<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row justify-content-center">
    <div class="col-md-8">
        <div class="card">
            <div class="card-header bg-primary text-white">
                <h3>Ajouter un Cadeau</h3>
            </div>
            <div class="card-body">
                <c:if test="${not empty erreur}">
                    <div class="alert alert-danger" role="alert">
                        ${erreur}
                    </div>
                </c:if>
                
                <form action="${pageContext.request.contextPath}/cadeau/creer" method="post" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label for="titre" class="form-label">Titre</label>
                        <input type="text" class="form-control" id="titre" name="titre" required>
                    </div>
                    <div class="mb-3">
                        <label for="prix" class="form-label">Prix ($CAD)</label>
                        <input type="number" step="0.01" min="0" class="form-control" id="prix" name="prix" required>
                    </div>
                    <div class="mb-3">
                        <label for="photo" class="form-label">Photo du cadeau</label>
                        <input type="file" class="form-control" id="photo" name="photo" accept="image/*" required>
                        <div class="form-text">Sélectionnez une image pour le cadeau.</div>
                    </div>
                    
                    <div class="d-flex justify-content-between">
                        <a href="${pageContext.request.contextPath}/cadeaux" class="btn btn-secondary">Annuler</a>
                        <button type="submit" class="btn btn-primary">Ajouter</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div> 