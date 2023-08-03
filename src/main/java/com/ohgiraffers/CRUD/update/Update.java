package com.ohgiraffers.CRUD.update;

import com.ohgiraffers.model.dto.MenuDTO;

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

        System.out.println("수정할 상품의 코드를 입력하세요.");
        int meCode = Integer.parseInt(sc.nextLine());

        System.out.println("수정할 상품을 입력하세요.");
        String meName = sc.nextLine();

        System.out.println("상품의 가격을 설정해주세요");
        int mePrice = Integer.parseInt(sc.nextLine());

        System.out.println("주문 가능 여부를 입력하세요");
        String orderStatus = sc.nextLine();

        System.out.println("카테고리 코드를 입력하세요");
        int cateCode = Integer.parseInt(sc.nextLine());



        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
            String query = prop.getProperty("updateMenu");

            try {
                MenuDTO dto = new MenuDTO(meCode, meName, mePrice, orderStatus, cateCode);

                pstmt = con.prepareStatement(query);

                pstmt.setString(1, dto.getMenuName());
                pstmt.setInt(2, dto.getMenuPrice());
                pstmt.setString(3, dto.getOrderableStatus());
                pstmt.setInt(4, dto.getCategoryCode());
                pstmt.setInt(5, dto.getMenuCode());
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
