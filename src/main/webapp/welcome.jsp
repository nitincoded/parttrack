<%@page %>
<!doctype html><html>
<head>
<%@ include file="tpl_head.jsp" %>
</head>
<body>
<div class="container">

<h1>Part Track</h1>


<div>
    <h2>Master Data</h2>

    <ul>
        <li>
            <a href="uom">Units of Measure</a>
        </li>
        <li>
            <a href="manufacturer">Manufacturers</a>
        </li>
        <li>
            <a href="part">Parts</a>
        </li>
        <li>
            <a href="store">Stores</a>
        </li>
        <li>
            <a href="supplier" onclick="alert('Under Construction'); return false;">Suppliers</a>
        </li>
        <li>
            <a href="customer" onclick="alert('Under Construction'); return false;">Customers</a>
        </li>
        <li>
            <a href="employee" onclick="alert('Under Construction'); return false;">Employees</a>
        </li>
    </ul>
</div>

<div>
    <h2>Store</h2>

    <ul>
        <li>
            <a href="stock">Stock</a>
        </li>
        <li>
            <a href="pickticket">Pick ticket</a>
        </li>
        <li>
            <a href="stock-issue">Stock Issue</a>
            <ul>
                <li>
                    <a href="stock-issue-pickticket-list">Stock Issue - Against Pick Ticket</a>
                </li>
                <li>
                    <a href="stock-issue-direct">Stock Issue - Direct</a>
                </li>
                <li>
                    <a href="stock-issue-sale-order">Stock Issue - Against Sale Order</a>
                </li>
            </ul>
        </li>
        <li>
            <a href="stock-receipt">Stock Receipt / Return</a>
            <ul>
                <li>
                    <a href="#" onclick="alert('Under Construction'); return false;">Stock Receipt / Return - Against Purchase Order</a>
                </li>
                <li>
                    <a href="#" onclick="alert('Under Construction'); return false;">Stock Receipt / Return - Direct</a>
                </li>
            </ul>
        </li>
    </ul>
</div>

<div>
    <h2>Procurement</h2>

    <ul>
        <li>
            <a href="requisition">Requisition</a>
        </li>
        <li>
            <a href="rfq" onclick="alert('Under Construction'); return false;">Request For Quotation</a>
        </li>
        <li>
            <a href="purchase-quotation" onclick="alert('Under Construction'); return false;">Purchase Quotation</a>
        </li>
        <li>
            <a href="purchase-order">Purchase Order</a>
        </li>
        <li>
            <a href="purchase-invoice">Purchase Invoice</a>
        </li>
    </ul>
</div>

<div style="display: none">
    <h2>Sales</h2>

    <ul>
        <li>
            <a href="sale-order" onclick="alert('Under Construction'); return false;">Sale Order</a>
        </li>
        <li>
            <a href="sale-quotation" onclick="alert('Under Construction'); return false;">Sale Quotation</a>
        </li>
        <li>
            <a href="sale-invoice" onclick="alert('Under Construction'); return false;">Sale Invoice</a>
        </li>
        <li>
            <a href="sale-route" onclick="alert('Under Construction'); return false;">Sale Route</a>
        </li>
    </ul>
</div>

<div style="display: none">
    <h2>Production / Service</h2>

    <ul>
        <li>
            <a href="work-order" onclick="alert('Under Construction'); return false;">Work Order</a>
        </li>
        <!--
            FMECA codes in Work Order
            Scheduling, Appointment
        -->
        <li>
            <a href="work-plan" onclick="alert('Under Construction'); return false;">Work Plan</a>
        </li>
        <li>
            <a href="work-schedule" onclick="alert('Under Construction'); return false;">Work Schedule</a>
        </li>
        <li>
            <a href="fixed-assets" onclick="alert('Under Construction'); return false;">Fixed Assets</a>
        </li>
    </ul>
</div>

<div style="display: none">
    <h2>Call Center</h2>

    <ul>
        <li>
            <a href="contact" onclick="alert('Under Construction'); return false;">Contacts</a>
        </li>
        <li>
            <a href="problem-code" onclick="alert('Under Construction'); return false;">Problem Codes</a>
        </li>
        <li>
            <a href="call-record" onclick="alert('Under Construction'); return false;">Call Records</a>
        </li>
    </ul>
</div>


</div>
</body>
</html>