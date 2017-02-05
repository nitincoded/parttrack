<%@ page import="com.katkam.entity.Customer" %>
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

<h1>Customer</h1>

<p>
    <a href="customer"><i class="glyphicon glyphicon-chevron-left"></i> Overview</a>
</p>

<!-- Using enctype multipart form makes Spring MVC see nulls -->
<form method="post" action="customer-save">
    <input type="hidden" name="id" value="${m==null ? -1 : m.id }" />
    <div class="form-group">
        <label>Customer</label>
        <input type="text" name="name" value="${m==null ? "" : m.name }" autofocus />
    </div>
    <div class="form-group">
        <input type="submit" value="Save" class="btn btn-primary" />
    </div>
</form>

<c:if test="${m!=null}">
    <form method="post" action="customer-delete">
        <input type="hidden" name="id" value="${m.id}" />
        <input type="submit" value="Delete" class="btn btn-default" onclick="return confirm('Do you want to delete this record?')" />
    </form>
</c:if>

</div>
</body>
</html>