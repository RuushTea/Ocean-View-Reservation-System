<%--
  Created by IntelliJ IDEA.
  User: Rushd
  Date: 21/02/2026
  Time: 7:40 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.oceanview.reservation.model.Staff" %>
<%
    Staff staff = (Staff) session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Staff Dashboard</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tailwindcssOutput.css">
</head>
<body class="min-h-screen bg-slate-50">
<div class="mx-auto max-w-4xl p-6">
    <div class="bg-white border border-slate-200 rounded-xl p-6 shadow-sm">
        <h1 class="text-2xl font-bold">Staff Dashboard</h1>
        <p class="text-slate-600 mt-1">Manage reservations, billing, and help.</p>

        <div class="mt-6 grid grid-cols-1 gap-3">
            <a class="rounded-lg bg-blue-600 text-white px-4 py-3 font-semibold text-center hover:bg-blue-700"
               href="<%= request.getContextPath() %>/reservation">
                Create Reservation for Guest
            </a>

            <a class="rounded-lg border border-slate-300 bg-white px-4 py-3 font-semibold text-center hover:bg-slate-100"
               href="<%= request.getContextPath() %>/staff?action=searchReservation">
                Search Reservation
            </a>

            <a class="rounded-lg border border-slate-300 bg-white px-4 py-3 font-semibold text-center hover:bg-slate-100"
               href="<%= request.getContextPath() %>/staff?action=help">
                Help
            </a>

            <form method="post" action="<%= request.getContextPath() %>/staff?action=logout">
                <button class="w-full rounded-lg bg-slate-800 text-white px-4 py-3 font-semibold hover:bg-slate-900">
                    Logout
                </button>
            </form>
        </div>
    </div>
</div>
</body>
</html>