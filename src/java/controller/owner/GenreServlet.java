/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import DAO.cinemaChainOwnerDAO.GenreDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.owner.Genre;
import util.Role;
import util.RouterJSP;
import util.RouterURL;

@WebServlet("/owner/genre")
public class GenreServlet extends HttpServlet {

    private GenreDAO genreDAO;

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            genreDAO = new GenreDAO((ServletContext) getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(GenreServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "create":
                    showCreateForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteGenre(request, response);
                    break;
                default:
                    listGenres(request, response);
                    break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenreServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect(RouterURL.ERROR_PAGE);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String genreIDStr = request.getParameter("genreID");
        String genreName = request.getParameter("genreName");

        if (genreName == null || genreName.trim().isEmpty()) {
            // Handle validation error
            request.setAttribute("error", "Genre name cannot be empty.");
            request.getRequestDispatcher(RouterJSP.OWNER_GENRE_FORM_PAGE).forward(request, response);
            return;
        }

        try {
            if (genreIDStr == null || genreIDStr.isEmpty()) {
                // Create new Genre
                Genre newGenre = new Genre();
                newGenre.setGenreName(genreName);
                genreDAO.createGenre(newGenre);
            } else {
                // Update existing Genre
                int genreID = Integer.parseInt(genreIDStr);
                Genre existingGenre = genreDAO.getGenreById(genreID);
                if (existingGenre != null) {
                    existingGenre.setGenreName(genreName);
                    genreDAO.updateGenre(existingGenre);
                } else {
                    request.setAttribute("error", "Genre not found.");
                    request.getRequestDispatcher(RouterJSP.OWNER_GENRE_FORM_PAGE).forward(request, response);
                    return;
                }
            }
            response.sendRedirect(RouterURL.MANAGE_GENRES);  // Redirect to genre list after successful operation
        } catch (SQLException e) {
            Logger.getLogger(GenreServlet.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect(RouterURL.ERROR_PAGE);
        }
    }

    private void listGenres(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Genre> genres = genreDAO.getAllGenres();
        request.setAttribute("genres", genres);
        request.getRequestDispatcher(RouterJSP.OWNER_GENRE_LIST_PAGE).forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("genre", null); // No genre data for create
        request.getRequestDispatcher(RouterJSP.OWNER_GENRE_FORM_PAGE).forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String genreIDStr = request.getParameter("id");
        if (genreIDStr == null || genreIDStr.isEmpty()) {
            response.sendRedirect(RouterURL.MANAGE_GENRES);
            return;
        }

        int genreID = Integer.parseInt(genreIDStr);
        Genre genre = genreDAO.getGenreById(genreID);
        if (genre == null) {
            request.setAttribute("error", "Genre not found.");
            request.getRequestDispatcher(RouterJSP.OWNER_GENRE_LIST_PAGE).forward(request, response);
            return;
        }

        request.setAttribute("genre", genre);
        request.getRequestDispatcher(RouterJSP.OWNER_GENRE_FORM_PAGE).forward(request, response);
    }

    private void deleteGenre(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String genreIDStr = request.getParameter("id");
        if (genreIDStr == null || genreIDStr.isEmpty()) {
            response.sendRedirect(RouterURL.MANAGE_GENRES);
            return;
        }

        int genreID = Integer.parseInt(genreIDStr);
        boolean success = genreDAO.deleteGenre(genreID);
        if (!success) {
            request.setAttribute("error", "Unable to delete genre. It might be associated with existing movies.");
            listGenres(request, response);
            return;
        }

        response.sendRedirect(RouterURL.MANAGE_GENRES);
    }
}
