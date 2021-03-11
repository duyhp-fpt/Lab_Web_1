/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.controllers;

import duyhp.daos.CartDAO;
import duyhp.daos.ProductDAO;
import duyhp.dtos.CartDTO;
import duyhp.dtos.ProductDTO;
import duyhp.dtos.UserDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author Huynh Duy
 */
public class CheckOutController extends HttpServlet {

    private static final String SUCCESS = "ViewProductController";
    private static final String ERROR = "viewcart.jsp";
    private static final String LOGOUT = "LogOutController";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("USER");
            if (user != null && "US".equals(user.getRoleID())) {
                CartDTO cart = (CartDTO) session.getAttribute("CART");
                String paymentID = request.getParameter("cbxPayment");
                if (paymentID == null || paymentID.isEmpty()) {
                    cart.setPaymentID("COD");
                } else {
                    cart.setPaymentID(paymentID);
                }
                boolean check = false;
                float total = 0;
                for (ProductDTO value : cart.getCart().values()) {
                    check = ProductDAO.checkOurProduct(value.getProductID(), value.getQuantity());
                    total = total + value.getPrice() * value.getQuantity();
                    if (!check) {
                        request.setAttribute("MESSAGE", value.getProductName() + "  is out of stock.");
                        break;
                    }
                }
                if (check) {
                    String orderID = CartDAO.insertOrder(cart.getUserID(), cart.getPaymentID(), total);
                    for (ProductDTO value : cart.getCart().values()) {
                        CartDAO.insertOrderDetail(orderID, value.getPrice(), value.getProductID(), value.getQuantity());
                    }
                    request.setAttribute("MESSAGE", "Pay success.");
                    session.removeAttribute("CART");
                    url = SUCCESS;
                }
            } else {
                url = LOGOUT;
            }
        } catch (Exception e) {
            Logger.getLogger(CheckOutController.class.getName()).error(e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
