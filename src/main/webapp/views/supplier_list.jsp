<%@ page
    import="com.katkam.entity.Supplier"
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

<h1>Suppliers</h1>

<p>
    <a href="supplier"><i class="glyphicon glyphicon-chevron-left"></i> Overview</a>
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
                <a href="supplier-edit?id=${row.id}">Edit</a>
            </td>
        </tr>
    </c:forEach>
</table>

<p>
    <%
        List<Supplier> lstSuppliers = (List<Supplier>) request.getAttribute("list");
    %>

    Item count is <% out.print(lstSuppliers == null ? 0 : lstSuppliers.size()); %>.
</p>

</div>
</body>
</html>