/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nguyendacphong
 */
public class CinemaChain {

    private int cinemaChainID;
    private int userID;
    private String name;
    private String avatarURL;
    private String information;
    private int userId;

    // Constructors
    public CinemaChain() {
    }

    public CinemaChain(int cinemaChainID, String name, String information, int userId) {
        this.cinemaChainID = cinemaChainID;
        this.name = name;
        this.information = information;
        this.userID = userId;
    }

    public CinemaChain(int userID, String name, String information) {
        this.userID = userID;
        this.name = name;
        this.information = information;
    }

    public CinemaChain(int cinemaChainID, String name, String information, int userId, String avatarURL) {
        this.userID = userId;
        this.cinemaChainID = cinemaChainID;
        this.name = name;
        this.information = information;
        this.avatarURL = avatarURL;
    }

    public int getCinemaChainID() {
        return cinemaChainID;
    }

    public void setCinemaChainID(int cinemaChainID) {
        this.cinemaChainID = cinemaChainID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public String toString() {
        return "CinemaChain{" + "cinemaChainID=" + cinemaChainID + ", name=" + name + ", information=" + information
                + ", userId=" + userId + ", avatarURL=" + avatarURL + '}';
    }

}
