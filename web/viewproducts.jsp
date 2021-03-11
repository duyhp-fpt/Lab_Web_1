<%-- 
    Document   : viewproducts
    Created on : Jan 20, 2021, 11:18:00 PM
    Author     : Huynh Duy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Product Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
  </head>
  <body>
    <h1 style="text-align: center;color: greenyellow">Welcome Hana Shop</h1>
    <c:if test="${sessionScope.USER!=null}">
        <h5 style="text-align: center">Hello<font style="color: greenyellow"> ${sessionScope.USER.userName}</font></h5>
        <div class="action" style="text-align: center">
          <a href="MainController?btnAction=Logout"style="text-decoration: none; margin-right: 20px">Logout</a>
          <c:set var="roleUS" scope="page" value="US"></c:set>
          <c:if test="${sessionScope.USER.roleID eq roleUS}">
              <a href="MainController?btnAction=ViewCart" style="text-decoration: none; margin-right: 20px">View Cart</a>
              <a href="MainController?btnAction=Shopping history "style="text-decoration: none; margin-right: 20px">Shopping history</a>
          </c:if>
          <c:set var="roleAD" scope="page" value="AD"></c:set>
          <c:if test="${sessionScope.USER.roleID eq roleAD}"><a href="MainController?btnAction=adminMode" style="text-decoration: none">Admin Mode</a></c:if>
          </div>
    </c:if>
    <c:if test="${sessionScope.USER == null}">
        <div style="text-align: center"><a href="login.jsp" class="btn btn-primary" style="text-decoration: none">Login</a></div>
    </c:if>
    <table border="0" class="mt-3" style="margin-left: auto;margin-right: auto">
      <thead>
        <tr class="text-center">
          <th>Category</th>
          <th>Name</th>
          <th>Min</th>
          <th>Max</th>
        </tr>
      </thead>
      <tbody>
      <form action="MainController">
        <tr>
          <td>
            <select name="cbxCategory" class="form-control">
              <option value="all">All</option>
              <c:forEach var="category" items="${sessionScope.CATEGORIES}">
                  <c:choose>
                      <c:when test="${pageScope.category.categoryID eq requestScope.cbxCategory}">
                          <option value="${pageScope.category.categoryID}" selected="${pageScope.category.categoryName}"></option>
                      </c:when>
                      <c:otherwise>
                          <option value="${pageScope.category.categoryID}">${pageScope.category.categoryName}</option>
                      </c:otherwise>
                  </c:choose>
              </c:forEach>
            </select>
          </td>
          <td>
            <input type="text" name="txtSearch" value="${param.txtSearch}" class="form-control"/>
          </td>
          <td>
            <input type="number" name="txtMin" value="${param.txtMin}" class="form-control"/>
          </td>
          <td>
            <input type="number" name="txtMax" value="${param.txtMax}" class="form-control"/>
          </td>
          <td>
            <input type="submit" name="btnAction" value="View Product" class="form-control"/>
          </td>
        </tr>
      </form>
    </tbody>
  </table>
  <h1><font style="color: red"${requestScope.MESSAGE}></font></h1>
  <table border="0" class="mt-5">
    <thead>
      <tr>
        <th>Product Name</th>
        <th>Product Image</th>
        <th>Description</th>
        <th>Price</th>
        <th>Create Date</th>
        <th>Category</th>
        <th>Quantity</th>
      </tr>
    </thead>
    <tbody>
      <c:set var="productCarts" value="${sessionScope.CART.getCart().values()}"></c:set>
      <c:forEach var="product" items="${sessionScope.PRODUCTS}">   
      <form action="MainController">
        <tr>                        
          <td style="text-align: center">
            ${product.productName}
            <input type="hidden" name="productName" value="${product.productName}" />
          </td>
          <td><img src="image/${product.imageName}" style="width:200px ;height:200px"></img> </td>
          <td style="width:  600px">${product.description}</td>
          <td style="text-align: center">
            ${product.price} VND
            <input type="hidden" name="productPrice" value="${product.price}" />
          </td>
          <td style="text-align: center">${product.createDate}</td>
          <td style="text-align: center">
            <c:forEach var="category" items="${sessionScope.CATEGORIES}">
                <c:if test="${product.caegoryID eq category.categoryID}">
                    ${category.categoryName}
                </c:if>
            </c:forEach>
          </td>
          <td style="text-align: center">
            <c:set var="quantity" value="${product.quantity}"></c:set>
            <c:forEach var="productCart" items="${productCarts}">
                <c:if test="${product.productID eq productCart.productID}">
                    <c:set var="quantity" value="${product.quantity - productCart.quantity}"></c:set>
                </c:if>
            </c:forEach>
            ${pageScope.quantity}
          </td>
          <td>
            <input type="hidden" name="txtQuantityTemp" value="${quantity}" />
            <input type="hidden" name="productID" value="${product.productID}" />       
            <input type="submit" value="Add to cart" name="btnAction" />                                                                                                                      
          </td>                        
        </tr> 
      </form>
    </c:forEach> 
  </tbody>
</table>
<div class="paging">
  <c:forEach begin="1" end="${endPage}" var="i">

      <c:url var="paging" value="MainController">
          <c:param name="btnAction" value="View Product"></c:param>
          <c:param name="txtSearch" value="${txtSearch}"></c:param>
          <c:param name="txtMax" value="${txtMax}"></c:param>
          <c:param name="txtMin" value="${txtMin}"></c:param>
          <c:param name="cbxCategory" value="${cbxCategory}"></c:param>
          <c:param name="pageIndex" value="${i}"></c:param>
      </c:url>
      <a href="${paging}">${i}</a>
  </c:forEach>
</div>
</tbody>
</table>
</body>
</html>
