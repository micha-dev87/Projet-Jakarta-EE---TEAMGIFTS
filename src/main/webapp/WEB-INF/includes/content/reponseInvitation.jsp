<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row justify-content-center">
    <div class="col-md-6">
        <div class="card">
            <div class="card-header bg-primary text-white">
                <h3>Réponse à l'invitation</h3>
            </div>
            <div class="card-body text-center">
                <c:if test="${not empty message}">
                    <div class="alert alert-info mb-4">
                        ${message}
                    </div>
                </c:if>
                
                <div class="mb-4">
                    <i class="bi bi-check-circle-fill text-success" style="font-size: 4rem;"></i>
                </div>
                
                <p class="lead">Votre réponse a été enregistrée.</p>
                
                <div class="mt-4">
                    <a href="${pageContext.request.contextPath}/invitations" class="btn btn-primary">Voir mes invitations</a>
                </div>
            </div>
        </div>
    </div>
</div> 