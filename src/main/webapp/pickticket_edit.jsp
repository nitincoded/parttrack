<%@ page
    import="com.katkam.entity.PickticketHeader"
    import="com.katkam.entity.PickticketLine"
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

<h1>Pick Ticket</h1>

<p>
    <a href="pickticket"><i class="glyphicon glyphicon-chevron-left"></i> Overview</a>
</p>

<%
PickticketHeader m = (PickticketHeader) request.getAttribute("m");
%>

<!-- Using enctype multipart form makes Spring MVC see nulls -->
<form method="post" action="pickticket-save">
    <input type="hidden" name="id" value="<%= m==null?-1:m.getId() %>" />
    <div class="form-group">
        <label>Pick Ticket</label>
        <input type="text" name="name" value="<%= m==null?"":m.getName() %>" autofocus />
    </div>
    <div class="form-group">
        <label>Store</label>
        <select name="store_id">
            <c:forEach var="row" items="${stores}">
                <c:if test="${m != null && m.store_id == row.id}">
                    <option value="${row.id}" selected>
                        <c:out value="${row.name}" />
                    </option>
                </c:if>
                <c:if test="${m == null || m.store_id != row.id}">
                    <option value="${row.id}">
                        <c:out value="${row.name}" />
                    </option>
                </c:if>
            </c:forEach>
        </select>
    </div>
    <div class="form-group">
        <input type="submit" value="Save" class="btn btn-primary" />
    </div>
</form>

<%
if (m!=null) {
%>
<form method="post" action="pickticket-delete">
    <input type="hidden" name="id" value="<%= m.getId() %>" />
    <input type="submit" value="Delete" class="btn btn-default" onclick="return confirm('Do you want to delete this record?')" />
</form>

<h4>
    Pick Ticket Lines
</h4>

<table class="table table-striped table-bordered table-hover table-condensed">
    <tr>
        <th>Part code</th>
        <th>Description</th>
        <th>Qty</th>
        <th>Actions</th>
    </tr>
    <%
    List<PickticketLine> lines = (List<PickticketLine>) request.getAttribute("lines");
    for (PickticketLine iterLine : lines) {
    %>
    <tr>
        <td><%= iterLine.getPart().getId() %></td>
        <td><%= iterLine.getPart().getName() %></td>
        <td><%= iterLine.getQty() %></td>
        <td>
            <form method="post" action="pickticketline-delete">
                <input type="hidden" name="id" value="<%= iterLine.getId() %>" />
                <input type="submit" value="Delete" />
            </form>
        </td>
    </tr>
    <%
    }
    %>
</table>

<form method="post" action="pickticketline-save">
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