/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.dtos;

/**
 *
 * @author Huynh Duy
 */
public class UserDTO {

    String userID;
    String roleID;
    String userName;

    public UserDTO() {
    }

    public UserDTO(String userID, String roleID, String userName) {
        this.userID = userID;
        this.roleID = roleID;
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

}
