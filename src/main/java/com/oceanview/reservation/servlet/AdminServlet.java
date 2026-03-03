package com.oceanview.reservation.servlet;

import com.oceanview.reservation.model.Staff;
import com.oceanview.reservation.model.SystemUser;
import com.oceanview.reservation.service.AdminService;
import com.oceanview.reservation.service.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminServlet", value = "/admin/home")
public class AdminServlet extends HttpServlet {

    private final AdminService adminService = new AdminServiceImpl();

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && "ADMIN".equals(session.getAttribute("role"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/auth");
            return;
        }

        String action = request.getParameter("action");
        if (action == null || action.equals("dashboard")) {
            request.getRequestDispatcher("/admin/adminHome.jsp").forward(request, response);
            return;
        }

        switch (action) {

            case "adminManageStaff": {
                List<SystemUser> staffUsers = adminService.getAllStaffUsers();
                request.setAttribute("staffUsers", staffUsers);

                request.getRequestDispatcher("/admin/adminManageStaff.jsp").forward(request, response);
                return;
            }

            case "adminEditUser": {
                int userId = Integer.parseInt(request.getParameter("userId"));
                SystemUser editUser = adminService.getStaffByUserId(userId);

                if (editUser == null) {
                    request.setAttribute("error", "Staff account not found.");
                    List<SystemUser> staffUsers = adminService.getAllStaffUsers();
                    request.setAttribute("staffUsers", staffUsers);
                    request.getRequestDispatcher("/admin/adminManageStaff.jsp").forward(request, response);
                    return;
                }

                request.setAttribute("editUser", editUser);
                request.getRequestDispatcher("/admin/adminEditUser.jsp").forward(request, response);
                return;
            }

            default:
                response.sendRedirect(request.getContextPath() + "/admin/home?action=dashboard");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/auth");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/admin/home?action=dashboard");
            return;
        }

        switch (action) {
            case "updateStaffCredentials": {
                int userId = Integer.parseInt(request.getParameter("userId"));
                String username = request.getParameter("username");
                String password = request.getParameter("password"); // may be blank
                String fullName = request.getParameter("fullName");

                boolean updated = adminService.updateStaffUser(userId, username, password, fullName);

                if (!updated) {
                    request.setAttribute("error", "Update failed. Check inputs or staff account not found.");
                    request.setAttribute("editUser", adminService.getStaffByUserId(userId));
                    request.getRequestDispatcher("/admin/adminEditUser.jsp").forward(request, response);
                    return;
                }

                response.sendRedirect(request.getContextPath() + "/admin/home?action=adminManageStaff&msg=updated");
                return;
            }

            default:
                response.sendRedirect(request.getContextPath() + "/admin/home?action=dashboard");
        }
    }
}
