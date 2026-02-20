package com.oceanview.reservation.servlet;

import com.oceanview.reservation.dao.RoomTypeDAO;
import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.model.Reservation;
import com.oceanview.reservation.service.ReservationService;
import com.oceanview.reservation.service.ReservationServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

@WebServlet(name = "ReservationServlet", value = "/reservation")
public class ReservationServlet extends HttpServlet {

    private final ReservationService reservationService = new ReservationServiceImpl();
    private final RoomTypeDAO roomTypeDAO = new RoomTypeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        request.setAttribute("roomTypes", roomTypeDAO.findAll());

        try {
            request.getRequestDispatcher("/newReservation.jsp").forward(request, response);
        } catch (Exception e){
            System.out.println("Failed to forward to new reservation page: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        Guest guest = new Guest();
        guest.setName(request.getParameter("name"));
        guest.setAddress(request.getParameter("address"));
        guest.setContactNo(request.getParameter("contactNo"));

        int roomTypeId = Integer.parseInt(request.getParameter("roomTypeId"));
        Date checkIn = Date.valueOf(request.getParameter("checkInDate"));
        Date checkOut = Date.valueOf(request.getParameter("checkOutDate"));

        Reservation reservation = reservationService.createReservation(guest, roomTypeId, checkIn, checkOut);

        if (reservation == null){
            request.setAttribute("error", "No rooms available for the selected type or dates");
            doGet(request, response);
            return;
        }

        request.setAttribute("reservation", reservation);

        try {
            request.getRequestDispatcher("/reservationSuccess.jsp").forward(request, response);
        } catch (Exception e){
            System.out.println("Failed to forward to reservation success page: " + e.getMessage());
        }
    }
}
