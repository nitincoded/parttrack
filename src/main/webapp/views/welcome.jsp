<%@page %>
<!doctype html><html>
<head>
    <%@ include file="tpl_head.jsp" %>

    <style>
    a.not-done { color: red; }
    </style>
    <script>
    $( document ).ready(function() {
        $('.not-done').click(function(event) {
            alert('Under Construction');
            event.preventDefault();
            event.stopPropagation();
            return false;
        });
    });
    </script>
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
            <a href="supplier">Suppliers</a>
        </li>
        <li>
            <a href="customer">Customers</a>
        </li>
        <li>
            <a href="employee">Employees</a>
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
                    <a href="stock-issue-sale-order" class="not-done">Stock Issue - Against Sale Order</a>
                </li>
            </ul>
        </li>
        <li>
            <a href="stock-receipt" class="not-done">Stock Receipt / Return</a>
            <ul>
                <li>
                    <a href="stock-receipt-purchase-order-list">Stock Receipt / Return - Against Purchase Order</a>
                </li>
                <li>
                    <a href="#" class="not-done">Stock Receipt / Return - Direct</a>
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
            <a href="rfq" class="not-done">Request For Quotation</a>
        </li>
        <li>
            <a href="purchase-quotation" class="not-done">Purchase Quotation</a>
        </li>
        <li>
            <a href="purchaseorder">Purchase Order</a>
        </li>
        <li>
            <a href="purchase-invoice" class="not-done">Purchase Invoice</a>
        </li>
    </ul>
</div>

<div>
    <h2>Sales</h2>

    <ul>
        <li>
            <a href="sale-order" class="not-done">Sale Order</a>
        </li>
        <li>
            <a href="sale-quotation" class="not-done">Sale Quotation</a>
        </li>
        <li>
            <a href="sale-invoice" class="not-done">Sale Invoice</a>
        </li>
        <li>
            <a href="sale-route" class="not-done">Sale Route</a>
        </li>
    </ul>
</div>

<div>
    <h2>Production / Service</h2>

    <ul>
        <li>
            <a href="work-order" class="not-done">Work Order</a>
        </li>
        <li>
            <a href="production-order" class="not-done">Production Order</a>
        </li>
        <!--
            FMECA codes in Work Order
            Scheduling, Appointment
            Preventive Maintenance
            Production Schedule, Production Plan
        -->
        <li>
            <a href="work-plan" class="not-done">Work Plan</a>
        </li>
        <li>
            <a href="production-plan" class="not-done">Production Plan</a>
        </li>
        <li>
            <a href="work-schedule" class="not-done">Work Schedule</a>
        </li>
        <li>
            <a href="fixed-assets" class="not-done">Fixed Assets</a>
        </li>
    </ul>
</div>

<div>
    <h2>Call Center</h2>

    <ul>
        <li>
            <a href="contact" class="not-done">Contacts</a>
        </li>
        <li>
            <a href="problem-code" class="not-done">Problem Codes</a>
        </li>
        <li>
            <a href="call-record" class="not-done">Call Records</a>
        </li>
    </ul>
</div>


</div>
</body>
</html>