<%--
  Created by IntelliJ IDEA.
  User: Rushd
  Date: 03/03/2026
  Time: 9:06 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.oceanview.reservation.model.SystemUser" %>
<%
    SystemUser editUser = (SystemUser) request.getAttribute("editUser");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Staff</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tailwindcssOutput.css">
</head>
<body class="min-h-screen bg-slate-50 text-slate-900">

<div class="mx-auto max-w-3xl px-4 py-8">
    <div class="bg-white border border-slate-200 rounded-2xl shadow-sm p-6">
        <h1 class="text-xl font-bold">Edit Staff Account</h1>

        <% if (error != null) { %>
        <div class="mt-4 rounded-lg border border-red-200 bg-red-50 p-3 text-red-700">
            <%= error %>
        </div>
        <% } %>

        <form class="mt-6 space-y-4" method="post" action="<%= request.getContextPath() %>/admin/home">
            <input type="hidden" name="action" value="updateStaffCredentials">
            <input type="hidden" name="userId" value="<%= editUser.getUserId() %>">

            <div>
                <label class="block text-sm font-semibold mb-1">Username</label>
                <input class="w-full rounded-lg border border-slate-300 px-3 py-2"
                       type="text" name="username" required
                       value="<%= editUser.getUserName() %>">
            </div>

            <div>
                <label class="block text-sm font-semibold mb-1">Password</label>
                <input class="w-full rounded-lg border border-slate-300 px-3 py-2"
                       type="password" name="password"
                       placeholder="Leave blank to keep current password">
            </div>

            <div>
                <label class="block text-sm font-semibold mb-1">Full Name</label>
                <input class="w-full rounded-lg border border-slate-300 px-3 py-2"
                       type="text" name="fullName" required
                       value="<%= editUser.getFullName() %>">
            </div>

            <div class="flex items-center gap-3">
                <button class="rounded-lg bg-blue-600 text-white px-4 py-2 font-semibold hover:bg-blue-700">
                    Save Changes
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