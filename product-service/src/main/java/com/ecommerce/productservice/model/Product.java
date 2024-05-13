package com.ecommerce.productservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;
    @Column(name = "product_name")
    private String productName;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn (name = "category_id")
    @JsonIgnore
    private Category category;
    @Column(name = "price")
    private float priceToday;
    @Column(name = "availability")
    private String availabilityStatus;

    @Column(name = "count")
    private int quantity;
    @Column(name = "status")
    private String approvalStatus;

    public Product(){

    }

    public Product(String productName, float priceToday, String availabilityStatus, int quantity, String approvalStatus) {
        this.productName = productName;
        this.priceToday = priceToday;
        this.availabilityStatus = availabilityStatus;
        this.quantity = quantity;
        this.approvalStatus = approvalStatus;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public float getPriceToday() {
        return priceToday;
    }

    public void setPriceToday(float priceToday) {
        this.priceToday = priceToday;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }



    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", category=" + category +
                ", priceToday=" + priceToday +
                ", availabilityStatus='" + availabilityStatus + '\'' +
                ", quantity=" + quantity +
                ", approvalStatus='" + approvalStatus + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return getProductId() == product.getProductId() && Float.compare(getPriceToday(), product.getPriceToday()) == 0 && getQuantity() == product.getQuantity() && Objects.equals(getProductName(), product.getProductName()) && Objects.equals(getCategory(), product.getCategory()) && Objects.equals(getAvailabilityStatus(), product.getAvailabilityStatus()) && Objects.equals(getApprovalStatus(), product.getApprovalStatus());
    }

}
