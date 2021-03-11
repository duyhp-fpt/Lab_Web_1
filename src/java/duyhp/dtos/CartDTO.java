/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.dtos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Huynh Duy
 */
public class CartDTO implements Serializable{
    private String orderID;
    private String userID;
    private float price;
    private String orderDate;
    private String paymentID;
    private Map<String,ProductDTO> cart;

    public CartDTO() {
    }

    public CartDTO(String userID, float price, String orderDate, Map<String, ProductDTO> cart) {
        this.userID = userID;
        this.price = price;
        this.orderDate = orderDate;
        this.cart = cart;
    }

    public CartDTO(String userID, Map<String, ProductDTO> cart) {
        this.userID = userID;
        this.cart = cart;
    }

    public CartDTO(String userID, float price, String orderDate, String paymentID, Map<String, ProductDTO> cart) {
        this.userID = userID;
        this.price = price;
        this.orderDate = orderDate;
        this.paymentID = paymentID;
        this.cart = cart;
    }

    public CartDTO(String orderID, String userID, float price, String orderDate, String paymentID, Map<String, ProductDTO> cart) {
        this.orderID = orderID;
        this.userID = userID;
        this.price = price;
        this.orderDate = orderDate;
        this.paymentID = paymentID;
        this.cart = cart;
    }

    
    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
    

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }
    
    
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Map<String, ProductDTO> getCart() {
        return cart;
    }

    public void setCart(Map<String, ProductDTO> cart) {
        this.cart = cart;
    }
    
    public void add(ProductDTO product){
        if(cart==null){
            this.cart=new HashMap<String,ProductDTO>();
        }
        if(this.cart.containsKey(product.getProductID())){
            int quantity=this.cart.get(product.getProductID()).getQuantity();
            product.setQuantity(product.getQuantity()+quantity);
        }
        cart.put(product.getProductID(), product);
    }
    public void update(ProductDTO product){
        if(this.cart!=null){
            if(this.cart.containsKey(product.getProductID())){
                this.cart.replace(product.getProductID(), product);
            }
        }
    }
    public void delete(String productID){
        if(this.cart==null)
            return;
        if(this.cart.containsKey(productID)){
            this.cart.remove(productID);
        }
    }
}

