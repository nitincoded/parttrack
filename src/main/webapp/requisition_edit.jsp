<%@ page
    import="com.katkam.entity.RequisitionHeader"
    import="com.katkam.entity.RequisitionLine"
    import="com.katkam.entity.Part"
    import="java.util.List"
    %>

<%--
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
--%>

<!doctype html><html>
<head>
<%@ include file="tpl_head.jsp" %>
</head>
<body>
<div class="container">

<h1>Requisition</h1>

<p>
    <a href="requisition"><i class="glyphicon glyphicon-chevron-left"></i> Overview</a>
</p>

<%
RequisitionHeader m = (RequisitionHeader) request.getAttribute("m");
%>

<!-- Using enctype multipart form makes Spring MVC see nulls -->
<form method="post" action="requisition-save">
    <input type="hidden" name="id" value="<%= m==null?-1:m.getId() %>" />
    <div class="form-group">
        <label>Requisition</label>
        <input type="text" name="name" value="<%= m==null?"":m.getName() %>" autofocus />
    </div>
    <div class="form-group">
        <input type="submit" value="Save" class="btn btn-primary" />
    </div>
</form>

<%
if (m!=null) {
%>
<form method="post" action="requisition-delete">
    <input type="hidden" name="id" value="<%= m.getId() %>" />
    <input type="submit" value="Delete" class="btn btn-default" onclick="return confirm('Do you want to delete this record?')" />
</form>

<h4>
    Requisition Lines
</h4>

<table class="table table-striped table-bordered table-hover table-condensed">
    <tr>
        <th>Part code</th>
        <th>Description</th>
        <th>Qty</th>
        <th>Actions</th>
    </tr>
    <%
    List<RequisitionLine> lines = (List<RequisitionLine>) request.getAttribute("lines");
    for (RequisitionLine iterLine : lines) {
    %>
    <tr>
        <td><%= iterLine.getPart().getId() %></td>
        <td><%= iterLine.getPart().getName() %></td>
        <td><%= iterLine.getQty() %></td>
        <td>
            <form method="post" action="requisitionline-delete">
                <input type="hidden" name="id" value="<%= iterLine.getId() %>" />
                <input type="submit" value="Delete" />
            </form>
        </td>
    </tr>
    <%
    }
    %>
</table>

<form method="post" action="requisitionline-save">
    <input type="hidden" name="header_id" value="<%= m==null?-1:m.getId() %>" />
    <input type="hidden" name="id" value="-1" /> <%-- No edit; only add. TODO: Add edit support --%>
    <div>
        <label>Part</label>
        <select name="part_id">
            <option></option>
            <%
            List<Part> parts = null;
            if (request!=null && request.getAttribute("parts") != null) {
            parts = (List<Part>) request.getAttribute("parts");
                for (Part iterPart : parts) {
            %>
                <option
                    value="<%= iterPart==null?"":iterPart.getId() %>"
                     >
                    <%= iterPart==null?"":iterPart.getName() %>
                </option>
            <%
                }
            }
            %>
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

<%
}
%>

</div>
</body>
</html>