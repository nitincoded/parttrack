<%@ page
    import="com.katkam.entity.PurchaseOrderHeader"
    import="com.katkam.entity.PurchaseOrderLine"
    import="com.katkam.entity.Part"
    import="com.katkam.entity.Store"
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

<h1>Purchase Orders</h1>

<p>
    <a href="purchaseorder"><i class="glyphicon glyphicon-chevron-left"></i> Overview</a>
</p>

<!-- Using enctype multipart form makes Spring MVC see nulls -->
<form method="post" action="purchaseorder-save">
    <input type="hidden" name="id" value="${m!=null?m.id:-1}" />
    <div class="form-group">
        <label>PO Description</label>
        <input type="text" name="name" value="${m!=null?m.name:""}" autofocus />
    </div>
    <div class="form-group">
        <label>Store</label>
        <select name="store_id">
            <option></option>
            <c:forEach var="row" items="${stores}">
                <option value="${row.id}"
                    ${m!=null && m.store!=null && m.store.id==row.id ? "selected" : "" }
                    >
                    <c:out value="${row.name}" />
                </option>
            </c:forEach>
        </select>
    </div>
    <div class="form-group">
        <input type="submit" value="Save" class="btn btn-primary" />
    </div>
</form>

<c:if test="${m!=null}">
    <form method="post" action="purchaseorder-delete">
        <input type="hidden" name="id" value="${m.id}" />
        <input type="submit" value="Delete" class="btn btn-default" onclick="return confirm('Do you want to delete this record?')" />
    </form>

    <h4>
        Purchase Order Lines
    </h4>

    <table class="table table-striped table-bordered table-hover table-condensed">
        <tr>
            <th>ID</th>
            <th>Description</th>
            <th>Qty</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="row" items="${lines}">
            <tr>
                <td>
                    <c:out value="${row.id}" />
                </td>
                <td>
                    <c:out value="${row.part.name}" />
                </td>
                <td>
                    <c:out value="${row.qty}" />
                </td>
                <td>
                    <form method="post" action="purchaseorderline-delete">
                        <input type="hidden" name="id" value="${row.id}" />
                        <input type="submit" value="Delete" />
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <form method="post" action="purchaseorderline-save">
        <input type="hidden" name="header_id" value="${m==null?-1:m.id}" />
        <input type="hidden" name="id" value="-1" /> <%-- No edit; only add. TODO: Add edit support --%>
        <div>
            <label>Part</label>
            <select name="part_id">
                <option></option>
                <c:forEach var="row" items="${parts}">
                    <option value="${row.id}">
                        <c:out value="${row.name}" />
                    </option>
                </c:forEach>
            </select>
        </div>
        <div>
            <label>Qty</label>
            <input type="text" name="qty" />
        </div>
        <div>
            <input type="submit" value="Save" />
        </div>
    </form>
</c:if>

</div>
</body>
</html>