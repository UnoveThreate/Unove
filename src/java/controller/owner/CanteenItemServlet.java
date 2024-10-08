/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import DAO.CanteenItemDAO;
import model.CanteenItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import util.RouterJSP;

/**
 *
 * @author Kaan
 */
@WebServlet("/CanteenItemServlet")
public class CanteenItemServlet extends HttpServlet {

    private CanteenItemDAO canteenItemDAO;

    @Override
    public void init() throws ServletException {
        try {
            canteenItemDAO = new CanteenItemDAO(getServletContext());
        } catch (Exception e) {
            throw new ServletException("Failed to initialize CanteenItemDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy cinemaID từ request
            int cinemaID = Integer.parseInt(request.getParameter("cinemaID"));
            List<CanteenItem> items = canteenItemDAO.getAllCanteenItems(cinemaID);
            request.setAttribute("canteenItems", items);
            request.setAttribute("cinemaID", cinemaID); // Thiết lập cinemaID cho JSP
            request.getRequestDispatcher(RouterJSP.CANTEEN_ITEM_UPLOAD_PAGE).forward(request, response);
        } catch (Exception e) {
            log("Error processing request: " + e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching canteen items");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            // Kiểm tra action để gọi phương thức tương ứng
            if ("add".equals(action)) {
                addCanteenItem(request, response);
            } else if ("update".equals(action)) {
                updateCanteenItem(request);
            } else if ("delete".equals(action)) {
                deleteCanteenItem(request);
            }
            // Chuyển hướng về danh sách Canteen Items sau khi thực hiện action
            response.sendRedirect("CanteenItemServlet?cinemaID=" + request.getParameter("cinemaID"));
        } catch (Exception e) {
            log("Error processing request: " + e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request");
        }
    }

    private void addCanteenItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Lấy cinemaID và kiểm tra
        String cinemaIdStr = request.getParameter("cinemaID");
        if (cinemaIdStr == null || cinemaIdStr.isEmpty()) {
            throw new IllegalArgumentException("Cinema ID is required.");
        }
        int cinemaID = Integer.parseInt(cinemaIdStr); // Chuyển đổi cinemaID

        // Lấy các tham số khác từ request
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");
        String stockStr = request.getParameter("stock");
        String status = request.getParameter("status");

        // Kiểm tra xem tất cả các trường đã được nhập chưa
        if (name == null || priceStr == null || stockStr == null || status == null) {
            throw new IllegalArgumentException("All fields must be filled out.");
        }

        // Chuyển đổi giá trị price và stock
        float price = Float.parseFloat(priceStr);
        int stock = Integer.parseInt(stockStr);

        // Tạo đối tượng CanteenItem mới
        CanteenItem item = new CanteenItem(0, cinemaID, name, price, stock, status);

        // Thêm item vào cơ sở dữ liệu
        canteenItemDAO.addCanteenItem(item);
    }

    private void updateCanteenItem(HttpServletRequest request) throws Exception {
        // Lấy các tham số từ request
        CanteenItem item = new CanteenItem(
                Integer.parseInt(request.getParameter("canteenItemID")),
                Integer.parseInt(request.getParameter("cinemaID")),
                request.getParameter("name"),
                Float.parseFloat(request.getParameter("price")),
                Integer.parseInt(request.getParameter("stock")),
                request.getParameter("status") // Không thay đổi status
        );

        // Cập nhật item trong cơ sở dữ liệu
        canteenItemDAO.updateCanteenItem(item);
    }

    private void deleteCanteenItem(HttpServletRequest request) throws Exception {
        // Lấy ID của item cần xóa
        int id = Integer.parseInt(request.getParameter("canteenItemID"));
        // Thay vì xóa, cập nhật status thành false (unavailable)
        canteenItemDAO.softDeleteCanteenItem(id);
    }
}
