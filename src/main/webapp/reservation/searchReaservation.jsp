<%--
  Created by IntelliJ IDEA.
  User: Rushd
  Date: 21/02/2026
  Time: 4:04 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Search Reservation</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tailwindcssOutput.css">
</head>
<body class="bg-slate-50 min-h-screen flex items-center justify-center">

<div class="bg-white p-8 rounded-xl shadow-md border border-slate-200 w-full max-w-md">
    <h2 class="text-2xl font-bold mb-4">Search Reservation</h2>

    <form action="<%= request.getContextPath() %>/reservation" method="GET">
        <input type="hidden" name="action" value="search"/>

        <label class="block text-sm font-semibold mb-2">Reservation ID</label>
        <input type="number" name="reservationId"
               class="w-full border border-slate-300 rounded-lg px-4 py-2 mb-4"
               required
               min="1"
               max="9999999999"
               oninput="if(this.value < 1) this.value = ''; if(this.value > 9999999999) this.value = 9999999999;"
               placeholder="Enter reservation ID (positive number)"/>

        <button type="submit"
                class="w-full bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700">
            Search
        </button>
    </form>
</div>

</body>
</html>
