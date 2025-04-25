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
    <jsp:include page="/WEB-INF/includes/header.jsp" />
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