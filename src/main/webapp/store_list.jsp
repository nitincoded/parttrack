<%@ page
    import="com.katkam.entity.Store"
    import="java.util.List"
    %>
<!doctype html><html>
<head>
<%@ include file="tpl_head.jsp" %>
</head>
<body>
<div class="container">

<h1>Store</h1>

<p>
    <a href="store"><i class="glyphicon glyphicon-chevron-left"></i> Overview</a>
</p>

<table class="table table-striped table-bordered table-hover table-condensed">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Actions</th>
    </tr>
<%
List<Store> lst = (List<Store>) request.getAttribute("list");
for (Store iterModel : lst) {
%>
    <tr>
        <td><%= iterModel.getId() %></td>
        <td><%= iterModel.getName() %></td>
        <td>
            <a href="store-edit?id=<%= iterModel.getId() %>">Edit</a>
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