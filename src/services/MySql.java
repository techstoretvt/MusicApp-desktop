/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author tranv
 */
import helpers.AppConstants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySql {

    public static Connection getConnection() {
        Connection conn = null;
        try {
//            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(AppConstants.DB_URL + "?useUnicode=true&characterEncoding=utf-8",
                    AppConstants.DB_USER_NAME, AppConstants.DB_PASSWORD);
            System.out.println("connect successful!");

        } catch (Exception er) {
            System.out.println("connect failure!");
            er.printStackTrace();
        }
        return conn;
    }

    public static ResultSet queryData(String sql) {
        ResultSet resultSet = null;
        Connection conn = null;
        Statement statement = null;

        try {
            // Kết nối tới MySQL Database
            conn = MySql.getConnection();
            if (conn == null) {
                return null;
            }

            // truy vấn user trong db
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);

        } catch (SQLException ex) {
            System.out.println("Lỗi truy vấn");
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return resultSet;
    }

    public static int excuteQuery(String sql) {
        int numRowsUpdated = 0;
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = MySql.getConnection(); // Kết nối tới cơ sở dữ liệu
            if (conn == null) {
                return 0;
            }

            // Thực thi câu lệnh SQL
            preparedStatement = conn.prepareStatement(sql);
            numRowsUpdated = preparedStatement.executeUpdate(); // Trả về số dòng đã được cập nhật

        } catch (SQLException ex) {
            System.out.println("Lỗi thực thi câu lệnh SQL.");
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Đảm bảo đóng tất cả các tài nguyên mở
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println("Lỗi khi đóng preparedStatement.");
                    Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Lỗi khi đóng kết nối.");
                    Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }

        return numRowsUpdated;
    }
}
