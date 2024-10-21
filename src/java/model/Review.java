package model;

import java.time.LocalDateTime;
import java.util.Date;

public class Review {

    private int reviewID;
    private int userID;
    private int movieID;
    private int rating;
    private Date timeCreated;
    private String content;
    private String userAvatarLink;
    private String username;

    // Constructor
    public Review() {
    }

    public Review(int userID, int movieID, int rating, Date timeCreated, String content, String userAvatarLink, String username) {
        this.userID = userID;
        this.movieID = movieID;
        this.rating = rating;
        this.timeCreated = timeCreated;
        this.content = content;
        this.userAvatarLink = userAvatarLink;
        this.username = username;
    }

    public Review(int reviewID, int userID, int movieID, int rating, Date timeCreated, String content, String userAvatarLink, String username) {
        this.reviewID = reviewID;
        this.userID = userID;
        this.movieID = movieID;
        this.rating = rating;
        this.timeCreated = timeCreated;
        this.content = content;
        this.userAvatarLink = userAvatarLink;
        this.username = username;
    }

    // Getters and setters
    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserAvatarLink() {
        return userAvatarLink;
    }

    public void setUserAvatarLink(String userAvatarLink) {
        this.userAvatarLink = userAvatarLink;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Review{" + "reviewID=" + reviewID + ", userID=" + userID + ", movieID=" + movieID + ", rating=" + rating + ", timeCreated=" + timeCreated + ", content=" + content + ", userAvatarLink=" + userAvatarLink + ", username=" + username + '}';
    }
}
