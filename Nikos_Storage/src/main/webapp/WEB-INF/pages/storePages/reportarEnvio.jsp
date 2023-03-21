<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="../commonPages/commonHead.jsp"/>
        <title>Reportar producto</title>
    </head>
    <body>
        <jsp:include page="../storePages/storeNavBar.jsp"/>
        <c:if test="${isDev == false}">
            <jsp:include page="../storePages/commonReportIncidencia.jsp"/>
        </c:if>
        <c:if test="${isDev != false}">
            <jsp:include page="../storePages/commonReportDevolucion.jsp"/>
        </c:if>


    </body>
</html>
