package com.ohgiraffers.CRUD.insert;

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

public class Insert {
    public static void main(String[] args) {

        Connection con = getConnection();
        PreparedStatement pstmt = null;
        int result = 0;



        Scanner sc = new Scanner(System.in);

        System.out.print("추가할 상품의 코드를 입력하세요");
        int menuCode = Integer.parseInt(sc.nextLine());

        System.out.print("추가할 상품을 입력하세요");
        String menuName = sc.nextLine();

        System.out.print("가격을 설정해주세요");
        int menuPrice = Integer.parseInt(sc.nextLine());

        System.out.print("주문가능여부");
        String orderableStatus = sc.nextLine();

        System.out.print("카테고리의 코드를 입력해주세요");
        int categoryCode = Integer.parseInt(sc.nextLine());


        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
            String query = prop.getProperty("insertMenu");

                pstmt = con.prepareStatement(query);

                MenuDTO menuDTO = new MenuDTO(menuCode, menuName, menuPrice, orderableStatus, categoryCode);

                pstmt.setInt(1, menuDTO.getMenuCode());
                pstmt.setString(2, menuDTO.getMenuName());
                pstmt.setInt(3, menuDTO.getMenuPrice());
                pstmt.setInt(4, menuDTO.getCategoryCode());
                pstmt.setString(5, menuDTO.getOrderableStatus());

                System.out.println("query: " + query);

                result = pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(con);
            close(pstmt);
        }

        System.out.println("result: " + result);
    }
}
