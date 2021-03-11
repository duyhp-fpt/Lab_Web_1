/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.daos;

import duyhp.dtos.UserDTO;
import duyhp.utils.Myconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author Huynh Duy
 */
public class UserDAO {

    public static UserDTO CheckLogin(String userID, String password) throws SQLException, NamingException {
        UserDTO user = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = Myconnection.makeConnetion();
            if (cn != null) {
                String sql = "select userName,roleID from tblUsers where password=? and userID=?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, password);
                pst.setString(2, userID);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String roleID = rs.getString("roleID");
                    String userName = rs.getString("userName");
                    user = new UserDTO(userID, roleID, userName);
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

        return user;
    }

    public static UserDTO CheckLoginForGoogle(String userID) throws SQLException, NamingException {
        UserDTO user = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = Myconnection.makeConnetion();
            if (cn != null) {
                String sql = "select userName,roleID from tblUsers where userID=?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, userID);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String roleID = rs.getString("roleID");
                    String userName = rs.getString("userName");
                    user = new UserDTO(userID, roleID, userName);
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
        return user;
    }

    public static boolean insertUser(String userID, String userName) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = Myconnection.makeConnetion();
            if (cn != null) {
                String sql = "Insert into tblUsers(userID,userName,roleID) "
                        + "values (?,?,'US') ";
                pst = cn.prepareStatement(sql);
                pst.setString(1, userID);
                pst.setString(2, userName);
                pst.executeUpdate();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return false;
    }
}
