<%-- 
    Document   : createproduct
    Created on : Jan 23, 2021, 10:39:39 AM
    Author     : Huynh Duy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Product Page</title>
    </head>
    <body>
        <c:set var="role" value="AD" scope="page"/>
        <c:choose>
            <c:when test="${not empty sessionScope.USER && sessionScope.USER.roleID eq role}">
                <div class="action" style="text-align: center">
                    <a href="MainController?btnAction=Logout">Log Out</a>
                    <a href="MainController?btnAction=View Product">User Mode</a>
                    <a href="MainController?btnAction=adminMode">Admin Mode</a>
                </div>
                <h3> Create Product</h3>
                <form action="MainController" method="POST" enctype="multipart/form-data">
                    <table border="0">
                        <thead>
                            <tr>
                                <th>Product Name</th>
                                <th>Category</th>
                                <th>Description</th>
                                <th>Image</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    <input type="text" name="txtProductName" value="${param.txtProductName}" />
                                </td>
                                <td>
                                    <select name="cbxCategory">
                                        <c:forEach var="category" items="${sessionScope.CATEGORIES}">
                                            <c:choose>
                                                <c:when test="${pageScope.category.categoryID eq requestScope.cbxCategory}">
                                                    <option value="${pageScope.category.categoryID}" selected="">${pageScope.category.categoryName}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${pageScope.category.categoryID}">${pageScope.category.categoryName}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <textarea rows="15" cols="47" name="txtDescription">${param.txtDescription}</textarea>
                                </td>
                                <td>
                                    <input type="file" name="imageName" value="${product.imageName}" accept="image/x-png,image/gif,image/jpeg"/>
                                </td>
                                <td>
                                    <input type="number" name="txtPrice" value="${param.txtPrice}"/>
                                </td>
                                <td>
                                    <input type="number" name="txtQuantity" value="${param.txtQuantity}" max="999999999"/>
                                </td>
                                <td>
                                    <select name="status">
                                        <option value="${false}" selected="">Active</option>
                                        <option value="${true}" selected="">InActive</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>${ERROR.productNameError}</td>
                                <td></td>
                                <td>${ERROR.descriptionError}</td>
                                <td>${ERROR.imageNameError}</td>
                                <td>${ERROR.priceError}</td>
                                <td>${ERROR.quantityError}</td>
                                <td></td>
                            </tr>
                        </tbody>
                    </table>
                    <div style="text-align: center">
                        <input type="submit" value="Create" name="btnAction"/>
                        <input type="reset" value="Reset" />
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <h1> <font style="color: red">You are not authorized to perform this action</font> </h1>
                <a href="MainController?btnAction=Logout">Login</a>
            </c:otherwise>
        </c:choose>
    </body>
</html>
