package model;

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


        this.title = title;
            
        this.datePublished = datePublished;
        this.rating = rating;
        this.imageURL = imageURL;
        this.synopsis = synopsis;
        this.country = country;
        this.genres = genres;
    }

    // Constructor
    public Movie(int movieID, String title, String synopsis, Date datePublished, String imageURL, float rating, String status, String country, List<String> genres) {
        this.movie

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

    public String getStatus() {

    
    public void setStatus(String status) {
        this.status = status;
    }

    // Getters and setters
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

        this.imageURL = imageURL;
    }

    public float getRating() {
        return rating;
    }


    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
}

