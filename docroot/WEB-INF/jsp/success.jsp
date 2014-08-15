<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="Language-ext"/>
<fmt:message key="success.welcome"/> ${requestScope.user.firstName} <br />

<a href="${requestScope.homeRenderUrl}">home</a>

