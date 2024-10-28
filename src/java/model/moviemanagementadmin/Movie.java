package model.moviemanagementadmin;

import java.text.SimpleDateFormat;
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
    private String type;
    private boolean status;
    private List<String> genres;
     private String cinemaName;

    public Movie() {
    }

    public Movie(String title, String synopsis, Date datePublished, String imageURL, float rating, String country,
            String linkTrailer, int cinemaID, String type, boolean status, List<String> genres) {
        this.title = title;
        this.synopsis = synopsis;
        this.datePublished = datePublished;
        this.imageURL = imageURL;
        this.rating = rating;
        this.country = country;
        this.linkTrailer = linkTrailer;
        this.cinemaID = cinemaID;
        this.type = type;
        this.status = status;
        this.genres = genres;
    }

    // Constructor với movieID
    public Movie(int movieID, String title, String synopsis, Date datePublished, String imageURL, float rating,
            String country, String linkTrailer, int cinemaID, String type, boolean status, List<String> genres) {
        this.movieID = movieID;
        this.title = title;
        this.synopsis = synopsis;
        this.datePublished = datePublished;
        this.imageURL = imageURL;
        this.rating = rating;
        this.country = country;
        this.linkTrailer = linkTrailer;
        this.cinemaID = cinemaID;
        this.type = type;
        this.status = status;
        this.genres = genres;
    }
    
    // Lấy ra chuỗi các thể loại
    public String getGenresAsString() {
        return String.join(", ", genres);
    }

    // Getters and Setters

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

    public void setDatePublished(String datePublished) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.datePublished = (datePublished != null) ? formatter.parse(datePublished) : null;
        } catch (Exception e) {
            this.datePublished = null;
        }
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

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
     public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieID=" + movieID +
                ", title='" + title + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", datePublished=" + datePublished +
                ", imageURL='" + imageURL + '\'' +
                ", rating=" + rating +
                ", country='" + country + '\'' +
                ", linkTrailer='" + linkTrailer + '\'' +
                ", cinemaID=" + cinemaID +
                ", type='" + type + '\'' +
                ", status=" + status +
                ", genres=" + genres +
                '}';
    }
}