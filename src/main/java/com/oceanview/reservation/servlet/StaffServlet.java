package com.oceanview.reservation.servlet;

import com.oceanview.reservation.dao.ReservationDAO;
import com.oceanview.reservation.dao.RoomTypeDAO;
import com.oceanview.reservation.model.Bill;
import com.oceanview.reservation.model.Reservation;
import com.oceanview.reservation.service.ReservationServiceImpl;
import com.oceanview.reservation.service.facade.ReservationFacade;
import com.oceanview.reservation.service.facade.ReservationFacadeInterface;
import com.oceanview.reservation.service.BillServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "StaffServlet", value = "/staff/home")
public class StaffServlet extends HttpServlet {

    private final ReservationFacadeInterface reservationFacade;
    private final ReservationDAO reservationDAO;
    private final RoomTypeDAO roomTypeDAO;

    public StaffServlet() {
        this.reservationFacade = new ReservationFacade(
            new ReservationServiceImpl(),
            new BillServiceImpl()
        );
        this.reservationDAO = new ReservationDAO();
        this.roomTypeDAO = new RoomTypeDAO();
    }


    private boolean isStaff(HttpSession session) {
        if (session.getAttribute("role") == null) {
            return false;
        }
        Object role = session.getAttribute("role");
        return role != null && "STAFF".equals(role.toString());
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !isStaff(session)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        //Find which action for which code
        String action = request.getParameter("action");

        if (action == null) {
            request.getRequestDispatcher("/staff/staffHome.jsp").forward(request, response);
            return;
        }

        //All get methods
        switch (action) {
            case "searchReservation": {
                request.getRequestDispatcher("/staff/staffSearchReservation.jsp").forward(request, response);
                break;
            }
            case "help": {
                request.getRequestDispatcher("/staff/help.jsp").forward(request, response);
                break;
            }
            case "doSearch": {
                searchWithSearchType(request, response);
                return;
            }
            case "edit": {
                int reservationId = Integer.parseInt(request.getParameter("reservationId"));
                request.setAttribute("roomTypes", roomTypeDAO.findAll());
                request.setAttribute("reservation", reservationDAO.findByReservationNo(reservationId));
                request.getRequestDispatcher("/staff/editReservation.jsp").forward(request, response);
                return;
            }
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);


        if (!isStaff(session)) {
            response.sendRedirect(request.getContextPath() + "/auth");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }


        int reservationNo;
        String id = request.getParameter("reservationNo");

        switch (action) {
            //Search reservation
            case "doSearch": {
                searchWithSearchType(request, response);
                return;
            }
            //Cancel reservation
            case "cancelReservation": {

                try {
                    reservationNo = Integer.parseInt(id);
                } catch (Exception e) {
                    response.sendRedirect(request.getContextPath() + "/staff/home?action=searchReservation");
                    return;
                }

                boolean success = reservationFacade.cancelReservation(reservationNo);
                if (!success) {
                    request.setAttribute("error", "Failed to cancel reservation for No: " + reservationNo);
                    request.setAttribute("reservation", reservationDAO.findByReservationNo(reservationNo));
                    request.getRequestDispatcher("/staff/staffReservationSearchResults.jsp").forward(request, response);
                    return;
                }

                // Get updated reservation and stay on same page
                Reservation updatedReservation = reservationFacade.searchReservation(reservationNo);
                request.setAttribute("reservation", updatedReservation);
                request.getRequestDispatcher("/staff/staffReservationSearchResults.jsp").forward(request, response);
                break;
            }
            //Generate bill
            case "generateBill": {
                try {
                    reservationNo = Integer.parseInt(id);
                } catch (Exception e) {
                    response.sendRedirect(request.getContextPath() + "/staff/home?action=searchReservation");
                    return;
                }

                Bill bill = reservationFacade.generateBill(reservationNo);
                if (bill == null) {
                    request.setAttribute("error", "Unable to generate bill for No: " + reservationNo);
                    request.getRequestDispatcher("/staff/home?action=searchReservation").forward(request, response);
                    return;
                } else {
                    request.setAttribute("bill", bill);
                }

                request.setAttribute("reservation", reservationDAO.findByReservationNo(reservationNo));
                request.getRequestDispatcher("/staff/staffGenerateBill.jsp").forward(request, response);
            }
            //Toggle to either cancel or reinstate reservation
            case "toggleCancel": {
                try {
                    reservationNo = Integer.parseInt(request.getParameter("reservationId"));
                } catch (Exception e) {
                    response.sendRedirect(request.getContextPath() + "/staff/home?action=searchReservation");
                    return;
                }

                boolean success = reservationFacade.toggleCancel(reservationNo);
                if (!success) {
                    request.setAttribute("error", "Unable to change reservation status for no:" + reservationNo);
                    request.setAttribute("reservation", reservationDAO.findByReservationNo(reservationNo));
                    request.getRequestDispatcher("/staff/staffReservationSearchResults.jsp").forward(request, response);
                    return;
                }

                // Get the updated reservation to display
                Reservation updatedReservation = reservationFacade.searchReservation(reservationNo);
                request.setAttribute("reservation", updatedReservation);
                request.getRequestDispatcher("/staff/staffReservationSearchResults.jsp").forward(request, response);
                break;
            }
            //Update reservation
            case "update": {
                reservationNo = Integer.parseInt(request.getParameter("reservationId"));

                String name = request.getParameter("name");
                String address = request.getParameter("address");
                String contactNo = request.getParameter("contactNo");

                int roomTypeId = Integer.parseInt(request.getParameter("roomTypeId"));
                Date checkInDate = Date.valueOf(request.getParameter("checkInDate"));
                Date checkOutDate = Date.valueOf(request.getParameter("checkOutDate"));

                //Input values
                Reservation updatedRes = reservationFacade.updateReservation(
                        reservationNo, name, address, contactNo, roomTypeId, checkInDate, checkOutDate
                );

                if (updatedRes == null) {
                    request.setAttribute("error", "Update failed: No rooms available or data is invalid");
                    request.setAttribute("roomTypes", roomTypeDAO.findAll());
                    request.setAttribute("reservation", reservationDAO.findByReservationNo(reservationNo));
                    request.getRequestDispatcher("/staff/editReservation.jsp").forward(request, response);
                    return;
                }

                Reservation updatedReservation = reservationFacade.searchReservation(reservationNo);
                request.setAttribute("reservation", updatedReservation);
                request.getRequestDispatcher("/staff/staffReservationSearchResults.jsp").forward(request, response);
                break;
            }
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void searchWithSearchType(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // reservationNo or contactNo
        String searchType = req.getParameter("searchType");
        //Typed search value
        String searchValue = req.getParameter("searchValue");

        if (searchType == null || searchType.trim().isEmpty()) {
            searchType = "reservationNo"; // default
        }

        if (searchValue == null || searchValue.trim().isEmpty()) {
            req.setAttribute("error", "Please enter a value to search.");
            req.getRequestDispatcher("/staff/staffSearchReservation.jsp").forward(req, resp);
            return;
        }

        searchValue = searchValue.trim();

        // Search by reservation number
        if ("reservationNo".equalsIgnoreCase(searchType)) {
            try {
                int reservationNo = Integer.parseInt(searchValue);

                if (reservationNo <= 0) {
                    req.setAttribute("error", "Reservation number must be greater than 0.");
                    req.getRequestDispatcher("/staff/staffSearchReservation.jsp").forward(req, resp);
                    return;
                }

                Reservation reservation = reservationFacade.searchReservation(reservationNo);

                if (reservation == null) {
                    req.setAttribute("error", "Reservation not found for No: " + reservationNo);
                    req.getRequestDispatcher("/staff/staffSearchReservation.jsp").forward(req, resp);
                    return;
                }

                // Display reservation details in detail view or list view
                String viewMode = req.getParameter("view");
                if ("detail".equals(viewMode)) {
                    List<Reservation> singleReservationList = new ArrayList<>();
                    singleReservationList.add(reservation);
                    req.setAttribute("reservations", singleReservationList);
                } else {
                    req.setAttribute("reservations", reservationFacade.searchByGuestContactNo(reservation.getGuest().getContactNo()));
                }

                req.setAttribute("reservation", reservation);
                req.setAttribute("searchValue", String.valueOf(reservationNo));
                req.getRequestDispatcher("/staff/staffReservationSearchResults.jsp").forward(req, resp);
                return;

            } catch (NumberFormatException e) {
                req.setAttribute("error", "Reservation number must be numeric.");
                req.getRequestDispatcher("/staff/staffSearchReservation.jsp").forward(req, resp);
                return;
            }
        }

        // Search by guest contact number
        if ("contactNo".equalsIgnoreCase(searchType)) {

            List<Reservation> list = reservationFacade.searchByGuestContactNo(searchValue);

            if (list == null || list.isEmpty()) {
                req.setAttribute("error", "No reservations found for contact number: " + searchValue);
                req.getRequestDispatcher("/staff/staffSearchReservation.jsp").forward(req, resp);
                return;
            }

            req.setAttribute("reservations", list);
            req.setAttribute("searchValue", searchValue);
            req.getRequestDispatcher("/staff/staffReservationSearchResults.jsp").forward(req, resp);
            return;
        }

        // if searchType is invalid
        req.setAttribute("error", "Invalid search type.");
        req.getRequestDispatcher("/staff/staffSearchReservation.jsp").forward(req, resp);
    }
}
