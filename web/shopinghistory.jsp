<%-- 
    Document   : shoppinghistory
    Created on : Jan 22, 2021, 7:14:11 AM
    Author     : Huynh Duy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>History Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
  </head>
  <body>
    <div style="text-align: center">
      <a href="MainController?btnAction=View Product">Home</a>
      <a href="MainController?btnAction=LogOut">LogOut</a>
    </div>
    <c:choose>
        <c:when test="${not empty sessionScope.USER}">
            <h3 style="text-align: center">Hello <font style="color: blue">${sessionScope.USER.userName}</h3>
              <c:set var="historyCart" value="${sessionScope.HISTORY}"/>
            <table border="0">
              <thead>
                <tr>
                  <th>Date(dd/MM/yyyy</th>
                  <th>Name</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
              <form action="MainController">
                <tr>
                  <td>
                    <input type="text" name="txtOrderDate" value="${param.txtOrderDate}"/>
                  </td>
                  <td>
                    <input type="text" name="txtOrderSearch" value="${param.txtOrderSearch}"/>
                  </td>
                  <td>
                    <input type="hidden" name="btnAction" value="Shopping history"/>
                    <input type="submit" value="Search"/>
                  </td>
                </tr>
              </form>
              <tr>
                <td><font style="color: red"/>${DATEERROR}</td>
                <td></td>
                <td></td>
              </tr>
            </tbody>
        </table>
        <c:choose>
            <c:when test="${historyCart!=null && historyCart.size()>0}">
                <table border="0">
                  <thead>
                    <tr>
                      <th>Order ID</th>
                      <th>Total Price</th>
                      <th>Order Date</th>
                      <th>Payment</th>
                      <th></th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach var="cartH" items="${historyCart}">
                    <form action="MainController">
                      <tr>
                        <td>${cartH.orderID}</td>
                        <td>${cartH.price}</td>
                        <td>${cartH.orderDate}</td>
                        <td>
                          <c:forEach var="payment" items="${sessionScope.PAYMENTS}">
                              <c:if test="${payment.paymentID eq cartH.paymentID}">
                                  ${payment.payName}
                              </c:if>
                          </c:forEach>
                        </td>
                      </tr>
                    </form>
                  </c:forEach>
                </tbody>
            </table>
            <c:if test="${orderDetails!=null}">
                <table border="0">
                  <thead>
                    <tr>
                      <th>Product</th>
                      <th>Price</th>
                      <th>Quantity</th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach var="product" items="${orderDetails}">
                        <tr>
                          <td>${product.productName}</td>
                          <td>${product.price} VND</td>
                          <td>${product.quantity}</td>
                        </tr>
                    </c:forEach>
                  </tbody>
                </table>
            </c:if>
        </c:when>
        <c:otherwise>
            <h5>No order</h5>
        </c:otherwise>
    </c:choose>
  </c:when>
  <c:otherwise>
      <h1><font style="color: red">You are not authorized to perform this action></font></h1>
      <a href="MainController?btnAction=LogOut">Login</a>
  </c:otherwise>
</c:choose>
</body>
</html>
