<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>${titre} - TeamGifts</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <!-- Inclusion des styles personnalisés si présents -->
    <c:if test="${not empty styles}">
        <jsp:include page="/WEB-INF/includes/${styles}" />
    </c:if>
</head>
<body>

<!-- Inclusion de l'en-tête si l'utilisateur est connecté -->
<c:if test="${not empty utilisateur}">
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/evenements">TeamGifts</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link ${contenu.contains('evenements') ? 'active' : ''}" href="${pageContext.request.contextPath}/evenements">Mes événements</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link ${contenu.contains('invitations') ? 'active' : ''}       " href="${pageContext.request.contextPath}/invitations">Mes invitations</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link ${contenu.contains('cadeaux') ? 'active' : ''}       " href="${pageContext.request.contextPath}/cadeaux">Catalogue de cadeaux</a>
                    </li>
          
          
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                ${utilisateur.nomComplet}
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile">Mon profil</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Déconnexion</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</c:if>

<!-- Contenu principal -->
<div class="container mt-4">
    <!-- Inclusion du contenu spécifique à la page -->
    <jsp:include page="${contenu}" />
</div>

<script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/webjars/jquery/3.7.1/jquery.min.js"></script>
<!-- Inclusion des scripts personnalisés si présents -->
<c:if test="${not empty scripts}">
    <jsp:include page="/WEB-INF/includes/${scripts}" />
</c:if>
</body>
</html> 