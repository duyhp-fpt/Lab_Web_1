/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.daos;

import duyhp.dtos.PaymentDTO;
import duyhp.utils.Myconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;

/**
 *
 * @author Huynh Duy
 */
public class PaymentDAO {
    public static ArrayList<PaymentDTO> getPayment() throws SQLException, NamingException {
        ArrayList<PaymentDTO> list = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            cn = Myconnection.makeConnetion();
            if (cn != null) {
                String sql = "select paymentID,payName\n"
                        + "from tblPayments";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    String paymentID = rs.getString("paymentID");
                    String payName = rs.getString("payName");
                    PaymentDTO payment = new PaymentDTO(paymentID, payName);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(payment);
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
