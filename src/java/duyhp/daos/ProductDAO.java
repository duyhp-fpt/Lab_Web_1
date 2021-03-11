/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.daos;

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
public class ProductDAO {
    public static float getMaxPrice() throws SQLException, NamingException {
        float result = 0;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = Myconnection.makeConnetion();
            if (cn != null) {
                String sql = "select top 1 price\n"
                        + "from tblProducts\n"
                        + "order by price DESC";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                if (rs.next()) {
                    result = rs.getFloat("price");
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
        return result;
    }
    public static int countAllProductActive(String txtSearch, String categoryID, float txtMax, float txtmin) throws SQLException, NamingException {
        int result = 0;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = Myconnection.makeConnetion();
            if (cn != null) {
                String sql;
                if (categoryID.equals("all")) {
                    sql = "select count(productID) as count\n"
                            + "from tblProducts\n"
                            + "where productName like ? and price between ? and ? and status='false' and quantity>0";
                } else {
                    sql = "select count(productID) as count\n"
                            + "from tblProducts\n"
                            + "where productName like ? and price between ? and ? and categoryID =? and status='true' and quantity>=0";
                }
                pst = cn.prepareStatement(sql);
                pst.setString(1, "%" + txtSearch + "%");
                pst.setFloat(2, txtmin);
                pst.setFloat(3, txtMax);
                if (!categoryID.equals("all")) {
                    pst.setString(4, categoryID);
                }
                rs = pst.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("count");
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
        return result;
    }
    public static ArrayList<ProductDTO> getAllProductActive(int pageSize, int pageIndex, String txtSearch, String categoryID, float txtMax, float txtmin) throws SQLException, NamingException {
        ArrayList<ProductDTO> list = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = Myconnection.makeConnetion();
            if (cn != null) {
                String sql = "";
                if (categoryID.equals("all")) {
                    sql = "with x as(select ROW_NUMBER() over (order by productID asc ) as r,productID,productName,categoryID,description,imageName,price,quantity,createDate,status from tblProducts where productName like ? and price between ? and ? and status='false' and quantity>0) \n"
                            + "select productID,productName,categoryID,description,imageName,price,quantity,createDate,status\n"
                            + "from x\n"
                            + "where r between ? and ?";
                } else {
                    sql = "with x as(select ROW_NUMBER() over (order by productID asc ) as r,productID,productName,categoryID,description,imageName,price,quantity,createDate,status from tblProducts where productName like ? and price between ? and ? and categoryID =? and status='false' and quantity>0) \n"
                            + "select productID,productName,categoryID,description,imageName,price,quantity,createDate,status\n"
                            + "from x\n"
                            + "where r between ? and ?";
                }

                pst = cn.prepareStatement(sql);
                pst.setString(1, "%" + txtSearch + "%");
                pst.setFloat(2, txtmin);
                pst.setFloat(3, txtMax);
                if (!categoryID.equals("all")) {
                    pst.setString(4, categoryID);
                    pst.setInt(5, pageSize * pageIndex - (pageSize - 1));
                    pst.setInt(6, pageSize * pageIndex);
                } else {
                    pst.setInt(4, pageSize * pageIndex - (pageSize - 1));
                    pst.setInt(5, pageSize * pageIndex);
                }

                rs = pst.executeQuery();
                while (rs.next()) {
                    String productID = rs.getString("productID");
                    String productName = rs.getString("productName");
                    String caegoryID = rs.getString("categoryID");
                    String description = rs.getString("description");
                    String imageName = rs.getString("imageName");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    String createDate = rs.getString("createDate");
                    boolean status = rs.getBoolean("status");
                    ProductDTO product = new ProductDTO(productID, productName, caegoryID, description, imageName, price, quantity, createDate, status);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(product);
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
    public static boolean checkOurProduct(String productID, int quantity) throws SQLException, NamingException {
        boolean result = false;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = Myconnection.makeConnetion();
            if (cn != null) {
                String sql = "select productID\n"
                        + "from tblProducts\n"
                        + "where productID=? and status='false' and quantity>=?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, productID);
                pst.setInt(2, quantity);
                rs = pst.executeQuery();
                if (rs.next()) {
                    result = true;
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
        return result;
    }
    public static ArrayList<ProductDTO> getAllProduct(int pageSize, int pageIndex, String txtSearch, String categoryID, float txtMax, float txtmin) throws SQLException, NamingException {
        ArrayList<ProductDTO> list = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = Myconnection.makeConnetion();
            if (cn != null) {
                String sql ;
                if (categoryID.equals("all")) {
                    sql = "with x as(select ROW_NUMBER() over (order by productID asc ) as r,productID,productName,categoryID,description,imageName,price,quantity,createDate,status from tblProducts where productName like ? and price between ? and ? ) \n"
                            + "select productID,productName,categoryID,description,imageName,price,quantity,createDate,status\n"
                            + "from x\n"
                            + "where r between ? and ?";
                } else {
                    sql = "with x as(select ROW_NUMBER() over (order by productID asc ) as r,productID,productName,categoryID,description,imageName,price,quantity,createDate,status from tblProducts where productName like ? and price between ? and ? and categoryID =? ) \n"
                            + "select productID,productName,categoryID,description,imageName,price,quantity,createDate,status\n"
                            + "from x\n"
                            + "where r between ? and ?";
                }

                pst = cn.prepareStatement(sql);
                pst.setString(1, "%" + txtSearch + "%");
                pst.setFloat(2, txtmin);
                pst.setFloat(3, txtMax);
                if (!categoryID.equals("all")) {
                    pst.setString(4, categoryID);
                    pst.setInt(5, pageSize * pageIndex - (pageSize - 1));
                    pst.setInt(6, pageSize * pageIndex);
                } else {
                    pst.setInt(4, pageSize * pageIndex - (pageSize - 1));
                    pst.setInt(5, pageSize * pageIndex);
                }

                rs = pst.executeQuery();
                while (rs.next()) {
                    String productID = rs.getString("productID");
                    String productName = rs.getString("productName");
                    String caegoryID = rs.getString("categoryID");
                    String description = rs.getString("description");
                    String imageName = rs.getString("imageName");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    String createDate = rs.getString("createDate");
                    boolean status = rs.getBoolean("status");
                    ProductDTO product = new ProductDTO(productID, productName, caegoryID, description, imageName, price, quantity, createDate, status);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(product);
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

    public static void updateProduct(ProductDTO product, String userID) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = Myconnection.makeConnetion();
            if (cn != null) {
                String sql = "update tblProducts \n"
                        + "set productName=?,categoryID=?,description=?,imageName=?,price=?,quantity=?,status=?\n"
                        + "where productID=?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, product.getProductName());
                pst.setString(2, product.getCaegoryID());
                pst.setString(3, product.getDescription());
                pst.setString(4, product.getImageName());
                pst.setFloat(5, product.getPrice());
                pst.setInt(6, product.getQuantity());
                pst.setBoolean(7, product.isStatus());
                pst.setString(8, product.getProductID());
                int result = pst.executeUpdate();
                if (result == 1) {
                    sql = "insert into tblrecordUpdate(userID,productID,recordDate,note) values(?,?,?,?)";
                    pst = cn.prepareStatement(sql);
                    pst.setString(1, userID);
                    pst.setString(2, product.getProductID());
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    String recordDate = dateFormat.format(date);
                    pst.setString(3, recordDate);
                    pst.setString(4, "Update");
                    pst.executeUpdate();
                }
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

    public static void insertNewProduct(ProductDTO product, String userID) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = Myconnection.makeConnetion();
            if (cn != null) {
                String sql = "insert into tblProducts(productName,categoryID,description,imageName,price,quantity,createDate,status)\n"
                        + "values(?,?,?,?,?,?,?,?)";
                pst = cn.prepareStatement(sql);
                pst.setString(1, product.getProductName());
                pst.setString(2, product.getCaegoryID());
                pst.setString(3, product.getDescription());
                pst.setString(4, product.getImageName());
                pst.setFloat(5, product.getPrice());
                pst.setInt(6, product.getQuantity());
                pst.setBoolean(8, product.isStatus());
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                String createDate = dateFormat.format(date);
                pst.setString(7, createDate);
                int result = pst.executeUpdate();
                if (result == 1) {
                    sql = "insert into tblrecordUpdate(userID,productID,recordDate,note) values(?,?,?,?)";
                    pst = cn.prepareStatement(sql);
                    pst.setString(1, userID);
                    pst.setString(2, product.getProductID());
                    String recordDate = createDate;
                    pst.setString(3, recordDate);
                    pst.setString(4, "Create");
                    pst.executeUpdate();
                }
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

    public static void DeleteProduct(String productID, String userID) throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = Myconnection.makeConnetion();
            if (cn != null) {
                String sql = "update tblProducts\n"
                        + "set status='true'\n"
                        + "where productID=?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, productID);
                int result = pst.executeUpdate();
                if (result == 1) {
                    sql = "insert into tblrecordUpdate(userID,productID,recordDate,note) values(?,?,?,?)";
                    pst = cn.prepareStatement(sql);
                    pst.setString(1, userID);
                    pst.setString(2, productID);
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    String recordDate = dateFormat.format(date);
                    pst.setString(3, recordDate);
                    pst.setString(4, "Delete");
                    pst.executeUpdate();
                }
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
}
