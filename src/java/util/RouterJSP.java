/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author Admin
 */
public class RouterJSP {

    public static String LANDING_PAGE = "/page/landingPage/LandingPage.jsp";

    <<<<<<<HEAD
    // public String HOMEPAGE = "/page/home/Home.jsp";

    =======
    // public String HOMEPAGE = "/page/home/Home.jsp";
    >>>>>>>feature/canteenCRUD
    public static String ERROR_PAGE = "/page/error/ErrorPage.jsp";

    public String REGISTER = "/page/auth/Register.jsp";

    public static String LOGIN = "/page/auth/Login.jsp";

    public String VERIFY = "/page/auth/Verify.jsp";

    public String FORGET_PASSWORD = "/page/auth/ForgetPassword.jsp";

    public String DISPLAY_INFO = "/page/user/DisplayUserInfo.jsp";

    public static String BOOKING_SEAT = "/page/cinema_room/BookingSeat.jsp";

    // for example, must replace when get real page content schedule component
    public String SCHEDULE_MOIVE = "components/schedule/Schedule.jsp";

    public String USER = "/page/landingPage/LandingPage.jsp";// templatefor testing login by role

    public String CINEMA_CHAIN_ITEM = "/page/cinemaChain/CinemaDetail.jsp";// templatefor testing login by role

    public String STAFF = "/page/staff/Staff.jsp";// templatefor testing login by role

    public String ADMIN = "/page/admin/AdminPage.jsp";// templatefor testing login by role

    public String CHANGE_PASSWORD = "/page/user/ChangePassword.jsp";

    public static String ROOM_CREAT_SEAT = "/page/owner/room/Seat.create.jsp";

    public static String VN_PAY_PAYMENT_HOMEPAGE = "/page/payment/vnpay/vnpay_pay.jsp";

    public static String PAYMENT_HOMEPAGE = "/page/payment/payment.index";

    public String MOVIE_LIST = "page/movie/MovieListComponents.jsp";

    public String MOVIE_Genre = "page/movie/FilterMovie.jsp";

    public String MOVIE_SHOW = "page/movie/MovieShow.jsp";

    public static String RETURN_TRACSACTION_BOOKING_TICKET = "/page/payment/vnpay/vnpay_return.jsp";

    public String LIST_CINEMACHAIN = "/page/admin/rooms/ListCinemaChain.jsp";

    public String LIST_CINEMA = "/page/admin/rooms/ListCinema.jsp";

    public String ADD_ROOM = "/page/owner/manage/CreateRoom.jsp";

    public String ROOM_OWNER = "/page/owner/manage/Rooms.jsp";

    public String UPDATE_ROOM = "/page/owner/manage/UpdateRoom.jsp";

    public String HOME_OWNER = "/page/owner/manage/HomeOwner.jsp"; // Update this path accordingly

    public String CREATE_CINEMACHAIN = "/page/owner/manage/CreateCinemaChain.jsp";

    public String CINEMACHAIN = "/page/owner/manage/CinemaChain.jsp";

    public String MOVIECINEMA = "/page/owner/manage/MovieCinema.jsp";

    public String CREATE_CINEMA = "/page/owner/manage/CreateCinemas.jsp";

    public String CINEMAS = "/page/owner/manage/Cinemas.jsp";

    public static String VIEW_ORDER = "/page/bill/Bill.jsp";

    public static String SCHEDULE_MOVIEe = "/page/movie/schedule/ScheduleMovie.jsp";

    public String UPDATE_CINEMACHAIN = "/page/owner/manage/UpdateCinemaChain.jsp";

    public String UPDATE_BANNER = "/page/owner/manage/UpdateBanner.jsp";

    public String UPDATE_CINEMA = "/page/owner/manage/UpdateCinemas.jsp";

    public String Room_Admin = "/page/admin/rooms/Room-Admin.jsp";

    public String UPDATE_MOVIE_LIST = "/page/admin/ListMovieAdmin.jsp";

    public String UPDATE_MOVIE = "/page/admin/EditMovieForm.jsp";

    public String SALES_REPORT = "/page/admin/Report.jsp";

    public static String DISPLAY_CINEMA_CHAINS = "/page/cinema/DisplayCinemaChains.jsp";

    public static String DISPLAY_CINEMAS = "/page/cinema/DisplayCinemas.jsp";

    public static String SEARCH_MOVIE = "/page/search/SearchMovie.jsp";

