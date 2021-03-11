<%-- 
    Document   : login
    Created on : Jan 20, 2021, 9:09:41 AM
    Author     : Huynh Duy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="google-signin-scope" content="profile email">
    <meta name="google-signin-client_id" content="273627501527-4ubb4cbk7m380o0piubgvmnnrrmotrrs.apps.googleusercontent.com">
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <title>Login Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
  </head>
  <body>
    <h1 class="mb-3 text-center my-5">Login</h1>
    <h1><font style="color: blueviolet">${requestScope.MESSAGE}</font></h1>

    <div style="display: flex;justify-content: center;">
      <form action="MainController" method="POST" class="bg-light p-3">
        <table border="0">
          <thead>
            <tr>
              <th>User ID</th>
              <th><input class="form-control" type="text" name="userID" value="${param.userID}"</th>
              <th><font color="red">${userIDError}</font> </th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>Password</td>
              <td><input class="form-control" type="password" name="password" value="${param.password}"</td>
              <td><font color="red">${passwordError}</font></td>
            </tr>
          </tbody>
        </table>

        <font color="red">${LOGINERROR}</font></br>
        <input type="submit" value="Login" name="btnAction"/>
        <input type="submit" value="View Product" name="btnAction"/>
        <input type="reset" value="Reset"/></br>
        <div class="g-signin2 mt-3" data-onsuccess="onSignIn" data-theme="dark"></div>
      </form>
    </div>

    <script>
        function onSignIn(googleUser) {
            // Useful data for your client-side scripts:
            var auth2 = gapi.auth2.getAuthInstance();
            auth2.signOut().then(function () {
                console.log('User signed out.');
            });
            var profile = googleUser.getBasicProfile();
            window.location.href = 'MainController?btnAction=GmailByLogin&UserID=' + profile.getId() + '&name=' + profile.getName();

            // The ID token you need to pass to your backend:
            var id_token = googleUser.getAuthResponse().id_token;
            console.log("ID Token: " + id_token);
        }
    </script>
  </body>
</html>
