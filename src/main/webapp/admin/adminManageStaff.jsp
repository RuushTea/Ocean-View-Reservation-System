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
    String msg = request.getParameter("msg");
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
        <div class="flex items-center justify-between">
            <div>
                <h1 class="text-xl font-bold">Manage Staff</h1>
                <p class="mt-1 text-slate-600">View and update staff system accounts.</p>
            </div>
            <a class="rounded-lg bg-blue-600 text-white px-4 py-2 font-semibold hover:bg-blue-700 inline-block"
               href="<%= request.getContextPath() %>/admin/home?action=adminCreateStaff">
                Create Staff
            </a>
        </div>

        <% if (error != null) { %>
        <div class="mt-4 rounded-lg border border-red-200 bg-red-50 p-3 text-red-700">
            <%= error %>
        </div>
        <% } %>

        <% if (msg != null) { %>
        <div class="mt-4 rounded-lg border border-green-200 bg-green-50 p-3 text-green-700">
            <% if (msg.equals("created")) { %>
            Staff account created successfully!
            <% } else if (msg.equals("updated")) { %>
            Staff account updated successfully!
            <% } else if (msg.equals("toggled")) { %>
            Staff active status updated successfully!
            <% } else if (msg.equals("deleted")) { %>
            Staff account deleted successfully!
            <% } %>
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
                    <td class="py-2 pr-4"><%= u.getUserId() %>
                    </td>
                    <td class="py-2 pr-4"><%= u.getUserName() %>
                    </td>
                    <td class="py-2 pr-4"><%= u.getFullName() %>
                    </td>
                    <td class="py-2 pr-4"><%= u.isActive() ? "Yes" : "No" %>
                    </td>
                    <td class="py-2 pr-4">
                        <div class="flex items-center gap-2 flex-wrap">

                            <%--Edit--%>
                            <a class="inline-flex items-center justify-center rounded-lg border border-slate-300 bg-white px-3 py-1.5 text-sm font-semibold hover:bg-slate-100 transition"
                               href="<%= request.getContextPath() %>/admin/home?action=adminEditUser&userId=<%= u.getUserId() %>">
                                Edit
                            </a>

                            <%--Change with activate and deactivate--%>
                            <form method="post"
                                  action="<%= request.getContextPath() %>/admin/home"
                                  class="inline">
                                <input type="hidden" name="action" value="toggleActive">
                                <input type="hidden" name="userId" value="<%= u.getUserId() %>">

                                <button type="submit"
                                        class="inline-flex items-center justify-center rounded-lg px-3 py-1.5 text-sm font-semibold transition
                                <%= u.isActive()
                                ? "bg-amber-100 text-amber-800 border border-amber-300 hover:bg-amber-200"
                                : "bg-emerald-100 text-emerald-800 border border-emerald-300 hover:bg-emerald-200" %>">
                                    <%= u.isActive() ? "Deactivate" : "Activate" %>
                                </button>
                            </form>

                            <%--Delete--%>
                            <form method="post"
                                  action="<%= request.getContextPath() %>/admin/home"
                                  class="inline"
                                  onsubmit="return confirm('Are you sure you want to delete this staff member?');">

                                <input type="hidden" name="action" value="deleteStaff">
                                <input type="hidden" name="userId" value="<%= u.getUserId() %>">

                                <button type="submit"
                                        class="inline-flex items-center justify-center rounded-lg px-3 py-1.5 text-sm font-semibold
                                        bg-red-100 text-red-800 border border-red-300
                                        hover:bg-red-200 transition">
                                    Delete
                                </button>
                            </form>

                        </div>
                    </td>
                </tr>

                <% }
                } %>
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
