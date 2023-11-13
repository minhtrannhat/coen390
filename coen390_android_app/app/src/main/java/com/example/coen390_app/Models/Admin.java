package com.example.coen390_app.Models;

import java.util.List;

public class Admin {
    public String email;
    public String password;

    public List<Integer> lots;

    public Admin() {
    }

    public Admin(String email, String password, List<Integer> lots) {
        this.email = email;
        this.password = password;
        this.lots = lots;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Integer> getLots() {
        return lots;
    }

    public void setLots(List<Integer> lots) {
        this.lots = lots;
    }
}
