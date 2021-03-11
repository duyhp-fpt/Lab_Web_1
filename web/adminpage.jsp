<%-- 
    Document   : adminpage
    Created on : Jan 22, 2021, 9:25:17 PM
    Author     : Huynh Duy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
    </head>
    <body>
    <c:set var="role" value="AD" scope="page"/>
    <c:choose>           
        <c:when test="${not empty sessionScope.USER && sessionScope.USER.roleID eq role}">
            <h5 style="text-align: center">Hello <font style="color: green">${sessionScope.USER.userName}</font></h5>
            <div class="action" style="text-align: center">
                <a href="MainController?btnAction=Logout" style="text-decoration: none;margin-right: 20px">Log Out</a>
                <a href="MainController?btnAction=View Product" style="text-decoration: none;margin-right: 20px">User mode</a>
                <a href="createproduct.jsp" style="text-decoration: none">Create Product</a>
            </div>
            <div class="search">
                <table border="0" style="margin-left: auto;margin-right: auto">
                    <thead>
                        <tr>
                            <th>Category</th>
                            <th>Name</th>
                            <th>Min</th>
                            <th>Max</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                    <form action="MainController" method="POST">
                        <tr>
                            <td>
                                <select name="cbxCategory">
                                    <option value="all">All</option>
                                    <c:forEach var="category" items="${sessionScope.CATEGORIES}">
                                        <c:choose>
                                            <c:when test="${pageScope.category.categoryID eq requestScope.cbxCategory}">
                                                <option value="${pageScope.category.categoryID}" selected="">${pageScope.category.categoryName}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${pageScope.category.categoryID}" >${pageScope.category.categoryName}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>                                
                            </td>
                            <td>
                                <input type="text" name="txtSearch" value="${param.txtSearch}" />
                            </td>
                            <td>
                                <input type="number" name="txtMin" value="${param.txtMin}" min="0" max="9999999999"/>
                            </td>
                            <td>
                                <input type="number" name="txtMax" value="${param.txtMax}" mix="0" max="9999999999"/>
                            </td>
                            <td>
                                <input type="hidden" value="adminMode" name="btnAction" />
                                <input type="submit" value="Search" />
                            </td>
                        </tr>
                    </form>
                    </tbody>
                </table>
            </div>
            <h1> <font style="color: red">${requestScope.MESSAGE}</font> </h1>
            <div class="Errorupdate">
                <c:set value="${requestScope.ERROR}" var="error"/>
                <c:if test="${not empty error}">
                    <h3> ${error.productNameError} </h3>
                    <h3> ${error.descriptionError} </h3>
                    <h3> ${error.imageNameError} </h3>
                    <h3> ${error.priceErro} </h3>
                    <h3> ${error.quantityError} </h3>
                </c:if>
            </div>
            <div class="product">
                <table border="1" style="border-collapse:collapse">
                    <thead>
                        <tr style="text-align: center">
                            <th>Product Name</th>
                            <th>Product Image</th>
                            <th style="width: 300px">Description</th>
                            <th style="width: 100px">Price</th>
                            <th style="width: 100px">Create Date</th>
                            <th style="width: 60px">Category</th>
                            <th style="width: 20px">Quantity</th>
                            <th style="width: 80px">Status</th>                                
                            <th>Delete</th>
                            <th>Update</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="product" items="${sessionScope.PRODUCTS}">   
                        <form action="MainController" method="POST" enctype="multipart/form-data" >
                            <tr>                        
                                <td style="text-align: center">
                                    <input type="text" name="productName" value="${product.productName}" />
                                </td>
                                <td>
                                    <img src="image/${product.imageName}" style="width:200px ;height:200px" ></img>                                         
                                    <input type="file" name="imageName" value="" accept="image/x-png,image/gif,image/jpeg" />
                                    <input type="hidden" name="imageOld" value="${product.imageName}" />
                                </td>
                                <td style="width:  300px">
                                    <textarea rows="15" cols="47" name="txtDescription">${product.description}</textarea>
                                </td>
                                <td style="text-align: center">
                                    <input type="number" name="productPrice" value="${product.price}" />VND
                                </td>
                                <td style="text-align: center">
                                    ${product.createDate}
                                    <input type="hidden" name="txtCreateDate" value="${product.createDate}" />
                                </td>
                                <td style="text-align: center">
                                    <select name="cbxcategory">
                                        <c:forEach var="category" items="${sessionScope.CATEGORIES}">
                                            <c:choose>
                                                <c:when test="${product.caegoryID eq category.categoryID}">
                                                    <option value="${category.categoryID}"selected="">${category.categoryName}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${category.categoryID}">${category.categoryName}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>

                                </td>
                                <td style="text-align: center;width: 20px">
                                    <input type="number" name="txtQuantity" value="${product.quantity}" size="20px" max="999999999"/>
                                </td>
                                <td style="text-align: center">
                                    <select name="status" >
                                        <c:choose>
                                            <c:when test="${product.status == false}">
                                                <option value="false" selected="">Active</option>   
                                                <option value="true" >Inactive</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="true" selected="">Inactive</option>
                                                <option value="false" >Active</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </td>
                                <td style="text-align: center">
                                    <a href="MainController?btnAction=DeleteProduct&productID=${product.productID}" onclick="return confirm('Do you want to delete ?')">Delete</a>
                                </td>
                                <td>                                       
                                    <input type="hidden" name="productID" value="${product.productID}" />             
                                    <input type="submit" name="btnAction" value="Update Product"/>      
                                </td>   
                            </tr> 
                        </form>
                    </c:forEach> 

                    </tbody>
                </table>
            </div>
            <div class="paging">
                <c:forEach begin="1" end="${endPage}" var="i">

                    <c:url var="paging" value="MainController">
                        <c:param name="btnAction" value="adminMode"></c:param>
                        <c:param name="txtSearch" value="${txtSearch}"></c:param>
                        <c:param name="txtMax" value="${txtMax}"></c:param>
                        <c:param name="txtMin" value="${txtMin}"></c:param>
                        <c:param name="cbxCategory" value="${cbxCategory}"></c:param>
                        <c:param name="pageIndex" value="${i}"></c:param>
                    </c:url>
                    <a href="${paging}">${i}</a>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <h1> <font style="color: red">You are not authorized to perform this action</font> </h1>
            <a href="MainController?btnAction=LogOut">Login</a>
        </c:otherwise>
    </c:choose>
</body>
</html>
