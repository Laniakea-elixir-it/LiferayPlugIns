<%@ include file="init.jsp" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>

    <c:when test="${renderRequest.getAttribute('isIamUser')}">

        Hello <c:out value="${renderRequest.getAttribute('iamSubject')}"/>

    </c:when>

    <c:otherwise>

        You must be logged in using IAM.

    </c:otherwise>

</c:choose>
