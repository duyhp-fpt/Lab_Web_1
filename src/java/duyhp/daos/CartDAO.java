/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.daos;

import duyhp.dtos.CartDTO;
import duyhp.dtos.ProductDTO;
import duyhp.utils.Myconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.naming.NamingException;

/**
 *
 * @author Huynh Duy
 */
public class CartDAO {

    public static String insertOrder(String userID, String paymentID, float totalPrice) throws SQLException, NamingException {
        String orderID = "";
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = Myconnection.makeConnetion();
            if (cn != null) {
                String sql = "insert into tblOrder(userID,paymentID,orderDate,totalPrice) values(?,?,?,?)\n";
                pst = cn.prepareStatement(sql);
                pst.setString(1, userID);
                pst.setString(2, paymentID);
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                String orderDate = dateFormat.format(date);
                pst.setString(3, orderDate);
                pst.setFloat(4, totalPrice);
                pst.executeUpdate();
                sql = "select top 1 orderID\n"
                        + "from tblOrder\n"
                        + "where userID=?\n"
                        + "ORDER BY orderID desc";
                pst = cn.prepareStatement(sql);
                pst.setString(1, userID);
                rs = pst.executeQuery();
                if (rs.next()) {
                    orderID = rs.getString("orderID");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return orderID;
    }

    public static void insertOrderDetail(String orderID, float price, String productID, int quantity) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = Myconnection.makeConnetion();
            if (cn != null) {
                String sql = "insert into tblOrderDetail(orderID,price,productID,quantity) values(?,?,?,?)";
                pst = cn.prepareStatement(sql);
                pst.setString(1, orderID);
                pst.setFloat(2, price);
                pst.setString(3, productID);
                pst.setInt(4, quantity);
                pst.executeUpdate();
                sql = "update tblProducts\n"
                        + "set quantity=quantity-?\n"
                        + "where productID=?";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, quantity);
                pst.setString(2, productID);
                pst.executeUpdate();
            }
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }
    public static ArrayList<CartDTO> getHistory(String userID, String orderDates, String txtOrderSearch) throws SQLException, NamingException {
        ArrayList<CartDTO> list = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = Myconnection.makeConnetion();
            if (cn != null) {
                String sql = "";
                if (orderDates.isEmpty()) {
                    sql = "select a.orderID,a.userID,a.paymentID,a.orderDate,a.totalPrice\n"
                            + "from tblOrder a,tblOrderDetail b,tblProducts c\n"
                            + "where a.userID=? and a.orderID=b.orderID and b.productID=c.productID and c.productName like ?";
                } else {
                    sql = "select a.orderID,a.userID,a.paymentID,a.orderDate,a.totalPrice\n"
                            + "from tblOrder a,tblOrderDetail b,tblProducts c\n"
                            + "where a.userID=? and a.orderID=b.orderID and b.productID=c.productID and c.productName like ? and CONVERT(varchar, a.orderDate, 103) =?";
                }
                pst = cn.prepareStatement(sql);
                pst.setString(1, userID);
                pst.setString(2, "%" + txtOrderSearch + "%");
                if (!orderDates.isEmpty()) {
                    pst.setString(3, orderDates);
                }
                rs = pst.executeQuery();
                while (rs.next()) {
                    float price = rs.getFloat("totalPrice");
                    String paymentID = rs.getString("paymentID");
                    String orderID = rs.getString("orderID");
                    String orderDate = rs.getString("orderDate");
                    CartDTO cart = new CartDTO(orderID, userID, price, orderDate, paymentID, null);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    if (list.size() == 0) {
                        list.add(cart);
                    } else {
                        boolean check = true;
                        for (CartDTO cartDTO : list) {
                            if (cartDTO.getOrderID().equals(cart.getOrderID())) {
                                check = false;
                            }
                        }
                        if (check) {
                            list.add(cart);
                        }
                    }
                }
                if (list != null) {
                    for (CartDTO cartDTO : list) {
                        sql = "select a.productID,a.quantity,a.price,b.productName\n"
                                + "from tblOrderDetail a, tblProducts b\n"
                                + "where orderID=? and a.productID=b.productID";
                        pst = cn.prepareStatement(sql);
                        pst.setString(1, cartDTO.getOrderID());
                        rs = pst.executeQuery();
                        while (rs.next()) {
                            String productID = rs.getString("productID");
                            String productName = rs.getString("productName");
                            int quantity = rs.getInt("quantity");
                            float price = rs.getFloat("price");
                            ProductDTO productCart = new ProductDTO(productID, productName, price, quantity);
                            cartDTO.add(productCart);
                        }
                    }
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return list;
    }
}
