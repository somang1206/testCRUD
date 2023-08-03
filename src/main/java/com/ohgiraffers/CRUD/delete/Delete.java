package com.ohgiraffers.CRUD.delete;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Delete {
    public static void main(String[] args) {
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        int result = 0;
        Properties prop = new Properties();

        Scanner sc = new Scanner(System.in);
        System.out.println("검색할 메뉴코드를 입력하세요");
        String meName = sc.nextLine();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
            String query = prop.getProperty("deleteMenu");

            try {
                pstmt = con.prepareStatement(query);
                pstmt.setInt(1, 44);

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
        System.out.println("result :" + result);
    }
}
