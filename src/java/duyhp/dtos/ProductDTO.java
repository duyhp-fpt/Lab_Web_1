/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.dtos;

import java.io.Serializable;

/**
 *
 * @author Huynh Duy
 */
public class ProductDTO implements Serializable {

    private String productID;
    private String productName;
    private String caegoryID;
    private String description;
    private String imageName;
    private float price;
    private int quantity;
    private String createDate;
    private boolean status;

    public ProductDTO() {
    }

    public ProductDTO(String productID, String productName, String caegoryID, String description, String imageName, float price, int quantity, String createDate, boolean status) {
        this.productID = productID;
        this.productName = productName;
        this.caegoryID = caegoryID;
        this.description = description;
        this.imageName = imageName;
        this.price = price;
        this.quantity = quantity;
        this.createDate = createDate;
        this.status = status;
    }

    public ProductDTO(String productID, String productName, float price, int quantity) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public ProductDTO(String productName, String caegoryID, String description, String imageName, float price, int quantity, boolean status) {
        this.productName = productName;
        this.caegoryID = caegoryID;
        this.description = description;
        this.imageName = imageName;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCaegoryID() {
        return caegoryID;
    }

    public void setCaegoryID(String caegoryID) {
        this.caegoryID = caegoryID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getPrice() {
        return (int) price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
