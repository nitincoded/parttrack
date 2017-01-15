<%@ page
    import="com.katkam.entity.Manufacturer"
    import="java.util.List"
    %>
<!doctype html><html>
<head>
<%@ include file="tpl_head.jsp" %>
</head>
<body>

<h1>Manufacturers</h1>

<p>
    <a href="manufacturer">Overview</a>
</p>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Actions</th>
    </tr>
<%
List<Manufacturer> lst = (List<Manufacturer>) request.getAttribute("list");
for (Manufacturer iterModel : lst) {
%>
    <tr>
        <td><%= iterModel.getId() %></td>
        <td><%= iterModel.getName() %></td>
        <td>
            <a href="manufacturer-edit?id=<%= iterModel.getId() %>">Edit</a>
        </td>
    </tr>
<%
}
%>
</table>

<p>
Item count is <% out.print(lst.size()); %>.
</p>

</body>
</html>