package com.example.coen390_app.Models;

public class ParkingLotDevice {

    public String name;
    public boolean status;

    public ParkingLotDevice(String name, boolean status){
        this.name = name;
        this.status = status;
    }

    public String getName(){
        return name;
    }

    public boolean getStatus(){return status;}

}
