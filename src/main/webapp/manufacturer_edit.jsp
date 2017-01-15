<%@ page import="com.katkam.entity.Manufacturer" %>
<!doctype html><html>
<head>
<%@ include file="tpl_head.jsp" %>
</head>
<body>

<h1>Manufacturer</h1>

<p>
    <a href="manufacturer">Overview</a>
</p>

<%
Manufacturer m = (Manufacturer) request.getAttribute("m");
%>

<!-- Using enctype multipart form makes Spring MVC see nulls -->
<form method="post" action="manufacturer-save">
    <input type="hidden" name="id" value="<%= m==null?-1:m.getId() %>" />
    <div>
        <label>Manufacturer</label>
        <input type="text" name="name" value="<%= m==null?"":m.getName() %>" autofocus />
    </div>
    <div>
        <input type="submit" value="Save" />
    </div>
</form>

<%
if (m!=null) {
%>
<form method="post" action="manufacturer-delete">
    <input type="hidden" name="id" value="<%= m.getId() %>" />
    <input type="submit" value="Delete" onclick="return confirm('Do you want to delete this record?')" />
</form>
<%
}
%>

</body>
</html>