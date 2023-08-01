package com.ohgiraffers.CRUD.update;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Update {
    public static void main(String[] args) {
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        int result = 0;

        Properties prop = new Properties();

        Scanner sc = new Scanner(System.in);
        System.out.println("선택할 상품을 조회하세요.");
        String meName = sc.nextLine();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
            String query = prop.getProperty("updateMenu");
            try {
                pstmt = con.prepareStatement(query);
                pstmt.setString(1, "감자튀김");
                pstmt.setInt(2, 2500);
                pstmt.setInt(3, 1 );
                pstmt.setInt(4, 20);

                result = pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(con);
            close(pstmt);
        }
        System.out.println("result: " + result);
    }
}
