package com.ohgiraffers.CRUD.insert;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Insert {
    public static void main(String[] args) {

        Connection con = getConnection();
        PreparedStatement pstmt = null;
        int result = 0;

        Scanner sc = new Scanner(System.in);
        System.out.println("조회할 상품을 입력하세요");
        String meName = sc.nextLine();
        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
            String query = prop.getProperty("insertMenu");

            try {
                pstmt = con.prepareStatement(query);
                pstmt.setString(1, "소곱창");
                pstmt.setInt(2, 21000);
                pstmt.setInt(3, 1);
                pstmt.setString(4, "Y");

                System.out.println("query: " + query);

                result = pstmt.executeUpdate();


            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(con);
            close(pstmt);
        }

        System.out.println("result: " + result);
    }
}
