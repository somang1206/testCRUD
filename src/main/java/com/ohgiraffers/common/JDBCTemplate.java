package com.ohgiraffers.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCTemplate {
    public static Connection getConnection() {

        Connection con = null;
        Properties prop = new Properties();

        try {
            prop.load(new FileReader("src/main/java/com/ohgiraffers/config/connection-info.properties"));
            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");

            try {
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            try {
                con = DriverManager.getConnection(url, prop);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void close(Connection con){
            try {
                if(con != null && !con.isClosed()){
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static void close(PreparedStatement pstmt) {

        try {
            if(pstmt != null && !pstmt.isClosed()) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void close(ResultSet rset) {
        try {
            if (rset != null && !rset.isClosed()){
                rset.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
