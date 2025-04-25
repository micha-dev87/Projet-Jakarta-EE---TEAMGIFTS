<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row justify-content-center">
    <div class="col-md-8 text-center">
        <h1>Bienvenue sur TeamGifts</h1>
        <p class="lead mt-4">Organisez des cadeaux communs facilement</p>
        
        <div class="mt-5">
            <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">Connexion</a>
            <a href="${pageContext.request.contextPath}/register" class="btn btn-outline-primary ms-3">Inscription</a>
        </div>
    </div>
</div> 