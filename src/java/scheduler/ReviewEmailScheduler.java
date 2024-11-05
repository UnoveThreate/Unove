package scheduler;

import DAO.cinemaChainOwnerDAO.MovieSlotDAO;
import DAO.payment.OrderDAO;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.EmailService;

public class ReviewEmailScheduler {

    private static final Logger LOGGER = Logger.getLogger(ReviewEmailScheduler.class.getName());
    private ScheduledExecutorService scheduler;

    public ReviewEmailScheduler() {

    }

    public void startScheduler(OrderDAO orderDAO, MovieSlotDAO movieSlotDAO, EmailService emailService) {
        LOGGER.info("Starting ReviewEmailScheduler...");
        scheduler = Executors.newSingleThreadScheduledExecutor();

        // Lập lịch gửi email mỗi phút
        scheduler.scheduleAtFixedRate(() -> {
            try {
                sendReviewEmails(orderDAO, movieSlotDAO, emailService);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error occurred while sending review emails", e);
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    private void sendReviewEmails(OrderDAO orderDAO, MovieSlotDAO movieSlotDAO, EmailService emailService) throws SQLException {
        // Gọi phương thức gửi email đã có trong EmailService
        emailService.sendReviewEmails();
    }

    public void stopScheduler() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
                LOGGER.log(Level.SEVERE, "Scheduler interrupted during shutdown", e);
            }
        }
    }
}
