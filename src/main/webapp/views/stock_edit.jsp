<%@ page
    import="java.util.List"
    import="com.katkam.entity.Stock"
    import="com.katkam.entity.Store"
    import="com.katkam.entity.Part"
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

<%
Stock m = (Stock) request.getAttribute("m");
%>

<!-- Using enctype multipart form makes Spring MVC see nulls -->
<form method="post" action="stock-save">
    <input type="hidden" name="id" value="<%= m==null?-1:m.getId() %>" />
    <div class="form-group">
        <label>Store</label>
        <select name="store_id">
            <option></option>
            <%
            List<Store> stores = null;
            if (request!=null && request.getAttribute("stores") != null) {
            stores = (List<Store>) request.getAttribute("stores");
                for (Store iterStore : stores) {
            %>
                <option
                    value="<%= iterStore==null?"":iterStore.getId() %>"
                     <%=
                     (m==null||m.getStore()==null||iterStore==null)
                     ? ""
                     : (
                        m.getStore().getId()!=iterStore.getId()
                        ? ""
                        : "selected"
                        )
                     %>
                     >
                    <%= iterStore==null?"":iterStore.getName() %>
                </option>
            <%
                }
            }
            %>
        </select>
    </div>
    <div class="form-group">
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
                     <%=
                     (m==null||m.getPart()==null||iterPart==null)
                     ? ""
                     : (
                        m.getPart().getId()!=iterPart.getId()
                        ? ""
                        : "selected"
                        )
                     %>
                     >
                    <%= iterPart==null?"":iterPart.getName() %>
                </option>
            <%
                }
            }
            %>
        </select>
    </div>
    <div class="form-group">
        <label>Qty</label>
        <input type="text" name="qty" value="<%= m==null?"":m.getQty() %>" autofocus />
    </div>
    <div class="form-group">
        <input type="submit" value="Save" class="btn btn-primary" />
    </div>
</form>

<%
if (m!=null && m.getQty()==0) { //will the double match zero?
%>
<form method="post" action="stock-delete">
    <input type="hidden" name="id" value="<%= m.getId() %>" />
    <input type="submit" value="Delete" class="btn btn-default" onclick="return confirm('Do you want to delete this record?')" />
</form>
<%
}
%>

</div>
</body>
</html>