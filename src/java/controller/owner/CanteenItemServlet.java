package controller.owner;

import DAO.CanteenItemDAO;
import model.CanteenItem;
import util.FileUploader;
import util.RouterJSP;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import static java.lang.Boolean.TRUE;
import java.util.List;

@WebServlet("/CanteenItemServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
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
            // Lấy cinemaID từ request và kiểm tra
            String cinemaIDStr = request.getParameter("cinemaID");
            if (cinemaIDStr == null || cinemaIDStr.isEmpty()) {
                throw new IllegalArgumentException("Cinema ID is required.");
            }

            int cinemaID = Integer.parseInt(cinemaIDStr);
            List<CanteenItem> items = canteenItemDAO.getAllCanteenItems(cinemaID);

            request.setAttribute("canteenItems", items);
            request.setAttribute("cinemaID", cinemaID); // Thiết lập cinemaID cho JSP
            request.getRequestDispatcher(RouterJSP.CANTEEN_ITEM_UPLOAD_PAGE).forward(request, response);
        } catch (NumberFormatException e) {
            log("Invalid Cinema ID format: " + e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Cinema ID format");
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

        int cinemaID;
        try {
            cinemaID = Integer.parseInt(cinemaIdStr); // Chuyển đổi cinemaID
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid Cinema ID format.");
        }

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
        float price;
        int stock;
        try {
            price = Float.parseFloat(priceStr);
            stock = Integer.parseInt(stockStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid price or stock format.");
        }

        // Xử lý upload hình ảnh và lấy URL
        String image = uploadImage(request);

        // Tạo đối tượng CanteenItem mới
        CanteenItem item = new CanteenItem(0, cinemaID, name, price, stock, status, image, TRUE); // Cập nhật constructor

        // Thêm item vào cơ sở dữ liệu
        canteenItemDAO.addCanteenItem(item);
    }

    private String uploadImage(HttpServletRequest request) throws IOException, ServletException {
        String imageURL = "";
        Part imagePart = request.getPart("imageURL"); // "imageURL" là name của input file trong form
        if (imagePart != null && imagePart.getSize() > 0) {
            // Tạo file tạm để lưu trữ ảnh
            File imageFile = File.createTempFile("canteen_item_", "_" + imagePart.getSubmittedFileName());
            imagePart.write(imageFile.getAbsolutePath());

            // Upload file lên dịch vụ lưu trữ (giả sử Cloudinary)
            FileUploader fileUploader = new FileUploader();
            imageURL = fileUploader.uploadAndReturnUrl(imageFile, "canteen_item_" + System.currentTimeMillis(), "canteen/item");

            if (imageURL.isEmpty()) {
                throw new IOException("Failed to upload image.");
            }

            System.out.println("Uploaded image URL: " + imageURL);
        }
        return imageURL;
    }

    private void updateCanteenItem(HttpServletRequest request) throws Exception {
        int itemID, cinemaID;
        try {
            itemID = Integer.parseInt(request.getParameter("canteenItemID"));
            cinemaID = Integer.parseInt(request.getParameter("cinemaID"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid ID format.");
        }

        // Lấy giá trị từ form
        String name = request.getParameter("name");
        int stock = Integer.parseInt(request.getParameter("stock"));
        String status = request.getParameter("status");

        // Nếu số lượng hàng về 0 thì tự động set thành hết hàng (status = false)
        if (stock == 0) {
            status = "false";  // Set status thành unavailable khi stock = 0
        }

        // Tạo đối tượng CanteenItem
        CanteenItem item = new CanteenItem(
                itemID,
                cinemaID,
                name,
                Float.parseFloat(request.getParameter("price")),
                stock,
                status,
                request.getParameter("imageURL"), // Có thể giữ nguyên URL hình ảnh
                Boolean.parseBoolean(status)
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
