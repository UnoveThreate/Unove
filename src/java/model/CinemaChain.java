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
    private String name;
    private String information;
    private int userID;
    private String avatarURL;

    // Constructors
    public CinemaChain() {
    }

    public CinemaChain(int cinemaChainID, String name, String information, int userId, String avatarURL) {
        this.cinemaChainID = cinemaChainID;
        this.name = name;
        this.information = information;
        this.userID = userId;
        this.avatarURL = avatarURL;
    }

    public CinemaChain(int userID, String name, String information) {
        this.userID = userID;
        this.name = name;
        this.information = information;
    }

    public int getCinemaChainID() {
        return cinemaChainID;
    }

    public void setCinemaChainID(int cinemaChainID) {
        this.cinemaChainID = cinemaChainID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getUserId() {
        return userID;
    }

    public void setUserId(int userId) {
        this.userID = userId;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarLink(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    @Override
    public String toString() {
        return "CinemaChain{" + "cinemaChainID=" + cinemaChainID + ", name=" + name + ", information=" + information + '}';
    }

}
