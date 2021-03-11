/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.controllers;

import duyhp.daos.ProductDAO;
import duyhp.dtos.ProductDTO;
import duyhp.dtos.ProductErrorDTO;
import duyhp.dtos.UserDTO;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.log4j.Logger;
@MultipartConfig // liên quan đến việc chuyển tải hình ảnh
/**
 *
 * @author Huynh Duy
 */
public class UpdateProductController extends HttpServlet {

    private static final String SUCCESS = "AdminController";
    private static final String ERROR = "adminpage.jsp";
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
            boolean check = true;
            ProductErrorDTO Error = new ProductErrorDTO("", "", "", "", "");
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("USER");
            if (user != null && "AD".equals(user.getRoleID())) {
                String productID = request.getParameter("productID");
                String productName = request.getParameter("productName");
                if (productName == null || productName.isEmpty()) {
                    Error.setProductNameError("ProductName not Empty!!!");
                    check = false;
                }
                String categoryID = request.getParameter("cbxcategory");
                String description = request.getParameter("txtDescription");
                if (description == null || description.isEmpty()) {
                    Error.setDescriptionError("Description is not Empty!");
                    check = false;
                }
                float price = 0;
                try {
                    price = Float.parseFloat(request.getParameter("productPrice"));
                    if (price <= 0) {
                        Error.setPriceErro("Enter price number >0!!!!");
                        check = false;
                    }
                } catch (NumberFormatException e) {
                    Error.setPriceErro("Enter number Price!!!");
                    check = false;
                }

                int quantity = 0;
                try {
                    quantity = Integer.parseInt(request.getParameter("txtQuantity"));
                    if (quantity <= 0) {
                        Error.setQuantityError("Quantity >0!!!!!");
                        check = false;
                    }
                } catch (NumberFormatException e) {
                    Error.setQuantityError("Quantity Enter Number!!!!!!");
                    check = false;
                }
                Part filePart = request.getPart("imageName");
                String fileName = filePart.getSubmittedFileName();
                if (filePart != null) {
                    if (fileName.indexOf(":") >= 0) {
                        fileName = fileName.replaceAll(":", "");
                    }
                    if (!fileName.isEmpty()) {
                        String contextType = filePart.getContentType();
                        if (!contextType.contains("image")) {
                            Error.setImageNameError("Selected file image!!!!");
                            check = false;
                        }
                        if (check) {
                            String part = getServletContext().getRealPath("");
                            String partr = part.substring(0, part.lastIndexOf(File.separator + "build")) + File.separator + "web" + File.separator + "image";
                            File fImage = new File(partr);
                            if (!fImage.exists()) {
                                fImage.mkdir();
                            }
                            partr = partr + File.separator + fileName;
                            fImage = new File(partr);
                            if (!fImage.exists()) {
                                filePart.write(partr);
                            }
                        }
                    }
                }
                if (fileName.isEmpty()) {
                    fileName = request.getParameter("imageOld");
                }
                String createDate = request.getParameter("txtCreateDate");
                boolean status = Boolean.parseBoolean(request.getParameter("status"));
                if (check) {
                    ProductDTO product = new ProductDTO(productID, productName, categoryID, description, fileName, price, quantity, createDate, status);
                    ProductDAO.updateProduct(product, user.getUserID());
                    request.setAttribute("MESSAGE", "Update " + productName + " sussecc.");
                } else {
                    request.setAttribute("ERROR", Error);
                }
                url = SUCCESS;
            } else {
                url = LOGOUT;
            }
        } catch (Exception e) {
            Logger.getLogger(UpdateProductController.class.getName()).error(e.toString());
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
