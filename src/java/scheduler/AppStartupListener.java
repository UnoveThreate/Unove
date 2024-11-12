/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/ServletListener.java to edit this template
 */
package scheduler;

/**
 * Web application lifecycle listener.
 *
 * @author nguyendacphong
 */
import DAO.cinemaChainOwnerDAO.MovieSlotDAO;
import DAO.payment.OrderDAO;
import DAO.review.MovieReviewDAO;
import jakarta.servlet.ServletContext;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.EmailService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = (ServletContext) sce.getServletContext();

        OrderDAO orderDAO = null;
        try {
            orderDAO = new OrderDAO(context);
        } catch (Exception ex) {
            Logger.getLogger(AppStartupListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        MovieSlotDAO movieSlotDAO = null;
        try {
            movieSlotDAO = new MovieSlotDAO(context);
        } catch (Exception ex) {
            Logger.getLogger(AppStartupListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        MovieReviewDAO movieReviewDAO = null;
        try {
            movieReviewDAO = new MovieReviewDAO(context);
        } catch (Exception ex) {
            Logger.getLogger(AppStartupListener.class.getName()).log(Level.SEVERE, null, ex);
        }

        EmailService emailService = new EmailService(orderDAO, movieSlotDAO, movieReviewDAO);

        // Lưu vào ServletContext
        context.setAttribute("orderDAO", orderDAO);
        context.setAttribute("movieSlotDAO", movieSlotDAO);
        context.setAttribute("movieReviewDAO", movieReviewDAO);
        context.setAttribute("emailService", emailService);

        // Khởi chạy ReviewEmailScheduler
        ReviewEmailScheduler scheduler = new ReviewEmailScheduler();
        scheduler.startScheduler(orderDAO, movieSlotDAO, emailService);

        // Lưu scheduler để có thể dừng khi ứng dụng ngừng hoạt động
        context.setAttribute("reviewEmailScheduler", scheduler);
        Logger.getLogger(AppStartupListener.class.getName()).log(Level.INFO, "Application is starting...");
        System.out.println("AppStartupListener initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Dừng scheduler khi ứng dụng ngừng hoạt động
        ReviewEmailScheduler scheduler = (ReviewEmailScheduler) sce.getServletContext().getAttribute("reviewEmailScheduler");
        if (scheduler != null) {
            scheduler.stopScheduler();
        }
        Logger.getLogger(AppStartupListener.class.getName()).log(Level.INFO, "Application is stopping...");
    }
}
