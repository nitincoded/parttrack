<%@ page
    import="com.katkam.entity.RequisitionHeader"
    import="java.util.List"
    %>
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

<table class="table table-striped table-bordered table-hover table-condensed">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Actions</th>
    </tr>
<%
List<RequisitionHeader> lst = (List<RequisitionHeader>) request.getAttribute("list");
for (RequisitionHeader iterModel : lst) {
%>
    <tr>
        <td><%= iterModel.getId() %></td>
        <td><%= iterModel.getName() %></td>
        <td>
            <a href="requisition-edit?id=<%= iterModel.getId() %>">Edit</a>

            <form action="purchaseorder-from-requisition" method="post">
                <input type="hidden" name="id" value="<%= iterModel.getId() %>" />
                <input type="submit" value="Generate PO" />
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