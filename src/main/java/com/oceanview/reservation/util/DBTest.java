package com.oceanview.reservation.util;

import java.sql.Connection;

public class DBTest {
    public static void main(String[] args) {
        try (Connection con = DBConnectionManager.getConnection()) {
            System.out.println("Connected! " + con.getMetaData().getDatabaseProductName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
