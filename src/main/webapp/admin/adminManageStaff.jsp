<%--
  Created by IntelliJ IDEA.
  User: Rushd
  Date: 03/03/2026
  Time: 9:07 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.oceanview.reservation.model.SystemUser" %>

<%
    List<SystemUser> staffUsers = (List<SystemUser>) request.getAttribute("staffUsers");
    String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Staff</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tailwindcssOutput.css">
</head>

<body class="min-h-screen bg-slate-50 text-slate-900">
<div class="mx-auto max-w-5xl px-4 py-8">

    <div class="bg-white border border-slate-200 rounded-2xl shadow-sm p-6">
        <h1 class="text-xl font-bold">Manage Staff</h1>
        <p class="mt-1 text-slate-600">View and update staff system accounts.</p>

        <% if (error != null) { %>
        <div class="mt-4 rounded-lg border border-red-200 bg-red-50 p-3 text-red-700">
            <%= error %>
        </div>
        <% } %>

        <div class="mt-6 overflow-x-auto">
            <table class="w-full border-collapse">
                <thead>
                <tr class="text-left border-b border-slate-200">
                    <th class="py-2 pr-4">User ID</th>
                    <th class="py-2 pr-4">Username</th>
                    <th class="py-2 pr-4">Full Name</th>
                    <th class="py-2 pr-4">Active</th>
                    <th class="py-2 pr-4">Actions</th>
                </tr>
                </thead>

                <tbody>
                <% if (staffUsers != null) {
                    for (SystemUser u : staffUsers) { %>

                <tr class="border-b border-slate-100">
                    <td class="py-2 pr-4"><%= u.getUserId() %></td>
                    <td class="py-2 pr-4"><%= u.getUserName() %></td>
                    <td class="py-2 pr-4"><%= u.getFullName() %></td>
                    <td class="py-2 pr-4"><%= u.isActive() ? "Yes" : "No" %></td>
                    <td class="py-2 pr-4">
                        <a class="rounded-lg border border-slate-300 bg-white px-3 py-1 font-semibold hover:bg-slate-100 inline-block"
                           href="<%= request.getContextPath() %>/admin/home?action=adminEditUser&userId=<%= u.getUserId() %>">
                            Edit
                        </a>
                    </td>
                </tr>

                <% } } %>
                </tbody>
            </table>
        </div>

        <div class="mt-6">
            <a class="text-sm font-semibold text-slate-700 hover:text-slate-900"
               href="<%= request.getContextPath() %>/admin/home">
                Back to dashboard
            </a>
        </div>

    </div>
</div>
</body>
</html>
