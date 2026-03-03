<%--
  Created by IntelliJ IDEA.
  User: Rushd
  Date: 03/03/2026
  Time: 6:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.oceanview.reservation.model.HelpArticle" %>

<%
    List<HelpArticle> articles = (List<HelpArticle>) request.getAttribute("articles");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Help Articles</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tailwindcssOutput.css">
</head>

<body class="min-h-screen bg-slate-50 text-slate-900">

<header class="bg-white border-b border-slate-200 shadow-sm">
    <div class="mx-auto max-w-6xl px-4 py-4 flex items-center justify-between">
        <div>
            <h1 class="text-lg font-semibold">Help Section</h1>
            <p class="text-sm text-slate-500">Guidelines for Staff and Admin users</p>
        </div>

        <%--Back button to return to role specific home--%>
        <%
            String role = (String) session.getAttribute("role");
            String url = "/" + role.toLowerCase() + "/home";
        %>
        <a href="<%= request.getContextPath() + url %>"
           class="inline-flex items-center justify-center rounded-lg border border-slate-300 bg-white px-4 py-2 text-sm font-semibold text-slate-700 hover:bg-slate-100 shadow-sm">
            Back to Dashboard
        </a>
    </div>
</header>

<main class="mx-auto max-w-6xl px-4 py-8">
    <section class="bg-white border border-slate-200 rounded-2xl shadow-sm p-6">
        <div class="flex items-start justify-between gap-4">
            <div>
                <h2 class="text-xl font-bold">Available Articles</h2>
                <p class="mt-1 text-sm text-slate-600">
                    Select an article to view step-by-step instructions.
                </p>
            </div>
        </div>

        <div class="mt-6">
            <% if (articles == null || articles.isEmpty()) { %>
            <div class="rounded-lg border border-slate-200 bg-slate-50 p-4 text-sm text-slate-700">
                No help articles found. Ask an Admin to add some help content to the system.
            </div>
            <% } else { %>
            <div class="divide-y divide-slate-200 border border-slate-200 rounded-xl overflow-hidden">
                <% for (HelpArticle a : articles) { %>
                <a class="block p-4 hover:bg-slate-50"
                   href="<%= request.getContextPath() %>/help?id=<%= a.getArticleId() %>">
                    <div class="flex items-center justify-between gap-4">
                        <div>
                            <p class="font-semibold text-slate-900"><%= a.getTitle() %></p>
                        </div>

                        <span class="text-sm font-semibold text-blue-700">
                                    View →
                                </span>
                    </div>
                </a>
                <% } %>
            </div>
            <% } %>
        </div>
    </section>

    <footer class="mt-10 text-center text-xs text-slate-500">
        <%= java.time.Year.now() %> Ocean View Resort | Help Section
    </footer>
</main>

</body>
</html>