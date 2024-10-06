/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */package model.owner;

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

    // Getters và Setters
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

    public Movie() {
    }

    public Movie(int movieID, String title, String synopsis, Date datePublished, String imageURL, float rating, String country, String linkTrailer, int cinemaID, List<Genre> genres) {
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
    }
    
}
