<%@page %>
<!doctype html><html>
<head>
<%@ include file="tpl_head.jsp" %>
</head>
<body>
<div class="container">

<h1>Part Track</h1>



<h2>Master Data</h2>

<ul>
    <li>
        <a href="uom">UoM</a>
    </li>
    <li>
        <a href="manufacturer">Manufacturers</a>
    </li>
    <li>
        <a href="part">Parts</a>
    </li>
    <li>
        <a href="store">Store</a>
    </li>
</ul>



<h2>Store / Procurement</h2>

<ul>
    <li>
        <a href="stock">Stock</a>
    </li>
    <li>
        <a href="pickticket">Pick ticket</a>
    </li>
    <li>
        Stock Issue
        <ul>
            <li>
                <a href="stock-issue-pickticket-list">Stock Issue - Against Pick Ticket</a>
            </li>
        </ul>
    </li>
    <li>
        <a href="requisition">Requisition</a>
    </li>
</ul>

</div>
</body>
</html>