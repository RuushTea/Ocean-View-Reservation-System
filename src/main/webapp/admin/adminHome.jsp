<%--
  Created by IntelliJ IDEA.
  User: Rushd
  Date: 21/02/2026
  Time: 7:40 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.oceanview.reservation.model.Admin" %>
<%
    Admin admin = (Admin) session.getAttribute("user");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tailwindcssOutput.css">
</head>

<body class="min-h-screen bg-slate-50 text-slate-900">
<div class="mx-auto max-w-6xl px-4 py-8">

    <%--Header--%>
    <header class="bg-white border border-slate-200 rounded-2xl shadow-sm p-6 flex items-start justify-between gap-4">
        <div>
            <h1 class="text-xl font-bold">Admin Dashboard</h1>
            <p class="mt-1 text-slate-600">
                Welcome,
                <span class="font-semibold">
                    <%= admin != null ? admin.getFullName() : "Admin" %>
                </span>
            </p>
        </div>

        <div class="flex items-center gap-2">
            <a href="<%= request.getContextPath() %>/logout"
               class="inline-flex items-center justify-center rounded-lg bg-slate-900 px-4 py-2 text-sm font-semibold text-white hover:bg-slate-800 shadow-sm">
                Logout
            </a>
        </div>
    </header>

    <%--Action buttons--%>
    <section class="mt-6 bg-white border border-slate-200 rounded-2xl shadow-sm p-6">
        <h2 class="text-lg font-bold">Admin Actions</h2>
        <p class="mt-1 text-sm text-slate-600">Choose an option.</p>

        <div class="mt-5 grid grid-cols-1 md:grid-cols-2 gap-4">

            <%--Create Reservation--%>
            <a href="<%= request.getContextPath() %>/reservation"
               class="block rounded-xl border border-slate-200 bg-white p-5 shadow-sm hover:bg-slate-50">
                <p class="text-sm font-semibold text-slate-900">Create Reservation</p>
                <p class="mt-1 text-sm text-slate-600">Create a new reservation for a guest.</p>
            </a>

            <%--Manage Staff--%>
            <a href="<%= request.getContextPath() %>/admin/home?action=adminManageStaff"
               class="block rounded-xl border border-slate-200 bg-white p-5 shadow-sm hover:bg-slate-50">
                <p class="text-sm font-semibold text-slate-900">Manage Staff</p>
                <p class="mt-1 text-sm text-slate-600">Create / update staff accounts.</p>
            </a>

            <%--Help Articles--%>
            <a href="<%= request.getContextPath() %>/help"
               class="block rounded-xl border border-slate-200 bg-white p-5 shadow-sm hover:bg-slate-50">
                <p class="text-sm font-semibold text-slate-900">Help Articles</p>
                <p class="mt-1 text-sm text-slate-600">Read help documentations.</p>
            </a>

            <%--View all reservations--%>
            <a href="<%= request.getContextPath() %>/admin/home?action=viewAllReservations"
               class="block rounded-xl border border-slate-200 bg-white p-5 shadow-sm hover:bg-slate-50">
                <p class="text-sm font-semibold text-slate-900">View All Reservations</p>
                <p class="mt-1 text-sm text-slate-600">Search and view all reservation records.</p>
            </a>

        </div>
    </section>

</div>
</body>
</html>