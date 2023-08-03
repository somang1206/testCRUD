package com.ohgiraffers.CRUD.select;

import com.ohgiraffers.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        System.out.println("조회할 카테고리코드를 입력하세요 : ");
        int meName = Integer.parseInt(sc.nextLine());

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
                pstmt.setInt(1, meName);
                rset = pstmt.executeQuery();
                ArrayList<MenuDTO> dtoList = new ArrayList<MenuDTO>();
                while (rset.next()) {

                    int meCode = rset.getInt("MENU_CODE");
                    String name = rset.getString("MENU_NAME");
                    int price = rset.getInt("MENU_PRICE");
                    String orderable = rset.getString("ORDERABLE_STATUS");
                    int category = rset.getInt("CATEGORY_CODE");

                    MenuDTO dto = new MenuDTO(meCode, name, price, orderable, category);
                    dtoList.add(dto);

                }

                for(MenuDTO dto : dtoList) {
                    System.out.println(dto.getMenuName()  + "가격은" + dto.getMenuPrice() + "이고, 카테고리 코드는"
                            + dto.getCategoryCode() + "입니다. 현재 주문 가능 여부 :" + dto.getOrderableStatus());
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