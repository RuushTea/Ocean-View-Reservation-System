<%--
  Created by IntelliJ IDEA.
  User: Rushd
  Date: 03/03/2026
  Time: 6:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.oceanview.reservation.model.HelpArticle" %>

<%
    HelpArticle article = (HelpArticle) request.getAttribute("article");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>View Help Article</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tailwindcssOutput.css">
</head>

<body class="min-h-screen bg-slate-50 text-slate-900">

<header class="bg-white border-b border-slate-200 shadow-sm">
    <div class="mx-auto max-w-6xl px-4 py-4 flex items-center justify-between">
        <div>
            <h1 class="text-lg font-semibold">Help Article</h1>
            <p class="text-sm text-slate-500">Read-only guidance for staff and admin</p>
        </div>

        <div class="flex items-center gap-2">
            <a href="<%= request.getContextPath() %>/help"
               class="inline-flex items-center justify-center rounded-lg border border-slate-300 bg-white px-4 py-2 text-sm font-semibold text-slate-700 hover:bg-slate-100 shadow-sm">
                Back to Help List
            </a>

            <a href="<%= request.getContextPath() %>/staff/home"
               class="inline-flex items-center justify-center rounded-lg border border-slate-300 bg-white px-4 py-2 text-sm font-semibold text-slate-700 hover:bg-slate-100 shadow-sm">
                Dashboard
            </a>
        </div>
    </div>
</header>

<main class="mx-auto max-w-6xl px-4 py-8">

    <% if (article == null) { %>
    <section class="bg-white border border-slate-200 rounded-2xl shadow-sm p-6">
        <h2 class="text-xl font-bold">Article Not Found</h2>
        <p class="mt-2 text-sm text-slate-600">
            The requested help article does not exist or was removed.
        </p>

        <div class="mt-6">
            <a href="<%= request.getContextPath() %>/help"
               class="inline-flex items-center justify-center rounded-lg bg-blue-600 px-4 py-2 text-sm font-semibold text-white hover:bg-blue-700 shadow-sm">
                Return to Help List
            </a>
        </div>
    </section>
    <% } else { %>
    <section class="bg-white border border-slate-200 rounded-2xl shadow-sm p-6">
        <div class="flex items-start justify-between gap-4">
            <div>
                <h2 class="text-2xl font-bold"><%= article.getTitle() %></h2>
            </div>

            <span class="inline-flex items-center rounded-full bg-blue-50 px-3 py-1 text-xs font-semibold text-blue-700 border border-blue-100">
                    Help
                </span>
        </div>

        <div class="mt-6 border-t border-slate-200 pt-5">
            <p class="text-sm text-slate-700 whitespace-pre-line">
                <%= article.getContent() %>
            </p>
        </div>

        <div class="mt-8 flex items-center gap-3">
            <a href="<%= request.getContextPath() %>/help"
               class="inline-flex items-center justify-center rounded-lg border border-slate-300 bg-white px-4 py-2 text-sm font-semibold text-slate-700 hover:bg-slate-100 shadow-sm">
                Back to Help List
            </a>

            <a href="<%= request.getContextPath() %>/staff/home"
               class="inline-flex items-center justify-center rounded-lg bg-blue-600 px-4 py-2 text-sm font-semibold text-white hover:bg-blue-700 shadow-sm">
                Back to Dashboard
            </a>
        </div>
    </section>
    <% } %>

    <footer class="mt-10 text-center text-xs text-slate-500">
        <%= java.time.Year.now() %> Ocean View Resort | Help Section
    </footer>

</main>
</body>
</html>
