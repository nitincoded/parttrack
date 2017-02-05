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

<h1>Stock - Direct Issue</h1>

<p>
    <a href="stock-issue"><i class="glyphicon glyphicon-chevron-left"></i> Overview</a>
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
            <form method="post" action="stock-issue-direct-save">
                <input type="hidden" name="id" value="<%= iterModel.getId() %>" />
                <label>Quantity</label>
                <input type="text" name="qty_delta" />
                <input type="submit" value="Issue" />
            </form>
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