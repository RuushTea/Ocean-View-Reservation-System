<%--
  Created by IntelliJ IDEA.
  User: Rushd
  Date: 03/03/2026
  Time: 9:30 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Create Staff</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tailwindcssOutput.css">
</head>
<body class="min-h-screen bg-slate-50 text-slate-900">

<div class="mx-auto max-w-3xl px-4 py-8">
    <div class="bg-white border border-slate-200 rounded-2xl shadow-sm p-6">
        <h1 class="text-xl font-bold">Create New Staff Account</h1>
        <p class="mt-1 text-slate-600">Create a new staff system account.</p>

        <% if (error != null) { %>
        <div class="mt-4 rounded-lg border border-red-200 bg-red-50 p-3 text-red-700">
            <%= error %>
        </div>
        <% } %>

        <form class="mt-6 space-y-4" method="post" action="<%= request.getContextPath() %>/admin/home">
            <input type="hidden" name="action" value="createStaff">

            <div>
                <label class="block text-sm font-semibold mb-1">Username</label>
                <input class="w-full rounded-lg border border-slate-300 px-3 py-2"
                       type="text" name="username" required
                       placeholder="Enter username">
            </div>

            <div>
                <label class="block text-sm font-semibold mb-1">Password</label>
                <input class="w-full rounded-lg border border-slate-300 px-3 py-2"
                       type="password" name="password" required
                       placeholder="Enter password">
            </div>

            <div>
                <label class="block text-sm font-semibold mb-1">Full Name</label>
                <input class="w-full rounded-lg border border-slate-300 px-3 py-2"
                       type="text" name="fullName" required
                       placeholder="Enter full name">
            </div>

            <div class="flex items-center gap-3">
                <button class="rounded-lg bg-blue-600 text-white px-4 py-2 font-semibold hover:bg-blue-700">
                    Create Staff
                </button>

                <a class="text-sm font-semibold text-slate-700 hover:text-slate-900"
                   href="<%= request.getContextPath() %>/admin/home?action=adminManageStaff">
                    Cancel
                </a>
            </div>
        </form>
    </div>
</div>

</body>
</html>
