package model.owner;

import java.util.Date;
import java.util.List;

public class Movie {
    private int movieID;
    private String title;
    private String synopsis;
    private Date datePublished;
    private String imageURL;
    private float rating;
    private String country;
    private String linkTrailer;
    private int cinemaID;
    private List<Genre> genres; // Danh sách các genre
    private String type; // Phim đang chiếu hoặc sắp chiếu
    private boolean status; // Trạng thái của phim (FALSE: ẩn, TRUE: hiện)

    // Constructor mặc định
    public Movie() {
    }

    // Constructor đầy đủ
    public Movie(int movieID, String title, String synopsis, Date datePublished, String imageURL, float rating, String country, String linkTrailer, int cinemaID, List<Genre> genres, String type, boolean status) {
        this.movieID = movieID;
        this.title = title;
        this.synopsis = synopsis;
        this.datePublished = datePublished;
        this.imageURL = imageURL;
        this.rating = rating;
        this.country = country;
        this.linkTrailer = linkTrailer;
        this.cinemaID = cinemaID;
        this.genres = genres;
        this.type = type;
        this.status = status;
    }

    // Getters và Setters cho tất cả các thuộc tính
    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLinkTrailer() {
        return linkTrailer;
    }

    public void setLinkTrailer(String linkTrailer) {
        this.linkTrailer = linkTrailer;
    }

    public int getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(int cinemaID) {
        this.cinemaID = cinemaID;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Movie{" + "movieID=" + movieID + ", title=" + title + ", synopsis=" + synopsis + ", datePublished=" + datePublished + ", imageURL=" + imageURL + ", rating=" + rating + ", country=" + country + ", linkTrailer=" + linkTrailer + ", cinemaID=" + cinemaID + ", genres=" + genres + ", type=" + type + ", status=" + status + '}';
    }
    
    
}
