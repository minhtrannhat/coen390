package com.example.coen390_app.Models;

import android.location.Address;

import com.google.firebase.database.ChildEvent;

import java.io.Serializable;

public class SecondaryParkingLot implements Serializable {

    public String name;

    public String address;

    public int occupancy;

    public String colour;

    public SecondaryParkingLot(String name, String address, int occupancy, String colour){
        this.name = name;
        this.address = address;
        this.occupancy = occupancy;
        this.colour = colour;
    }

    public String getName(){
        return name;
    }
    public String getAddress(){
        return address;
    }

    public int getOccupancy(){
        return occupancy;
    }

    public String getColour(){
        return colour;
    }
}
