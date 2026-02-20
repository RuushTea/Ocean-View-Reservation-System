<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Ocean View Resort System</title>

<%-- Tailwind  --%>
  <link rel="stylesheet" href="css/tailwindcssOutput.css">
</head>

<body class="min-h-screen bg-slate-50 text-slate-900">

<header class="border-b border-slate-200 bg-white shadow-sm">
  <div class="mx-auto max-w-6xl px-4 py-4 flex items-center justify-between">
    <div class="flex items-center gap-3">
      <div>
        <h1 class="text-lg font-semibold leading-tight">Ocean View Resort</h1>
        <p class="text-sm text-slate-500">Reservation Management System</p>
      </div>
    </div>

    <nav class="flex items-center gap-2">
      <a class="inline-flex items-center justify-center rounded-lg border border-slate-200 bg-white px-3 py-2 text-sm font-medium text-slate-700 hover:bg-slate-50 shadow-sm"
         href="<%= request.getContextPath() %>/auth">
        Login
      </a>
    </nav>
  </div>
</header>

<main class="mx-auto max-w-6xl px-4 py-8">
  <section class="bg-white border border-slate-200 rounded-xl shadow-sm p-6 md:p-8">
    <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-6">
      <div>
        <h2 class="text-2xl md:text-3xl font-bold">Welcome</h2>
        <p class="mt-2 text-slate-600 max-w-2xl">
          Use the quick actions below to add guests, search guest records, or create new reservations.
        </p>

        <div class="mt-4 flex flex-wrap gap-2">
          <a class="inline-flex items-center justify-center rounded-lg bg-blue-600 px-4 py-2 text-sm font-semibold text-white hover:bg-blue-700"
             href="<%= request.getContextPath() %>/reservation">
            Create Reservation
          </a>
          <a class="inline-flex items-center justify-center rounded-lg border border-slate-200 bg-white px-4 py-2 text-sm font-semibold text-slate-700 hover:bg-slate-50 shadow-sm"
             href="<%= request.getContextPath() %>/auth">
            Go to Login
          </a>
        </div>
      </div>

      <div class="w-full md:w-80 rounded-xl bg-slate-50 border border-slate-200 shadow-sm p-4">
        <p class="text-xs font-semibold text-slate-500 uppercase">System Tips</p>
        <ul class="mt-2 space-y-2 text-sm text-slate-700 list-disc pl-5">
          <li>Guests can self-serve and do reservations themselves.</li>
          <li>Room availability is checked when reservation is submitted.</li>
        </ul>
      </div>
    </div>
  </section>

<%-- Cards --%>
  <section class="mt-8 grid grid-cols-1 lg:grid-cols-2 gap-6">
    <div class="rounded-xl bg-white border border-slate-200 shadow-sm p-6">
      <div class="flex items-start justify-between gap-4">
        <div>
          <h3 class="text-lg font-semibold">Add Guest</h3>
          <p class="text-sm text-slate-600 mt-1">Create a new guest record.</p>
        </div>
        <span class="inline-flex items-center rounded-full bg-emerald-50 px-3 py-1 text-xs font-semibold text-emerald-700 border border-emerald-100">
            Guest
          </span>
      </div>

      <form class="mt-5 space-y-4" action="<%= request.getContextPath() %>/guest-servlet" method="POST">
        <div>
          <label class="block text-sm font-medium text-slate-700" for="name">Name</label>
          <input
                  class="mt-1 w-full rounded-lg border border-slate-300 bg-white px-3 py-2 text-sm outline-none focus:border-blue-600 focus:ring-2 focus:ring-blue-100"
                  type="text" id="name" name="name" placeholder="e.g. Rushdi Ramzaan" required />
        </div>

        <div>
          <label class="block text-sm font-medium text-slate-700" for="address">Address</label>
          <input
                  class="mt-1 w-full rounded-lg border border-slate-300 bg-white px-3 py-2 text-sm outline-none focus:border-blue-600 focus:ring-2 focus:ring-blue-100"
                  type="text" id="address" name="address" placeholder="e.g. Galle / Colombo" required />
        </div>

        <div>
          <label class="block text-sm font-medium text-slate-700" for="contactNo">Contact Number</label>
          <input
                  class="mt-1 w-full rounded-lg border border-slate-300 bg-white px-3 py-2 text-sm outline-none focus:border-blue-600 focus:ring-2 focus:ring-blue-100"
                  type="text" id="contactNo" name="contactNo" placeholder="e.g. 0771234567" required />
        </div>

        <div class="flex items-center gap-3 pt-2">
          <button
                  class="inline-flex items-center justify-center rounded-lg bg-emerald-600 px-4 py-2 text-sm font-semibold text-white hover:bg-emerald-700"
                  type="submit">
            Add Guest
          </button>

          <a class="text-sm font-semibold text-slate-700 hover:text-slate-900"
             href="<%= request.getContextPath() %>/reservation">
            Create reservation instead →
          </a>
        </div>
      </form>
    </div>

<%--  Search Guests  --%>
    <div class="rounded-xl bg-white border border-slate-200 shadow-sm p-6">
      <div class="flex items-start justify-between gap-4">
        <div>
          <h3 class="text-lg font-semibold">Search Guest</h3>
          <p class="text-sm text-slate-600 mt-1">Find guest details using Guest ID.</p>
        </div>
        <span class="inline-flex items-center rounded-full bg-blue-50 px-3 py-1 text-xs font-semibold text-blue-700 border border-blue-100">
            Lookup
          </span>
      </div>

      <form class="mt-5 space-y-4" action="<%= request.getContextPath() %>/guest-servlet" method="GET">
        <div>
          <label class="block text-sm font-medium text-slate-700" for="guestId">Guest ID</label>
          <input
                  class="mt-1 w-full rounded-lg border border-slate-300 bg-white px-3 py-2 text-sm outline-none focus:border-blue-600 focus:ring-2 focus:ring-blue-100"
                  type="number" id="guestId" name="guestId" placeholder="e.g. 1" min="1" required />
        </div>

        <div class="flex items-center gap-3 pt-2">
          <button
                  class="inline-flex items-center justify-center rounded-lg bg-blue-600 px-4 py-2 text-sm font-semibold text-white hover:bg-blue-700"
                  type="submit">
            Search
          </button>

          <a class="inline-flex items-center justify-center rounded-lg border border-slate-200 bg-white px-4 py-2 text-sm font-semibold text-slate-700 hover:bg-slate-50 shadow-sm"
             href="<%= request.getContextPath() %>/auth">
            Go to login →
          </a>
        </div>

        <p class="text-xs text-slate-500">
        </p>
      </form>
    </div>
  </section>

  <footer class="mt-10 text-center text-xs text-slate-500">
    <%= java.time.Year.now() %> Ocean View Resort | Java EE 8 | Tomcat 9
  </footer>
</main>
</body>
</html>