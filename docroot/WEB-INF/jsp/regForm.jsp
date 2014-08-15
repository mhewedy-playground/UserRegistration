<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setBundle basename="Language-ext" />

<form action="${requestScope.registerActionUrl}" method="POST">
	<table>
		<tr>
			<td colspan="2" style="color: red;"><c:out value="${requestScope.errorMsg}"/> </td>
		</tr>
		<tr>
			<td style="width: 40%"><fmt:message key="form.firstName" /> </td>
			<td><input type="text" name="firstNameTxt" value="${requestScope.user.firstName}" /></td>
		</tr>
		<tr>
			<td><fmt:message key="form.lastName" /></td>
			<td><input type="text" name="lastNameTxt" value="${requestScope.user.lastName}" /></td>
		</tr>
		<tr>
			<td><fmt:message key="form.email" /></td>
			<td><input type="text" name="emailTxt" value="${requestScope.defaultEmail}" /></td>
		</tr>
		<tr>
			<td>
				<input type="submit" value='<fmt:message key="form.submit" />' />
			</td>
			<td>
				<a href="${requestScope.homeRenderUrl}"><fmt:message key="form.reset" /></a>
			</td>
		</tr>
	</table>

</form>