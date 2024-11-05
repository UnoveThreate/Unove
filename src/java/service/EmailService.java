package service;

import DAO.cinemaChainOwnerDAO.MovieSlotDAO;
import DAO.payment.OrderDAO;
import DAO.review.MovieReviewDAO;
import DAO.ticket.TicketDAO;
import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import model.owner.MovieSlot;
import model.Order;
import model.Seat;
import model.Ticket;
import model.User;
import model.ticket.TicketOrderDetails;
import util.Config;

/**
 * Sends an email to the user.
 */
public class EmailService {

    private TicketDAO ticketDAO;
    private OrderDAO orderDAO;
    private MovieSlotDAO movieSlotDAO;
    private MovieReviewDAO movieReviewDAO;

    public EmailService() {
    }

    public EmailService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public EmailService(OrderDAO orderDAO, MovieSlotDAO movieSlotDAO, MovieReviewDAO movieReviewDAO) {
        this.orderDAO = orderDAO;
        this.movieSlotDAO = movieSlotDAO;
        this.movieReviewDAO = movieReviewDAO;
    }

    public boolean sendEmail(String email, String code) {
        return sendEmail(new User(email, code));
    }

    public boolean sendEmailActiveOrder(String email, String code, String QRCodeUrl) {
        boolean test = false;

        String toEmail = email;
        String fromEmail = "unovecinema@gmail.com";  // your email
        String password = "dhbx pdei viuc lpwt";  // your app password
        String logoUrl = "https://res.cloudinary.com/dsvllb1am/image/upload/v1718269790/sgvvasrlc3tisefkq92j.png";  // URL to your logo

        try {
            Properties pr = new Properties();
            pr.setProperty("mail.smtp.host", "smtp.gmail.com");
            pr.setProperty("mail.smtp.port", "587");
            pr.setProperty("mail.smtp.auth", "true");
            pr.setProperty("mail.smtp.starttls.enable", "true");

            // SSL properties should not be used when using STARTTLS
            pr.remove("mail.smtp.ssl.enable");
            pr.remove("mail.smtp.socketFactory.port");
            pr.remove("mail.smtp.socketFactory.class");

            // Get session
            Session session = Session.getInstance(pr, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

            Message mess = new MimeMessage(session);
            mess.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            mess.setFrom(new InternetAddress(fromEmail));
            mess.setSubject("Confirm Ticket");

            // HTML content
            String htmlContent = "<html>"
                    + "<body style='font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4;'>"
                    + "<table align='center' width='600' style='border-collapse: collapse; margin: 20px auto; background: #fff; padding: 20px; border: 1px solid #ddd; border-radius: 10px;'>"
                    + "    <tr>"
                    + "        <td align='center' style='padding: 20px 0;'>"
                    + "            <img src='" + logoUrl + "' alt='Your Company Logo' style='height: 50px;'>"
                    + "        </td>"
                    + "    </tr>"
                    + "    <tr>"
                    + "        <td style='padding: 20px;'>"
                    + "            <h2 style='color: #333; text-align: center;'>Order Confirmation</h2>"
                    + "            <p style='color: #666; text-align: center;'> Thank you for your order. Please confirm your order using the code or QR code below when you arrive at the theater. </p>"
                    + "            <div style='text-align: center; margin: 20px 0;'>"
                    + "                <a href='#' style='background-color: #0066cc; color: #fff; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block; font-size: 18px;'>"
                    + code + "</a>"
                    + "            </div>"
                    + "            <div style='text-align: center; margin: 20px 0;'>"
                    + "                <img src='" + QRCodeUrl + "' alt='QR Code' style='height: 150px;'>"
                    + "            </div>"
                    + "            <p style='color: #666; text-align: center;'>If you did not place this order, please ignore this email.</p>"
                    + "            <p style='color: #666; text-align: center;'>Best regards,<br>Your Company</p>"
                    + "        </td>"
                    + "    </tr>"
                    + "    <tr>"
                    + "        <td align='center' style='padding: 20px; font-size: 12px; color: #999;'>"
                    + "            <p>&copy; 2024 CinePa. All rights reserved.</p>"
                    + "            <p>CinePa, [12A Street], Danang, 43</p>"
                    + "        </td>"
                    + "    </tr>"
                    + "</table>"
                    + "</body>"
                    + "</html>";

            mess.setContent(htmlContent, "text/html");

            Transport.send(mess);
            test = true;

        } catch (MessagingException e) {
            e.printStackTrace();  // General messaging error
        } catch (Exception e) {
            e.printStackTrace();  // Any other exception
        }
        return test;
    }

    public boolean sendEmail(User user) {

        boolean isSucess = false;

        String toEmail = user.getEmail();
        String fromEmail = "dacphong2092003@gmail.com";  // your email
        String password = "cbki yoeg hoqh usiq";  // your app password
        String logoUrl = "https://res.cloudinary.com/dsvllb1am/image/upload/v1718269790/sgvvasrlc3tisefkq92j.png";  // URL to your logo

        try {
            Properties pr = new Properties();
            pr.setProperty("mail.smtp.host", "smtp.gmail.com");
            pr.setProperty("mail.smtp.port", "587");
            pr.setProperty("mail.smtp.auth", "true");
            pr.setProperty("mail.smtp.starttls.enable", "true");

            // SSL properties should not be used when using STARTTLS
            pr.remove("mail.smtp.ssl.enable");
            pr.remove("mail.smtp.socketFactory.port");
            pr.remove("mail.smtp.socketFactory.class");

            // Get session
            Session session = Session.getInstance(pr, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

            Message mess = new MimeMessage(session);
            mess.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            mess.setFrom(new InternetAddress(fromEmail));
            mess.setSubject("Email Verification");

            // HTML content
            String htmlContent = "<html>"
                    + "<body style='font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4;'>"
                    + "<table align='center' width='600' style='border-collapse: collapse; margin: 20px auto; background: #fff; padding: 20px; border: 1px solid #ddd; border-radius: 10px;'>"
                    + "    <tr>"
                    + "        <td align='center' style='padding: 20px 0;'>"
                    + "            <img src='" + logoUrl + "' alt='Your Company Logo' style='height: 50px;'>"
                    + "        </td>"
                    + "    </tr>"
                    + "    <tr>"
                    + "        <td style='padding: 20px;'>"
                    + "            <h2 style='color: #333; text-align: center;'>Register Confirmation</h2>"
                    + "            <p style='color: #666; text-align: center;'>Thank you for registering. Please verify your account using the code below:</p>"
                    + "            <div style='text-align: center; margin: 20px 0;'>"
                    + "                <a href='#' style='background-color: #0066cc; color: #fff; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block; font-size: 18px;'>"
                    + user.getCode() + "</a>"
                    + "            </div>"
                    + "            <p style='color: #666; text-align: center;'>If you did not register for this account, please ignore this email.</p>"
                    + "            <p style='color: #666; text-align: center;'>Best regards,<br>Your Company</p>"
                    + "        </td>"
                    + "    </tr>"
                    + "    <tr>"
                    + "        <td align='center' style='padding: 20px; font-size: 12px; color: #999;'>"
                    + "            <p>&copy; 2024 CinePa. All rights reserved.</p>"
                    + "            <p>CinePa, [123 Your Street], Danang, 43</p>"
                    + "        </td>"
                    + "    </tr>"
                    + "</table>"
                    + "</body>"
                    + "</html>";

            mess.setContent(htmlContent, "text/html");

            Transport.send(mess);
            isSucess = true;

        } catch (MessagingException e) {
            e.printStackTrace();  // General messaging error
        } catch (Exception e) {
            e.printStackTrace();  // Any other exception
        }
        return isSucess;
    }

    public boolean sendEmailTicketOrder(String email, String code, String qrCode, int orderID) {
        boolean isEmailSent = false;
        TicketOrderDetails ticketOrderDetails = ticketDAO.getTicketOrderDetailsByOrderID(orderID);

        // Thiết lập thông tin email
        String subject = "Thông tin vé xem phim tại hệ thống rạp phim";

        // Tạo nội dung email
        StringBuilder body = new StringBuilder();
        body.append("<html>");
        body.append("<head><title>Xác Nhận Đặt Vé</title></head>");
        body.append("<body style='font-family: Arial, sans-serif; color: #333;'>");
        body.append("<div style='text-align: center; background-color: #f8f9fa; padding: 20px;'>");
        body.append("<h1 style='color: #333; font-size: 28px; margin-bottom: 0;'>UNOVE CINEMAS</h1>");
        body.append("<p style='color: #666; font-size: 16px; margin-top: 5px;'>Your Best Movie Experience</p>");
        body.append("</div>");
        body.append("<div style='text-align: center; padding: 10px;'>");
        body.append("<hr style='border: 0; border-top: 1px solid #ddd;'>");
        body.append("<h2 style='color: #555;'>XÁC NHẬN ĐẶT VÉ THÀNH CÔNG</h2>");
        body.append("</div>");

// Khởi tạo các tham số cho đơn hàng
        body.append("<div style='margin: 0 auto; padding: 20px; max-width: 600px;'>");
        body.append("<p style='font-size: 16px;'><strong>Mã đơn hàng:</strong> ").append(ticketOrderDetails.getOrderID()).append("</p>");
        body.append("<p style='font-size: 16px;'><strong>Phim:</strong> ").append(ticketOrderDetails.getTitle()).append("</p>");
        body.append("<p style='font-size: 16px;'><strong>Ngày đặt:</strong> ").append(ticketOrderDetails.getTimeCreated()).append("</p>");

// Danh sách vé
        body.append("<h3 style='margin-top: 20px; color: #444;'>Danh Sách Vé</h3>");
        body.append("<table style='width: 100%; border-collapse: collapse; margin-top: 10px;'>");
        body.append("<thead>");
        body.append("<tr style='background-color: #f0f0f0;'>");
        for (Seat seat : ticketOrderDetails.getSeats()) {
            body.append("<th style='border: 1px solid #ddd; padding: 10px;'>Ghế: ").append(seat.getName()).append("</th>");
        }
        body.append("</tr>");
        body.append("</thead>");
        body.append("<tbody>");
        body.append("<tr>");
        body.append("<td style='border: 1px solid #ddd; padding: 10px; text-align: center;'>Mã Vé: ").append(ticketOrderDetails.getCode()).append("</td>");
        body.append("</tr>");
        body.append("<tr>");
        body.append("<td style='border: 1px solid #ddd; padding: 10px; text-align: center;'>Trạng Thái: ").append(ticketOrderDetails.getOrderStatus()).append("</td>");
        body.append("</tr>");
        body.append("</tbody>");
        body.append("</table>");

        body.append("<div style='text-align: center; margin-top: 20px;'>");
        body.append("<p style='margin: 10px 0;'><strong>Mã QR:</strong></p>");
        body.append("<img src='").append(qrCode).append("' alt='QR Code' style='width: 150px; height: 150px;'>");
        body.append("</div>");

        body.append("<div style='text-align: center; color: #888; margin-top: 30px; font-size: 14px;'>");
        body.append("<p>Đây là email tự động. Quý khách vui lòng không trả lời email này.</p>");
        body.append("</div>");

        body.append("</div>");
        body.append("</body></html>");

        // Cấu hình gửi email
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Địa chỉ máy chủ SMTP
        props.put("mail.smtp.port", "587"); // Cổng SMTP

        // Thông tin xác thực
        String username = "unovecinema@gmail.com"; // Thay bằng email của bạn
        String password = "dhbx pdei viuc lpwt"; // Thay bằng mật khẩu của bạn

        Session session = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            message.setContent(body.toString(), "text/html; charset=UTF-8");

            // Gửi email
            Transport.send(message);
            isEmailSent = true; // Email đã được gửi thành công
        } catch (MessagingException e) {
            e.printStackTrace(); // In lỗi nếu có
        }
        return isEmailSent; // Trả về kết quả
    }
    // Method to send review request emails

    public void sendReviewEmails() throws SQLException {
        List<Order> confirmedOrders = movieReviewDAO.getConfirmedOrders();
        System.out.println("Confirmed Orders Count: " + confirmedOrders.size());
        for (Order order : confirmedOrders) {
            MovieSlot movieSlot = movieSlotDAO.getMovieSlotById(order.getMovieSlotID());
            System.out.println("Processing Order ID: " + order.getOrderID() + " with MovieSlot ID: " + order.getMovieSlotID());

            if (movieSlot.getEndTime().isBefore(LocalDateTime.now())) {
                int userID = order.getUserID();
                int movieID = movieSlot.getMovieID();

                System.out.println("User ID: " + userID + ", Movie ID: " + movieID);
                boolean canReview = movieReviewDAO.canReview(userID, movieID);
                boolean hasReviewed = movieReviewDAO.hasReviewed(userID, movieID);
                System.out.println("Can Review: " + canReview + ", Has Reviewed: " + hasReviewed);

                if (canReview && !hasReviewed) {
                    String email = movieReviewDAO.getUserEmail(userID);
                    if (email != null && !email.isEmpty()) {
                        sendReviewRequestEmail(email, movieID);
                        System.out.println("Email sent to user " + userID + " for movieID " + movieID);

                        // Mark email as sent
                        movieReviewDAO.markEmailSent(userID, movieID);
                    } else {
                        System.out.println("Email is null or empty for User ID: " + userID);
                    }
                }
            }
        }
    }

    // Method to send a review request email
    public void sendReviewRequestEmail(String toEmail, int movieID) throws SQLException  {
        
        String fromEmail = "dacphong2092003@gmail.com";  // your email
        String password = "cbki yoeg hoqh usiq";  // your app password
        String subject = "Yêu cầu đánh giá phim";
        String reviewLink = Config.DOMAIN+"/Unove/movie/reviewMovie?movieID=" + movieID;
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create a session
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            // Create a MimeMessage
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText("Cảm ơn bạn đã xem phim. Hãy đánh giá bộ phim tại đây: " + reviewLink);

            // Send the email
            Transport.send(message);
            System.out.println("Email đã được gửi thành công!");
        } catch (MessagingException e) {
            e.printStackTrace(); // Print any messaging errors
        }
    }
}
