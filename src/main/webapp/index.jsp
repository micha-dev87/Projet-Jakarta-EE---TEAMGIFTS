<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    request.setAttribute("titre", "Accueil");
    request.setAttribute("contenu", "/WEB-INF/includes/content/accueil.jsp");
    request.getRequestDispatcher("/WEB-INF/templates/template.jsp").forward(request, response);
%>