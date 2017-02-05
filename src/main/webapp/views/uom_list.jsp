<%@ page
    import="com.katkam.entity.Uom"
    import="java.util.List"
    %>
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

<table class="table table-striped table-bordered table-hover table-condensed">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Actions</th>
    </tr>
<%
List<Uom> lst = (List<Uom>) request.getAttribute("list");
for (Uom iterModel : lst) {
%>
    <tr>
        <td><%= iterModel.getId() %></td>
        <td><%= iterModel.getName() %></td>
        <td>
            <a href="uom-edit?id=<%= iterModel.getId() %>">Edit</a>
        </td>
    </tr>
<%
}
%>
</table>

<p>
Item count is <% out.print(lst.size()); %>.
</p>

</div>
</body>
</html>