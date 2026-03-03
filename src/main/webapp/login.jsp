<%--
  Created by IntelliJ IDEA.
  User: Rushd
  Date: 21/02/2026
  Time: 7:32 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - Ocean View Resort</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tailwindcssOutput.css">
</head>

<body class="min-h-screen bg-slate-50 flex items-center justify-center px-4">

<%
    String error = (String) request.getAttribute("error");
%>

<!-- Toast Notification -->
<% if (error != null) { %>
<div id="toast"
     class="fixed middle-4 top-4 z-50 flex items-start gap-3 rounded-xl bg-red-600 text-white px-5 py-4 shadow-lg animate-fade-in">
    <div class="flex-1 text-sm font-semibold">
        <%= error %>
    </div>
    <button onclick="document.getElementById('toast').remove()"
            class="text-white text-lg leading-none font-bold hover:opacity-80">
        ×
    </button>
</div>
<% } %>

<div class="w-full max-w-md">

    <div class="bg-white border border-slate-200 rounded-2xl shadow-sm p-8">

        <div class="text-center">
            <h2 class="text-2xl font-bold">System User Login</h2>
            <p class="mt-1 text-sm text-slate-600">
                Staff and administrators only
            </p>
        </div>

        <form class="mt-6 space-y-4"
              method="POST"
              action="<%= request.getContextPath() %>/auth">

            <div>
                <label class="block text-sm font-semibold mb-1">Username</label>
                <input type="text"
                       name="username"
                       required
                       class="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm focus:border-blue-600 focus:ring-2 focus:ring-blue-100 outline-none">
            </div>

            <div>
                <label class="block text-sm font-semibold mb-1">Password</label>
                <input type="password"
                       name="password"
                       required
                       class="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm focus:border-blue-600 focus:ring-2 focus:ring-blue-100 outline-none">
            </div>

            <button type="submit"
                    class="w-full rounded-lg bg-blue-600 text-white py-2.5 font-semibold hover:bg-blue-700 transition">
                Login
            </button>
        </form>

        <div class="mt-6 text-center text-sm">
            <a href="<%= request.getContextPath() %>/index.jsp"
               class="font-semibold text-slate-700 hover:text-slate-900">
                ← Back to Home
            </a>
        </div>

    </div>

    <footer class="mt-6 text-center text-xs text-slate-500">
        <%= java.time.Year.now() %> Ocean View Resort | Java EE 8 | Tomcat 9
    </footer>

</div>
</body>
</html>
