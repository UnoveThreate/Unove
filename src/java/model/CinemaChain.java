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
    private int userId;
    // Constructors
    public CinemaChain() {}

    public CinemaChain(int cinemaChainID, String name, String information, int userId) {
        this.cinemaChainID = cinemaChainID;
        this.name = name;
        this.information = information;
        this.userId = userId;
    }
        public CinemaChain(int userID, String name, String information) {
        this.userId = userId;
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
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CinemaChain{" + "cinemaChainID=" + cinemaChainID + ", name=" + name + ", information=" + information + '}';
    }


}
