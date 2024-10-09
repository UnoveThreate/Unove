/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.canteen;

import database.MySQLConnect;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import util.RouterJSP;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "SelectFoodServlet", urlPatterns = {"/SelectFoodServlet"})
public class SelectFoodServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            connection = new MySQLConnect().connect(getServletContext());
        } catch (Exception e) {
            throw new ServletException("Unable to load database properties", e);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get list canteen item từ database (chèn data mẫu) , trước khi return về jsp 
        request.getRequestDispatcher(RouterJSP.CANTEEN_ITEM_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Mảng tên món ăn và giá tương ứng
        String[] itemNames = {
            "Popcorn Large", "Soda Medium", "Nachos", "Candy Bar",
            "Ice Cream", "Hot Dog", "Popcorn Small", "Water Bottle", "Chocolate"
        };
        float[] itemPrices = {4.50f, 3.00f, 5.00f, 2.00f, 3.50f, 4.00f, 3.00f, 1.50f, 2.50f};

        // Biến lưu trữ thông tin đơn hàng
        StringBuilder orderDetails = new StringBuilder();
        float totalPrice = 0.0f;

        // Kiểm tra từng món ăn
        for (int i = 0; i < itemNames.length; i++) {
            String quantityStr = request.getParameter("quantity_" + (i + 1));
            int quantity = (quantityStr != null && !quantityStr.isEmpty()) ? Integer.parseInt(quantityStr) : 0;

            if (quantity > 0) {
                float itemTotal = quantity * itemPrices[i];
                totalPrice += itemTotal;
                orderDetails.append(itemNames[i])
                        .append(" - Quantity: ")
                        .append(quantity)
                        .append(" - Total: $")
                        .append(itemTotal)
                        .append("<br>");
            }
        }

        // Lưu thông tin vào request
        request.setAttribute("orderDetails", orderDetails.toString());
        request.setAttribute("totalPrice", totalPrice);
        System.out.println(orderDetails);

        // Chuyển hướng đến trang JSP để hiển thị kết quả
        request.getRequestDispatcher("/page/order/OrderConfirmation.jsp").forward(request, response);
    }
}

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
/**
 * Handles the HTTP <code>GET</code> method.
 *
 * @param request servlet request
 * @param response servlet response
 * @throws ServletException if a servlet-specific error occurs
 * @throws IOException if an I/O error occurs
 */
/**
 * Handles the HTTP <code>POST</code> method.
 *
 * @param request servlet request
 * @param response servlet response
 * @throws ServletException if a servlet-specific error occurs
 * @throws IOException if an I/O error occurs
 */
/**
 * Returns a short description of the servlet.
 *
 * @return a String containing servlet description
 */
