<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Ocean View Resort</title>

  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tailwindcssOutput.css">
</head>

<body class="min-h-screen bg-gradient-to-br from-slate-50 to-slate-200 text-slate-900 flex flex-col">

<header class="bg-white shadow-sm border-b border-slate-200">
  <div class="mx-auto max-w-7xl px-6 py-5 flex items-center justify-between">
    <div>
      <h1 class="text-2xl font-bold tracking-tight">Ocean View Resort</h1>
      <p class="text-sm text-slate-500">Reservation Management System</p>
    </div>

    <a href="<%= request.getContextPath() %>/auth"
       class="rounded-xl border border-slate-300 bg-white px-6 py-2.5 text-sm font-semibold text-slate-700 hover:bg-slate-100 transition shadow-sm">
      Staff / Admin Login
    </a>
  </div>
</header>

<%--Guest card--%>
<main class="flex-1 flex items-center justify-center px-6 py-16">

  <section class="bg-white rounded-3xl shadow-lg border border-slate-200 max-w-4xl w-full text-center p-12">

    <h2 class="text-4xl md:text-5xl font-bold tracking-tight">
      Welcome to Ocean View Resort
    </h2>

    <p class="mt-6 text-lg text-slate-600 leading-relaxed max-w-2xl mx-auto">
      Experience the comfort of our beach-side resort located in Galle, Sri Lanka.
      <br>
      Guests can easily create reservations online in just a few steps.
    </p>

    <div class="mt-10 space-y-4">
      <a href="<%= request.getContextPath() %>/reservation"
         class="inline-flex items-center justify-center rounded-2xl bg-blue-600 px-10 py-4 text-lg font-semibold text-white hover:bg-blue-700 transition shadow-md">
        Make a Reservation
      </a>
      
      <div>
        <a href="<%= request.getContextPath() %>/guest/view"
           class="inline-flex items-center justify-center rounded-2xl border border-slate-300 bg-white px-10 py-4 text-lg font-semibold text-slate-700 hover:bg-slate-100 transition shadow-md">
          View Your Reservation
        </a>
      </div>
    </div>

    <div class="mt-12 bg-slate-50 rounded-2xl border border-slate-200 p-6">
      <p class="text-sm text-slate-700">
        Not a guest? Please login to the system for administrative tasks
      </p>
    </div>

  </section>

</main>
<footer class="text-center py-6 text-sm text-slate-500">
  <%= java.time.Year.now() %> Ocean View Resort | Java EE 8 | Tomcat 9
</footer>

</body>
</html>
