<%@ page
    import="com.katkam.entity.Employee"
    import="java.util.List"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<!doctype html><html>
<head>
<%@ include file="tpl_head.jsp" %>
</head>
<body>
<div class="container">

<h1>Employers</h1>

<p>
    <a href="employee"><i class="glyphicon glyphicon-chevron-left"></i> Overview</a>
</p>

<table class="table table-striped table-bordered table-hover table-condensed">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="row" items="${list}">
        <tr>
            <td><c:out value="${row.id}" /></td>
            <td><c:out value="${row.name}" /></td>
            <td>
                <a href="employee-edit?id=${row.id}">Edit</a>
            </td>
        </tr>
    </c:forEach>
</table>

<p>
    <%
        List<Employee> lstEmployees = (List<Employee>) request.getAttribute("list");
    %>

    Item count is <% out.print(lstEmployees == null ? 0 : lstEmployees.size()); %>.
</p>

</div>
</body>
</html>