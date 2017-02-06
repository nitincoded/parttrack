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

<h1>Stock Receipt - Select Items from Purchase Order</h1>

<p>
    <a href="stock-receipt-purchase-order-list"><i class="glyphicon glyphicon-chevron-left"></i> Back</a>
</p>

<table class="table table-striped table-bordered table-hover table-condensed">
    <tr>
        <th>Part Code</th>
        <th>Description</th>
        <th>Ordered Qty</th>
        <th>Received Qty</th>
        <th>UOM</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="row" items="${lines}">
        <tr>
            <td><c:out value="${row.part.id}" /></td>
            <td><c:out value="${row.part.name}" /></td>
            <td><c:out value="${row.qty}" /></td>
            <td><c:out value="${row.received_qty}" /></td>
            <td><c:out value="${row.part.uom.name}" /></td>
            <td>
                <form method="post" action="stock_receipt_by_purchase_order">
                    <input type="hidden" name="id" value="${row.id}" />
                    <input type="text" name="qty_delta" value="0" />
                    <input type="submit" value="Issue/Return" />
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<form method="post" action="stock_receipt_by_purchase_order_all">
    <input type="hidden" name="header_id" value="" />
    <input type="submit" value="Issue All" onclick="alert('Under Construction'); return false;" />
</form>

</div>
</body>
</html>
