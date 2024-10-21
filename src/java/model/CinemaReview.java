/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Kaan
 */
public class CinemaReview {

    private int cinemaReviewID;
    private int userID;
    private int cinemaID;
    private int rating;
    private Date timeCreated;
    private String content;

    public CinemaReview(int cinemaReviewID, int userID, int cinemaID, int rating, Date timeCreated, String content) {
        this.cinemaReviewID = cinemaReviewID;
        this.userID = userID;
        this.cinemaID = cinemaID;
        this.rating = rating;
        this.timeCreated = timeCreated;
        this.content = content;
    }

    public int getCinemaReviewID() {
        return cinemaReviewID;
    }

    public int getUserID() {
        return userID;
    }

    public int getCinemaID() {
        return cinemaID;
    }

    public int getRating() {
        return rating;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "CinemaReview{" + "cinemaReviewID=" + cinemaReviewID + ", userID=" + userID + ", cinemaID=" + cinemaID + ", rating=" + rating + ", timeCreated=" + timeCreated + ", content=" + content + '}';
    }
}
