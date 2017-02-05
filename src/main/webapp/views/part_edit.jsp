<%@ page
    import="com.katkam.entity.Part"
    import="com.katkam.entity.Uom"
    import="com.katkam.entity.Manufacturer"
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

<h1>Part</h1>

<p>
    <a href="part"><i class="glyphicon glyphicon-chevron-left"></i> Overview</a>
</p>

<%
Part m = (Part) request.getAttribute("m");
%>

<!-- Using enctype multipart form makes Spring MVC see nulls -->
<form method="post" action="part-save">
    <input type="hidden" name="id" value="<%= m==null?-1:m.getId() %>" />
    <div class="form-group">
        <label>Part</label>
        <input type="text" name="name" value="<%= m==null?"":m.getName() %>" autofocus />
    </div>
    <div class="form-group">
        <label>UoM</label>
        <select name="uom_id">
            <option></option>
            <% for (Uom iterUom : (List<Uom>) request.getAttribute("uoms")) { %>
            <option value="<%= iterUom.getId() %>" <%= m==null||m.getUom()==null?"":(m.getUom().getId()!=iterUom.getId()?"":"selected") %> ><%= iterUom.getName() %></option>
            <% } %>
        </select>
    </div>
    <div class="form-group">
        <label>Manufacturer</label>
        <select name="manufacturer_id">
            <option></option>
            <% for (Manufacturer iterManufacturer : (List<Manufacturer>) request.getAttribute("manufacturers")) { %>
            <option value="<%= iterManufacturer.getId() %>" <%= m==null||m.getManufacturer()==null?"":(m.getManufacturer().getId()!=iterManufacturer.getId()?"":"selected") %> ><%= iterManufacturer.getName() %></option>
            <% } %>
        </select>
    </div>
    <div class="form-group">
        <input type="submit" value="Save" class="btn btn-primary" />
    </div>
</form>

<%
if (m!=null) {
%>
<form method="post" action="part-delete">
    <input type="hidden" name="id" value="<%= m.getId() %>" />
    <input type="submit" value="Delete" class="btn btn-default" onclick="return confirm('Do you want to delete this record?')" />
</form>
<%
}
%>

</div>
</body>
</html>