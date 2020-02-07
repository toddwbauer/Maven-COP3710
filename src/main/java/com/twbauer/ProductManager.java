// Add logging & change try/catches to throws
package com.twbauer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductManager {
    private Connection con = null;

    public ProductManager() throws SQLException {
        con = DriverManager.getConnection("jdbc:h2:C:\\Users\\abass\\IdeaProjects\\H2Template\\H2Demo\\libs\\productDB");
    }

   public void insertProd(String iQuery, String[] insertValues) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(iQuery);
        pstmt.setInt(1, Integer.parseInt(insertValues[0]));
        pstmt.setString(2, insertValues[1]);
        pstmt.executeUpdate();
    }

    public void selectAll() {
        ResultSet rs = null;

        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM employee;");

            while(rs.next()) {
                System.out.printf("uID = %d%n", rs.getInt("uid"));
                System.out.printf("Name = %s%n", rs.getString("name"));
            }

        } catch (SQLException e) {
            sqlExceptionHandler(e);
        }
    }

    public void closeCon() {
        try {
            con.close();
        } catch (SQLException e) {
            sqlExceptionHandler(e);
        }
    }

    public void sqlExceptionHandler(SQLException error) {
        // add logging, could make into a wrapper function
        System.out.println("Standard Failure: " + error.getMessage());
    }
}
