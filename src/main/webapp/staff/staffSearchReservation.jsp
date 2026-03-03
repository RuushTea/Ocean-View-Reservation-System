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
<body class="min-h-screen bg-slate-50 text-slate-900">
<div class="mx-auto max-w-3xl px-4 py-8">

    <div class="bg-white border border-slate-200 rounded-2xl shadow-sm p-6">
        <h1 class="text-xl font-bold">Search Reservation</h1>
        <p class="mt-1 text-slate-600">Enter reservation number to find a booking.</p>

        <form class="mt-6 space-y-4"
              method="post"
              action="<%= request.getContextPath() %>/staff/home">

            <input type="hidden" name="action" value="doSearch"/>

            <!-- Search Type -->
            <div>
                <label class="block text-sm font-semibold mb-1">Search By</label>
                <select name="searchType" class="w-full rounded-lg border border-slate-300 px-3 py-2">
                    <option value="reservationNo">Reservation No</option>
                    <option value="contactNo">Guest Contact No</option>
                </select>
            </div>

            <div>
                <label class="block text-sm font-semibold mb-1">Search Value</label>
                <input class="w-full rounded-lg border border-slate-300 px-3 py-2"
                       type="text" name="searchValue" required>
            </div>

            <div class="flex items-center gap-3">
                <button class="rounded-lg bg-blue-600 text-white px-4 py-2 font-semibold hover:bg-blue-700">
                    Search
                </button>

                <a class="text-sm font-semibold text-slate-700 hover:text-slate-900"
                   href="<%= request.getContextPath() %>/staff/home">
                    Back to dashboard
                </a>
            </div>

            <% String error = (String) request.getAttribute("error"); %>
            <% if (error != null) { %>
            <div class="mt-4 rounded-lg border border-red-200 bg-red-50 p-3 text-red-700">
                <%= error %>
            </div>
            <% } %>

        </form>
    </div>

</div>
</body>
</html>