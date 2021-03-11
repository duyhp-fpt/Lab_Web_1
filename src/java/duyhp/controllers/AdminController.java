/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.controllers;

import duyhp.daos.CategoryDAO;
import duyhp.daos.ProductDAO;
import duyhp.dtos.CategoryDTO;
import duyhp.dtos.ProductDTO;
import duyhp.dtos.UserDTO;
import java.io.IOException;
import java.util.ArrayList;
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
public class AdminController extends HttpServlet {
private static final String SUCCESS = "adminpage.jsp";
    private static final String ERROR = "index.html";
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
        String url=ERROR;
        try {
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("USER");
            if (user != null && "AD".equals(user.getRoleID())) {
                float maxPrice=ProductDAO.getMaxPrice();
            
            String categoryID=request.getParameter("cbxCategory");
            if(categoryID==null ||categoryID.isEmpty()){
                categoryID="all";
            }
            String txtSearch=request.getParameter("txtSearch");
            if(txtSearch==null){
                txtSearch="";
            }
            String txtMaxS=request.getParameter("txtMax");
            float txtMax;
            if(txtMaxS==null|| txtMaxS.isEmpty()){
                txtMax=maxPrice;
            }else{
                txtMax=Float.parseFloat(txtMaxS);
            }
            String txtMinS=request.getParameter("txtMin");
            float txtMin;
            if(txtMinS==null||txtMinS.isEmpty()){
                txtMin=0;
            }else{
                txtMin=Float.parseFloat(txtMinS);
            }
            if(txtMin>=txtMax){
                request.setAttribute("MESSAGE", "price max>price min");
            }
            int pageIndex;
            String pageIndexS=request.getParameter("pageIndex");
            if(pageIndexS==null){
                    pageIndex=1;
            }else{
                pageIndex=Integer.parseInt(pageIndexS);
            }
            int count = ProductDAO.countAllProductActive(txtSearch, categoryID, txtMax, txtMin);
            int pageSize = 15;
            int endPage = 0;
            endPage = (int) Math.ceil((float)count / pageSize);
            ArrayList<ProductDTO> products = ProductDAO.getAllProduct(pageSize, pageIndex, txtSearch.trim(), categoryID, txtMax, txtMin);
            ArrayList<CategoryDTO> categorys = CategoryDAO.getCategory();
            
            request.setAttribute("cbxCategory", categoryID);
            session.setAttribute("CATEGORYS", categorys);
            session.setAttribute("PRODUCTS", products);
            session.setAttribute("endPage", endPage);
            url = SUCCESS;
            }else{
                url = LOGOUT;
            }
            
        } catch (Exception e) {
            Logger.getLogger(AdminController.class.getName()).error(e.toString());
        }finally{
            request.getRequestDispatcher(url).forward(request, response);;
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
