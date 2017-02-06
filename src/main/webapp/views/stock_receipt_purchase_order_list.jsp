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

<h1>Stock Receipt - Against Purchase Order</h1>

<p>
    <a href="stock-receipt"><i class="glyphicon glyphicon-chevron-left"></i> Overview</a>
</p>

<h2>Purchase Orders</h2>

<table class="table table-striped table-bordered table-hover table-condensed">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="row" items="${pos}">
        <tr>
            <td><c:out value="${row.id}" /></td>
            <td><c:out value="${row.name}" /></td>
            <td><a href="stock-receipt-purchase-order-select?id=${row.id}">Receive Material</a></td>
        </tr>
    </c:forEach>
</table>

<p>
Item count is <% out.print(((java.util.List<com.katkam.entity.PurchaseOrderHeader>)request.getAttribute("pos")).size()); %>.
</p>

</div>
</body>
</html>