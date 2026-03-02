package com.oceanview.reservation.servlet;

import com.oceanview.reservation.model.Bill;
import com.oceanview.reservation.model.Reservation;
import com.oceanview.reservation.service.ReservationService;
import com.oceanview.reservation.service.ReservationServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "StaffServlet", value = "/staff/home")
public class StaffServlet extends HttpServlet {

    private final ReservationService reservationService = new ReservationServiceImpl();

    private boolean isStaff(HttpSession session) {
        if (session.getAttribute("role") == null){
            return false;
        }
        Object role = session.getAttribute("role");
        return role != null && "STAFF".equals(role.toString());
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        HttpSession session = request.getSession(false);
        if (session == null || !isStaff(session)){
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        //Find which action for which code
        String action = request.getParameter("action");

        if (action == null){
            request.getRequestDispatcher("/staff/staffHome.jsp").forward(request, response);
            return;
        }

        switch (action){
            case "searchReservation":
                request.getRequestDispatcher("/staff/staffSearchReservation.jsp").forward(request, response);
                break;
            case "help":
                request.getRequestDispatcher("/staff/help.jsp").forward(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);


        if (!isStaff(session)){
            response.sendRedirect(request.getContextPath() + "/auth");
            return;
        }

        String action = request.getParameter("action");
        if (action == null){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }


        int reservationNo;
        String id = request.getParameter("reservationNo");

        switch (action){
            //Search reservation
            case "doSearch": {
                try {
                    reservationNo = Integer.parseInt(id);
                    if (reservationNo < 0) {
                        throw new NumberFormatException();
                    }
                } catch (Exception e) {
                    request.setAttribute("error", "Enter a valid reservation number");
                    request.getRequestDispatcher("/staff/staffSearchReservation.jsp").forward(request, response);
                    return;
                }

                Reservation reservation = reservationService.searchReservation(reservationNo);
                if (reservation == null) {
                    request.setAttribute("error", "Reservation not found for No: " + reservationNo);
                    request.getRequestDispatcher("/staff/staffSearchReservation.jsp").forward(request, response);
                    return;
                }

                request.setAttribute("reservation", reservation);
                request.getRequestDispatcher("/staff/staffReservationDetails.jsp").forward(request, response);
                break;
            }
            //Cancel reservation
            case "cancelReservation":{

                try {
                    reservationNo = Integer.parseInt(id);
                } catch (Exception e) {
                    response.sendRedirect(request.getContextPath() + "/staff/home?action=searchReservation");
                    return;
                }

                boolean success = reservationService.cancelReservation(reservationNo);
                if (!success) {
                    request.setAttribute("error", "Failed to cancel reservation for No: " + reservationNo);
                    request.getRequestDispatcher("/staff/home?action=searchReservation").forward(request, response);
                    return;
                }
                response.sendRedirect(request.getContextPath() + "/staff/home?action=searchReservation");
                break;
            }
            //Generate bill
            case "generateBill":{
                try {
                    reservationNo = Integer.parseInt(id);
                } catch (Exception e){
                    response.sendRedirect(request.getContextPath() + "/staff/home?action=searchReservation");
                    return;
                }

                Bill bill = reservationService.generateBill(reservationNo);

                if (bill == null){
                    request.setAttribute("error", "Unable to generate bill for No: " + reservationNo);
                    request.getRequestDispatcher("/staff/home?action=searchReservation").forward(request, response);
                    return;
                }

                request.setAttribute("bill", bill);
                request.getRequestDispatcher("/staff/staffGenerateBill.jsp").forward(request, response);
            }
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
