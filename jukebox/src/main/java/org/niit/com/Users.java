package org.niit.com;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Users {
    private int userId;
    private String userName;

    public Users() {
    }

    public Users(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
