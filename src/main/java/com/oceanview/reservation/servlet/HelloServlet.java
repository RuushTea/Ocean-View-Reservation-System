package com.oceanview.reservation.servlet;

import com.oceanview.reservation.util.DBConnectionManager;
import java.io.*;
import java.sql.Connection;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello world, this is Rushdi Ramzaan!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection con = DBConnectionManager.getConnection()) {
            System.out.println("I am finally connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}