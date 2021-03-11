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
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import org.apache.log4j.Logger;
@MultipartConfig
/**
 *
 * @author Huynh Duy
 */
public class CreateProductController extends HttpServlet {
private static final String SUCCESS = "adminpage.jsp";
    private static final String ERROR = "createproduct.jsp";
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
            UserDTO user = (UserDTO) session.getAttribute("USER");
            if (user != null && "AD".equals(user.getRoleID())) {
                boolean check = true;
                ProductErrorDTO pError = new ProductErrorDTO("", "", "", "", "");
                String txtProductName = request.getParameter("txtProductName").trim();
                if (txtProductName.isEmpty()) {
                    check = false;
                    pError.setProductNameError("Product Name is Empty!");
                }
                String category = request.getParameter("cbxCategory").trim();
                String description = request.getParameter("txtDescription").trim();
                if (description.isEmpty()) {
                    check = false;
                    pError.setDescriptionError("description is Empty!");
                }
                float price = 0;
                try {
                    price = Float.parseFloat(request.getParameter("txtPrice").trim());
                    if (price <= 0) {
                        check = false;
                        pError.setPriceErro("Price must be >0!");
                    }
                } catch (NumberFormatException e) {
                    check = false;
                    pError.setPriceErro("Enter number in Price!!!");
                }
                int quantity = 0;
                try {
                    quantity = Integer.parseInt(request.getParameter("txtQuantity").trim());
                    if (quantity <= 0) {
                        check = false;
                        pError.setQuantityError("Quantity must be >0!!!");
                    }
                } catch (NumberFormatException e) {
                    check = false;
                    pError.setQuantityError("Enter number in Quantity!!!");
                }
                boolean status = Boolean.parseBoolean(request.getParameter("status"));
                Part filePart = request.getPart("imageName");
                String fileName = filePart.getSubmittedFileName();
                if (filePart != null) {
                    if (fileName.indexOf(":") >= 0) {
                        fileName = fileName.replaceAll(":", "");
                    }
                    if (!fileName.isEmpty()) {
                        String contextType = filePart.getContentType();
                        if (!contextType.contains("image")) {
                            pError.setImageNameError("Selected file image!!!!");
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
                    } else {
                        pError.setImageNameError("Selset file image!!!!");
                        check = false;
                    }
                }
                if (check) {
                    ProductDTO p = new ProductDTO(txtProductName, category, description, fileName, price, quantity, status);
                    ProductDAO.insertNewProduct(p, user.getUserID());
                    url = SUCCESS;
                    request.setAttribute("MESSAGE", "Create success.");
                } else {
                    request.setAttribute("ERROR", pError);
                }
            } else {
                url = LOGOUT;
            }
        } catch (Exception e) {
            Logger.getLogger(CreateProductController.class.getName()).error(e.toString());
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
