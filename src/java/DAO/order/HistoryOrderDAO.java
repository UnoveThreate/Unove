package DAO.order;

import database.MySQLConnect;
import jakarta.servlet.ServletContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryOrderDAO extends MySQLConnect {

    public HistoryOrderDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    /**
     * Lấy lịch sử đơn hàng của người dùng với trạng thái thành công,
     * bao gồm thông tin về các mặt hàng canteen.
     *
     * @param userId ID của người dùng.
     * @return Danh sách các lịch sử đơn hàng dưới dạng HashMap.
     * @throws SQLException nếu có lỗi xảy ra khi truy vấn cơ sở dữ liệu.
     */
    public List<HashMap<String, Object>> getHistoryOrdersByUserId(int userId) throws SQLException {
        String query = """
          SELECT 
                o.OrderID,
                o.MovieSlotID,
                m.Title AS MovieTitle,
                ms.StartTime AS ShowTime,
                (ms.Price - COALESCE(ms.Discount, 0)) AS TotalPrice,
                ci.CanteenItemID,
                ci.Name AS CanteenItemName,
                oci.Quantity
            FROM 
                `Order` o
            JOIN 
                MovieSlot ms ON o.MovieSlotID = ms.MovieSlotID
            JOIN 
                Movie m ON ms.MovieID = m.MovieID
            LEFT JOIN 
                OrderCanteenItem oci ON o.OrderID = oci.OrderID
            LEFT JOIN 
                CanteenItem ci ON oci.CanteenItemID = ci.CanteenItemID
            WHERE 
                o.UserID = ?
                AND o.Status = 'success';
            """;

        List<HashMap<String, Object>> historyOrders = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                HashMap<Integer, HashMap<String, Object>> orderMap = new HashMap<>();

                while (resultSet.next()) {
                    int orderId = resultSet.getInt("OrderID");

                    // Kiểm tra xem order đã có trong orderMap chưa
                    HashMap<String, Object> orderDetails = orderMap.getOrDefault(orderId, new HashMap<>());

                    if (!orderMap.containsKey(orderId)) {
                        // Nếu chưa có, khởi tạo và thêm thông tin chính của đơn hàng
                        orderDetails.put("OrderID", orderId);
                        orderDetails.put("MovieSlotID", resultSet.getInt("MovieSlotID"));
                        orderDetails.put("MovieTitle", resultSet.getString("MovieTitle"));
                        orderDetails.put("ShowTime", resultSet.getTimestamp("ShowTime"));
                        orderDetails.put("TotalPrice", resultSet.getFloat("TotalPrice"));
                        orderDetails.put("Status", "success");
                        orderDetails.put("CanteenItems", new ArrayList<HashMap<String, Object>>());

                        // Lưu vào orderMap để theo dõi
                        orderMap.put(orderId, orderDetails);
                        historyOrders.add(orderDetails);
                    }

                    // Thêm mục canteen (nếu có) vào danh sách
                    Integer canteenItemId = resultSet.getInt("CanteenItemID");
                    if (canteenItemId != 0) {  // Kiểm tra mục canteen có tồn tại
                        HashMap<String, Object> canteenItem = new HashMap<>();
                        canteenItem.put("CanteenItemID", canteenItemId);
                        canteenItem.put("CanteenItemName", resultSet.getString("CanteenItemName"));
                        canteenItem.put("Quantity", resultSet.getInt("Quantity"));

                        // Thêm mục canteen vào danh sách trong orderDetails
                        List<HashMap<String, Object>> canteenItems = 
                            (List<HashMap<String, Object>>) orderDetails.get("CanteenItems");
                        canteenItems.add(canteenItem);
                    }
                }
            }
        }

        System.out.println("Total orders fetched for user " + userId + ": " + historyOrders.size());
        return historyOrders;
    }
}