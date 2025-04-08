/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.discount;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author Kaan
 */
public class MovieDiscount {

    private int discountID;
    private String discountCode;
    private BigDecimal discountPercentage;
    private Date startDate;
    private Date endDate;
    private String status;

    public MovieDiscount() {
    }

    public MovieDiscount(int discountID, String discountCode, BigDecimal discountPercentage, Date startDate, Date endDate, String status) {
        this.discountID = discountID;
        this.discountCode = discountCode;
        this.discountPercentage = discountPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public MovieDiscount(String discountCode, BigDecimal discountPercentage, Date startDate, Date endDate, String status) {
        if (!discountCode.matches("^[A-Z0-9]{10,20}$")) {
            throw new IllegalArgumentException("Discount code must be alphanumeric and between 10 and 20 characters.");
        }
        this.discountCode = discountCode;
        this.discountPercentage = discountPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public int getDiscountID() {
        return discountID;
    }

    public void setDiscountID(int discountID) {
        this.discountID = discountID;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Method to check if the discount is expired
    public boolean isExpired() {
        return endDate != null && endDate.toLocalDate().isBefore(LocalDate.now());
    }

    @Override
    public String toString() {
        return "MovieDiscount{" + "discountID=" + discountID + ", discountCode=" + discountCode + ", discountPercentage=" + discountPercentage + ", startDate=" + startDate + ", endDate=" + endDate + ", status=" + status + '}';
    }
}