    public static String ADMIN_PAGE = "/page/admin/AdminPage.jsp";

    // public static String ORDER_ITEM ="page/ladingPage/OrderConfirmation.jsp";

    // URL JSP FILE FOR OWNER //
    public static String OWNER_COMFIRM_ORDER_PAGE = "/page/owner/confirm/ConfirmTicket.jsp";

    public static String OWNER_COMFIRM_RESULT_PAGE = "/page/owner/confirm/ConfirmResult.jsp";

    public static String OWNER_DASHBOARD_PAGE = "/page/owner/dashboard/Dashboard.jsp";

    public static String OWNER_STATISTIC_MOVIE_PAGE = "/page/owner/dashboard/movie/MovieStatistic.jsp";

    public static String OWNER_STATISTIC_CINEMA_PAGE = "/page/owner/dashboard/cinema/CinemaStatistic.jsp";

    public static String FAVOURITE_MOVIE_PAGE = "/page/movie/favouriteMovies/FavouriteMovies.jsp";

    public static String REVIEW_MOVIE_PAGE = "/page/movie/reviewMovie/ReviewMovie.jsp";

    public static String CREATE_MOVIE_SLOT = "/page/owner/movie_slot/CreateMovieSlot.jsp";

    public static String EDIT_MOVIE_SLOT = "/page/owner/movie_slot/EditMovieSlot.jsp";

    public static String OWNER_REQUEST_MOVIE = "/page/owner/createMovie/CreateMovieRequest.jsp";

    public static String ADMIN_PENDING_MOVIE_LIST = "/page/admin/PendingMovieList.jsp";

    public static String OWNER_VIEW_MOVIE_REQUEST = "/page/owner/viewMovieRequests/ViewRequests.jsp";

    public String TOP_MOVIES = "/page/movie/topMovies/TopMovie.jsp";

    public static String SHOWTIME_PAGE = "/page/showtime/showtime.jsp";

    public static String OWNER_PAGE = "/page/owner/ownerDashBoard.jsp";

    public static String OWNER_CMC = "/page/owner/createCinemaChain.jsp";

    public static String OWNER_CREATE_CINEMA_PAGE = "/page/owner/createCinemaPage.jsp";

    public static String OWNER_EDIT_CINEMA_PAGE = "/page/owner/editCinemaPage.jsp";

    public static String OWNER_MANAGE_CINEMACHAIN_PAGE = "/page/owner/manageCinemaChain.jsp";

    public static String DETAIL_MOVIE_PAGE = "/page/movie/DisplayMovieInfo.jsp";

    public static String OWNER_GENRE_FORM_PAGE = "/page/owner/genre-form.jsp";

    public static String OWNER_GENRE_LIST_PAGE = "/page/owner/genre-list.jsp";

    public static String OWNER_MOVIE_LIST_PAGE = "/page/owner/owner_movie_list.jsp";

    public static String OWNER_MANAGE_CINEMA_PAGE = "/page/owner/owner_manage_cinema.jsp";

    public static String SCHEDULE_MOVIE = "/page/schedule/showtime.jsp";

    public static String SELECT_SEAT = "/page/seatselect/selectSeat.jsp";

    public static String OWNER_MOVIE_CREATE_PAGE = "/page/owner/owner_create_movie.jsp";

    public static String OWNER_MOVIE_UPDATE_PAGE = "/page/owner/owner_update_movie.jsp";

    public static String OWNER_UPDATE_CINEMACHAIN_PAGE = "/page/owner/updateCinemaChain.jsp";

    public static String OWNER_ROOM_CREATE_PAGE = "/page/owner/room/owner_create_room.jsp";

    public static String OWNER_MANAGE_ROOM_PAGE = "/page/owner/room/owner_manage_room.jsp";

    public static String OWNER_ROOM_UPDATE_PAGE = "/page/owner/room/owner_update_room.jsp";

    public static String ORDER_DETAIL = "/page/bill/OrderDetail.jsp";

    public static String CANTEEN_ITEM_PAGE = "/page/canteen/Item.jsp";

    public String TOP_MOVIES = "/page/movie/topMovies/TopMovie.jsp";

    public static String CINEMA_DETAIL_PAGE = "/page/cinema/CinemaDetail.jsp";

    public static String CANTEEN_ITEM_UPLOAD_PAGE = "/page/owner/CanteenItemUpload.jsp";

}
