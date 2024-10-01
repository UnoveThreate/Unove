/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Kaan
 */
public class Cinema {

    private int cinemaID;
    private int cinemaChainID;
    private String address;
    private String province;
    private String district;
    private String commune;

    public Cinema(int cinemaID, int cinemaChainID, String address, String province, String district, String commune) {
        this.cinemaID = cinemaID;
        this.cinemaChainID = cinemaChainID;
        this.address = address;
        this.province = province;
        this.district = district;
        this.commune = commune;
    }

    public Cinema() {
    }

    public int getCinemaID() {
        return cinemaID;
    }

    public int getCinemaChainID() {
        return cinemaChainID;
    }

    public String getAddress() {
        return address;
    }

    public String getProvince() {
        return province;
    }

    public String getDistrict() {
        return district;
    }

    public String getCommune() {
        return commune;
    }

    public void setCinemaID(int cinemaID) {
        this.cinemaID = cinemaID;
    }

    public void setCinemaChainID(int cinemaChainID) {
        this.cinemaChainID = cinemaChainID;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    @Override
    public String toString() {
        return "Cinema{" + "cinemaID=" + cinemaID + ", cinemaChainID=" + cinemaChainID + ", address=" + address + ", province=" + province + ", district=" + district + ", commune=" + commune + '}';
    }
}
