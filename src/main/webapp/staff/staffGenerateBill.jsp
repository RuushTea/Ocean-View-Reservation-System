<%--
  Created by IntelliJ IDEA.
  User: Rushd
  Date: 02/03/2026
  Time: 9:46 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.oceanview.reservation.model.Bill" %>

<%
    Bill bill = (Bill) request.getAttribute("bill");
    if (bill == null) {
        response.sendRedirect(request.getContextPath() + "/staff/home");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Bill</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tailwindcssOutput.css">
</head>
<body class="min-h-screen bg-slate-50 text-slate-900">
<div class="mx-auto max-w-3xl px-4 py-8">

    <div class="bg-white border border-slate-200 rounded-2xl shadow-sm p-6">
        <h1 class="text-2xl font-bold">Bill Summary</h1>

        <div class="mt-6 space-y-2">
            <p><strong>Reservation No:</strong> <%= bill.getReservationNo() %></p>
            <p><strong>Total Nights:</strong> <%= bill.getNights() %></p>
            <p><strong>Rate Per Night:</strong> <%= bill.getRatePerNight() %></p>
            <p class="text-lg font-bold mt-4">
                Total Amount: Rs. <%= bill.getTotalAmount() %>
            </p>
        </div>

        <div class="mt-6 flex gap-3">
            <button onclick="window.print()"
                    class="rounded-lg bg-emerald-600 text-white px-4 py-2 font-semibold hover:bg-emerald-700">
                Print
            </button>

            <a href="<%= request.getContextPath() %>/staff/home"
               class="rounded-lg border border-slate-300 bg-white px-4 py-2 font-semibold hover:bg-slate-100">
                Back to Dashboard
            </a>
        </div>

    </div>

</div>
</body>
</html>