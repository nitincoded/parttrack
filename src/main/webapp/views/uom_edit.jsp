<%@ page import="com.katkam.entity.Uom" %>
<!doctype html><html>
<head>
<%@ include file="tpl_head.jsp" %>
</head>
<body>
<div class="container">

<h1>UoM</h1>

<p>
    <a href="uom"><i class="glyphicon glyphicon-chevron-left"></i> Overview</a>
</p>

<%
Uom m = (Uom) request.getAttribute("m");
%>

<!-- Using enctype multipart form makes Spring MVC see nulls -->
<form method="post" action="uom-save">
    <input type="hidden" name="id" value="<%= m==null?-1:m.getId() %>" />
    <div class="form-group">
        <label>Manufacturer</label>
        <input type="text" name="name" value="<%= m==null?"":m.getName() %>" autofocus />
    </div>
    <div class="form-group">
        <input type="submit" value="Save" class="btn btn-primary" />
    </div>
</form>

<%
if (m!=null) {
%>
<form method="post" action="uom-delete">
    <input type="hidden" name="id" value="<%= m.getId() %>" />
    <input type="submit" value="Delete" class="btn btn-default" onclick="return confirm('Do you want to delete this record?')" />
</form>
<%
}
%>

</div>
</body>
</html>