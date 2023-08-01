package com.ohgiraffers.CRUD.select;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Select {
    public static void main(String[] args) {
        Connection con = getConnection();


        PreparedStatement pstmt = null;
        ResultSet rset = null;

        Scanner sc = new Scanner(System.in);
        System.out.println("조회할 메뉴명을 입력하세요 : ");
        String meName = sc.nextLine();

        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));

            String query = prop.getProperty("selectMenu");
            System.out.println("query : " + query);

            try {
                pstmt = con.prepareStatement(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                pstmt.setString(1, meName);

                rset = pstmt.executeQuery();

                while (rset.next()) {
                    Integer code = rset.getInt("MENU_CODE");
                    String name = rset.getString("MENU_NAME");
                    Integer price = rset.getInt("MENU_PRICE");
                    String orderable = rset.getString("ORDERABLE_STATUS");
                    Integer category = rset.getInt("CATEGORY_CODE");

                    System.out.println(name + "상품의 코드는" + code + "번이고," + "가격은" + price + "이고, 카테고리 코드는"
                            + category + "입니다. 현재 주문 가능 여부 :" + orderable);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(con);
            close(pstmt);
            close(rset);
        }
    }
}