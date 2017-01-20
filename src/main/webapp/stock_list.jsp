<%@ page
    import="com.katkam.entity.Stock"
    import="java.util.List"
    %>
<!doctype html><html>
<head>
<%@ include file="tpl_head.jsp" %>
</head>
<body>
<div class="container">

<h1>Stock</h1>

<p>
    <a href="stock"><i class="glyphicon glyphicon-chevron-left"></i> Overview</a>
</p>

<table class="table table-striped table-bordered table-hover table-condensed">
    <tr>
        <th>ID</th>
        <th>Store</th>
        <th>Part</th>
        <th>Qty</th>
        <th>Actions</th>
    </tr>
<%
List<Stock> lst = (List<Stock>) request.getAttribute("list");
for (Stock iterModel : lst) {
%>
    <tr>
        <td><%= iterModel.getId() %></td>
        <td><%= iterModel.getStore().getName() %></td>
        <td><%= iterModel.getPart().getName() %></td>
        <td><%= iterModel.getQty() %></td>
        <td>
            <a href="stock-edit?id=<%= iterModel.getId() %>">Edit</a>
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