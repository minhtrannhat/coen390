package com.example.coen390_app.Models;

import java.util.List;

public class Admin {
    public String username;
    public String password;

    public Admin() {
    }

    public Admin(String email, String password) {
        this.username = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
