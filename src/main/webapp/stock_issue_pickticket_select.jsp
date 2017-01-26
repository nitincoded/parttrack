<%@ page
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

<h1>Issue Stock - Select Items from Pick Ticket</h1>

<table class="table table-striped table-bordered table-hover table-condensed">
    <tr>
        <th>Part Code</th>
        <th>Description</th>
        <th>Requested Qty</th>
        <th>Issued Qty</th>
        <th>UOM</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="row" items="${picktickets}">
        <tr>
            <td><c:out value="${row.part.id}" /></td>
            <td><c:out value="${row.part.name}" /></td>
            <td><c:out value="${row.qty}" /></td>
            <td><c:out value="${row.issued_qty}" /></td>
            <td>TODO: UOM for Part</td>
            <td>
                <form method="post" action="stock_issue_by_pickticket">
                    <input type="hidden" name="id" value="" />
                    <input type="text" name="qty_delta" value="" />
                    <input type="submit" value="Issue/Return" />
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<form method="post" action="stock_issue_by_pickticket_all">
    <input type="hidden" name="header_id" value="" />
    <input type="submit" value="Issue All" />
</form>

</div>
</body>
</html>
