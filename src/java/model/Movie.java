package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Movie {

    private int movieID;
    private int cinemaID;
    private String title;
    private Date datePublished;
    private float rating;
    private String imageURL;
    private String synopsis;
    private String country;
    private String linkTrailer;
    private List<String> genres;
    private String status;

    public Movie() {
    }

    public Movie(String title, String synopsis, Date datePublished, String imageURL, float rating, String country,
            List<String> genres) {
        this.title = title;
        this.datePublished = datePublished;
        this.rating = rating;
        this.imageURL = imageURL;
        this.synopsis = synopsis;
        this.country = country;
        this.genres = genres;
    }

    // Constructor
    public Movie(int movieID, String title, String synopsis, Date datePublished, String imageURL, float rating,
            String status, String country, List<String> genres) {
        this.movieID = movieID;
        this.datePublished = datePublished;
        this.rating = rating;
        this.imageURL = imageURL;
        this.synopsis = synopsis;
        this.country = country;
        this.status = status;
        this.genres = genres;
    }

    // lay ra chuoi cac the loai :
    public String getGenresAsString() {
        return String.join(", ", genres);
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public int getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(int cinemaID) {
        this.cinemaID = cinemaID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    public void setDatePublished(String datePublished) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// change format date for suitable
        try {
            this.datePublished = (datePublished != null) ? formatter.parse(datePublished) : null;
        } catch (Exception e) {
            this.datePublished = null;
        }

    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;

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

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Movie{" + "movieID=" + movieID + ", title=" + title + ", synopsis=" + synopsis + ", datePublished="
                + datePublished + ", imageURL=" + imageURL + ", rating=" + rating + ", country=" + country
                + ", linkTrailer=" + linkTrailer + ", cinemaID=" + cinemaID + '}';
    }

}
