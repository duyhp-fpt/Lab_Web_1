/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.controllers;

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
public class UpdateCartController extends HttpServlet {
private static final String SUCCESS = "viewcart.jsp";
    private static final String ERROR = "index.jsp";
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
        try{
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("USER");
            if (user != null && "US".equals(user.getRoleID())) {
                int productQuantity = Integer.parseInt(request.getParameter("txtQuantity"));
                String productName = request.getParameter("txtProductName");
                String productID = request.getParameter("txtProductID");
                float price = Float.parseFloat(request.getParameter("txtProductPrice"));
                ProductDTO product = new ProductDTO(productID, productName, price, productQuantity);
                CartDTO cart = (CartDTO) session.getAttribute("CART");
                if (cart == null) {
                    cart = new CartDTO(user.getUserID(), null);
                }
                if (productQuantity == 0) {
                    cart.delete(productID);
                } else {
                    if(ProductDAO.checkOurProduct(productID, productQuantity)){
                        cart.update(product);
                        request.setAttribute("MESSAGE", "Update " + productName + " Success.");
                    }
                    else{
                        request.setAttribute("MESSAGE", productName + " product not enough");
                    }
                }
                session.setAttribute("CART", cart);               
                url = SUCCESS;
            } else {
                url = LOGOUT;
            }
        }catch (Exception e) {
           Logger.getLogger(UpdateCartController.class.getName()).error(e.toString());
        }finally{
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
