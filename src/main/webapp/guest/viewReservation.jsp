<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>View Your Reservation - Ocean View Resort</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tailwindcssOutput.css">
</head>

<body class="min-h-screen bg-gradient-to-br from-slate-50 to-slate-200 text-slate-900">

<header class="bg-white shadow-sm border-b border-slate-200">
    <div class="mx-auto max-w-7xl px-6 py-5 flex items-center justify-between">
        <div>
            <h1 class="text-2xl font-bold tracking-tight">Ocean View Resort</h1>
            <p class="text-sm text-slate-500">Guest Reservation Portal</p>
        </div>
        <a href="<%= request.getContextPath() %>/"
           class="rounded-xl border border-slate-300 bg-white px-6 py-2.5 text-sm font-semibold text-slate-700 hover:bg-slate-100 transition shadow-sm">
            Back to Home
        </a>
    </div>
</header>

<main class="flex-1 flex items-center justify-center px-6 py-16">
    <section class="bg-white rounded-3xl shadow-lg border border-slate-200 max-w-2xl w-full p-10">
        <h2 class="text-3xl font-bold tracking-tight text-center mb-8">
            View Your Reservation
        </h2>

        <form action="<%= request.getContextPath() %>/guest/view" method="POST" class="space-y-6" onsubmit="return validateForm()">
            <% if (request.getAttribute("error") != null) { %>
                <div class="bg-red-50 border border-red-200 rounded-xl p-4">
                    <p class="text-red-700 text-sm font-medium">
                        <%= request.getAttribute("error") %>
                    </p>
                </div>
            <% } %>
            
            <div>
                <label for="contactNo" class="block text-sm font-semibold text-slate-700 mb-2">
                    Contact Number <span class="text-red-500">*</span>
                </label>
                <input type="text" 
                       id="contactNo" 
                       name="contactNo"
                       class="w-full border border-slate-300 rounded-xl px-4 py-3 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition"
                       placeholder="Enter your contact number (e.g., 0712345678)"
                       pattern="[0-9]{10}"
                       maxlength="10"
                       required/>
                <p class="mt-1 text-xs text-slate-500">Enter 10-digit contact number</p>
            </div>

            <button type="submit"
                    class="w-full bg-blue-600 text-white py-3 rounded-xl hover:bg-blue-700 transition font-semibold shadow-md disabled:opacity-50 disabled:cursor-not-allowed">
                View All Reservations
            </button>
        </form>

        <div class="mt-8 bg-slate-50 rounded-2xl border border-slate-200 p-6">
            <p class="text-sm text-slate-600 text-center">
                Please enter your contact number to view all your reservations.
            </p>
        </div>
    </section>
</main>

<footer class="text-center py-6 text-sm text-slate-500">
    <%= java.time.Year.now() %> Ocean View Resort | Guest Reservation Portal
</footer>

<script>
function validateForm() {
    const contactNo = document.getElementById('contactNo').value.trim();
    
    if (!contactNo) {
        alert('Please fill in all required fields');
        return false;
    }
    
    if (!/^[0-9]{10}$/.test(contactNo)) {
        alert('Contact number must be exactly 10 digits');
        return false;
    }
    
    return true;
}

document.getElementById('contactNo').addEventListener('input', function(e) {
    this.value = this.value.replace(/[^0-9]/g, '');
});
</script>

</body>
</html>
