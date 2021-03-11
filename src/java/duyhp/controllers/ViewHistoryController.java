/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.controllers;

import duyhp.daos.CartDAO;
import duyhp.dtos.CartDTO;
import duyhp.dtos.UserDTO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class ViewHistoryController extends HttpServlet {
private static final String SUCCESS = "shopinghistory.jsp";
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
        try {
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("USER");
            if (user != null && "US".equals(user.getRoleID())) {
                String orderDate = request.getParameter("txtOrderDate");
                boolean check = true;
                if (orderDate == null || orderDate.isEmpty()) {
                    orderDate = "";
                } else {
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    df.setLenient(false);
                    try {
                        Date date = df.parse(orderDate);
                        orderDate = df.format(date);
                    } catch (Exception e) {
                        request.setAttribute("DATEERROR", "wrong format!");
                        check = false;
                        orderDate = "";
                    }
                }
                if (check) {
                    String txtOrderSearch = request.getParameter("txtOrderSearch");
                    if (txtOrderSearch == null) {
                        txtOrderSearch = "";
                    }
                    ArrayList<CartDTO> historyCart = CartDAO.getHistory(user.getUserID(), orderDate.trim(), txtOrderSearch.trim());
                    String orderID = request.getParameter("txtOrderID");
                    if (orderID != null) {
                        for (CartDTO cartDTO : historyCart) {
                            if (cartDTO.getOrderID().equals(orderID)) {
                                request.setAttribute("orderDetais", cartDTO.getCart().values());
                            }
                        }
                    }
                    session.setAttribute("HISTORY", historyCart);
                }
                url = SUCCESS;
            } else {
                url = LOGOUT;
            }
        } catch (Exception e) {
            Logger.getLogger(ViewHistoryController.class.getName()).error(e.toString());
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
