<%--
  Created by IntelliJ IDEA.
  User: Rushd
  Date: 02/03/2026
  Time: 7:29 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Search Reservation</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tailwindcssOutput.css">
</head>
<body class="min-h-screen bg-slate-50">
<div class="mx-auto max-w-4xl p-6">
    <div class="bg-white border border-slate-200 rounded-xl p-6 shadow-sm">
        <h1 class="text-xl font-bold">Search Reservation</h1>
        <p class="text-slate-600 mt-1">Enter reservation number to view details.</p>

        <form class="mt-6 space-y-3" method="post" action="<%= request.getContextPath() %>/staff?action=doSearchReservation">
            <div>
                <label class="block text-sm font-semibold mb-1">Reservation No</label>
                <input class="w-full rounded-lg border border-slate-300 px-3 py-2"
                       type="number" name="reservationId" min="1" required />
            </div>

            <button class="rounded-lg bg-blue-600 text-white px-4 py-2 font-semibold hover:bg-blue-700">
                Search
            </button>

            <a class="ml-2 text-sm font-semibold text-slate-700 hover:text-slate-900"
               href="<%= request.getContextPath() %>/staff">
                Back to dashboard
            </a>
        </form>

        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
        <div class="mt-4 rounded-lg border border-red-200 bg-red-50 p-3 text-red-700">
            <%= error %>
        </div>
        <% } %>
    </div>
</div>
</body>
</html>