package controller.schedule;

import dao.CinemaChainDAO;
import dao.CinemaDAO;
import dao.MovieDAO;
import dao.MovieSlotDAO;
import model.CinemaChain;
import model.Cinema;
import model.Movie;
import model.MovieSlot;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/showtimes")
public class ShowtimeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CinemaChainDAO cinemaChainDAO = new CinemaChainDAO();
        CinemaDAO cinemaDAO = new CinemaDAO();
        MovieDAO movieDAO = new MovieDAO();
        MovieSlotDAO movieSlotDAO = new MovieSlotDAO();
        
        // Lấy danh sách chuỗi rạp
        List<CinemaChain> cinemaChains = cinemaChainDAO.getAllCinemaChains();
        request.setAttribute("cinemaChains", cinemaChains);
        
        // Xác định chuỗi rạp được chọn
        int cinemaChainID = Integer.parseInt(request.getParameter("cinemaChainID") != null ?
                            request.getParameter("cinemaChainID") : 
                            String.valueOf(cinemaChains.get(0).getCinemaChainID()));
        
        // Lấy danh sách rạp thuộc chuỗi rạp đã chọn
        List<Cinema> cinemas = cinemaDAO.getCinemasByChain(cinemaChainID);
        request.setAttribute("cinemas", cinemas);
        
        // Xác định rạp được chọn
        int cinemaID = Integer.parseInt(request.getParameter("cinemaID") != null ? 
                       request.getParameter("cinemaID") : 
                       String.valueOf(cinemas.get(0).getCinemaID()));
        
        // Lấy thông tin chi tiết của rạp được chọn
        Cinema selectedCinema = cinemaDAO.getCinemaById(cinemaID);
        request.setAttribute("selectedCinema", selectedCinema);
        
        // Lấy danh sách ngày có suất chiếu
        List<LocalDate> availableDates = movieSlotDAO.getAvailableDates(cinemaID);
        request.setAttribute("availableDates", availableDates);
        
        // Xác định ngày được chọn
        LocalDate selectedDate = LocalDate.parse(request.getParameter("date") != null ? 
                                 request.getParameter("date") : 
                                 availableDates.get(0).toString());
        
        // Lấy danh sách phim đang chiếu tại rạp
        List<Movie> movies = movieDAO.getMoviesByCinema(cinemaID);
        
        // Lấy danh sách suất chiếu theo rạp và ngày
        List<MovieSlot> movieSlots = movieSlotDAO.getMovieSlotsByCinemaAndDate(cinemaID, selectedDate);
        
        // Nhóm các suất chiếu theo phim
        Map<Movie, List<MovieSlot>> movieSlotsByMovie = movieSlots.stream()
                .collect(Collectors.groupingBy(movieSlot -> movies.stream()
                        .filter(movie -> movie.getMovieID() == movieSlot.getMovieID())
                        .findFirst()
                        .orElse(null)));
        
        request.setAttribute("selectedCinemaChainID", cinemaChainID);
        request.setAttribute("selectedCinemaID", cinemaID);
        request.setAttribute("selectedDate", selectedDate);
        request.setAttribute("movies", movies);
        request.setAttribute("movieSlotsByMovie", movieSlotsByMovie);
        
        request.getRequestDispatcher("/pag/showtime.jsp").forward(request, response);
    }
}