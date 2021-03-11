<%-- 
    Document   : viewcart.jsp
    Created on : Jan 21, 2021, 11:10:26 AM
    Author     : Huynh Duy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>View Cart Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
  </head>
  <body>
    <c:set var="role" value="US" scope="page"/>
    <c:choose>
        <c:when test="${not empty sessionScope.USER and sessionScope.USER.roleID eq role}">
            <h3 style="text-align: center">Hello <font style="color: blue">${sessionScope.USER.userName}</h3>
            <div style="text-align: center"><a href="MainController?btnAction=Logout" style="text-decoration: none">Logout</a></div>
            <c:set var="cart" value="${sessionScope.CART}"/>
            <c:choose>
                <c:when test="${cart!=null && cart.getCart().size()!=0}">
                    <h3>Your Cart</h3>
                    <h3><font style="color: blue">${requestScope.MESSAGE}</h3>
                    <a href="viewproducts.jsp">Add more</a>
                    <table border="0">
                      <thead>
                        <tr>
                          <th>Product Name</th>
                          <th>Amount</th>
                          <th>Quantity</th>
                          <th>Total Price</th>
                          <th>Action</th>
                          <th></th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:set var="total" value="${0}"/>
                        <c:forEach var="product" items="${sessionScope.CART.getCart().values()}">
                            <tr>
                        <form action="MainController">
                          <td>${product.productName}</td>
                          <td>${product.price}</td>
                          <td>
                            <input type="number" name="txtQuantity" value="${product.quantity}" max="999999999"/>
                          </td>
                          <td>${product.price*product.quantity}VND</td>
                          <c:set var="total" value="${total + product.price*product.quantity}"/>
                          <td>
                            <input type="hidden" name="txtProductID" value="${product.productID}" />
                            <input type="hidden" name="txtProductName" value="${product.productName}" />
                            <input type="hidden" name="txtProductPrice" value="${product.price}" />
                            <input type="hidden" name="btnAction" value="updateCart"/>
                            <input type="submit" value="Update"/>
                          </td>
                        </form>
                        <form action="MainController">
                          <td>
                            <input type="hidden" name="txtProductID" value="${product.productID}" />
                            <input type="hidden" name="btnAction" value="deleteCart" />
                            <input type="submit" value="Delete" onclick="return confirm('Do you want to delete ?')"/>
                          </td>
                        </form>
                      </tr>
                    </c:forEach>
                    <tr>
                      <td>Total</td>
                      <td><font style="">${total}</td>
                    </tr>
                </tbody>
            </table>
            <form action="MainController">
              <select name="cbxPayment">
                <c:forEach var="payment" items="${sessionScope.PAYMENTS}">
                    <c:choose>
                        <c:when test="${payment.paymentID eq cbxPayment}">
                            <option value="${payment.paymentID}" selected="">${payment.payName}</option>> 
                        </c:when>
                        <c:otherwise>
                            <option value="${payment.paymentID}">${payment.payName}</option> 
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
              </select>
              <input type="submit" value="Pay Now" name="btnAction"/>
            </form>
        </c:when>
        <c:otherwise>
            <h5>You have not bought any products</h5>
            <form action="MainController">
              <input type="submit" name="btnAction" value="View Product"/>
            </form>
        </c:otherwise>
    </c:choose>
  </c:when>
  <c:otherwise>
      <h1 class="text-center my-5"><font style="color: red">You are not authorized to perform this action</font></h1>
      <div class="text-center">
        <a href="MainController?btnAction=Logout" class="btn btn-primary">Login</a>
      </div>
  </c:otherwise>
</c:choose>
</body>
</html>
