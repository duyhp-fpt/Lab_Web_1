/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.controllers;

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
public class AddToCartController extends HttpServlet {
    
    private static final String SUCCESS = "viewproducts.jsp";
    private static final String ERROR = "login.jsp";
    private static final String LOGOUT = "LogoutController";

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
            UserDTO dtos = (UserDTO) session.getAttribute("USER");
            if (dtos != null && "US".equals(dtos.getRoleID())) {
                CartDTO cart = (CartDTO) session.getAttribute("CART");
                String productID = request.getParameter("productID");
                String productName = request.getParameter("productName");
                float price = Float.parseFloat(request.getParameter("productPrice"));
                int quantity = Integer.parseInt(request.getParameter("txtQuantityTemp"));
                if (cart == null) {
                    cart = new CartDTO(dtos.getUserID(), null);
                }
                if (quantity > 0) {
                    cart.add(new ProductDTO(productID, productName, price, 1));
                    session.setAttribute("CART", cart);
                    request.setAttribute("MESSAGE", "You bought " + productName + " success.");
                } else {
                    request.setAttribute("MESSAGE", productName + " is out of stock.");
                }
                url = SUCCESS;
            }else if (dtos != null && "AD".equals(dtos.getRoleID())){
                url = LOGOUT;
            }
        } catch (Exception e) {
            Logger.getLogger(AddToCartController.class.getName()).error(e.toString());
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
