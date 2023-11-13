package com.example.coen390_app.Models;

import java.util.List;
import java.util.Map;

public class ParkingLotProfile {
    public String address;
    public String city;
    public int current_occupancy;
    public int floor_total;
    public String lot_owner;
    public String map_size;

    public int max_occupancy;

    public String name;

    public String owner_tel;

    public int placed_occupancy;
    public String postal_code;

    public ParkingLotProfile(String address, String city, int current_occupancy, int floor_total, String lot_owner, String map_size, int max_occupancy, String name, String owner_tel, int placed_occupancy, String postal_code, Map<String, Map<String, Boolean>> occupancy) {
        this.address = address;
        this.city = city;
        this.current_occupancy = current_occupancy;
        this.floor_total = floor_total;
        this.lot_owner = lot_owner;
        this.map_size = map_size;
        this.max_occupancy = max_occupancy;
        this.name = name;
        this.owner_tel = owner_tel;
        this.placed_occupancy = placed_occupancy;
        this.postal_code = postal_code;
        this.occupancy = occupancy;
    }

    public Map<String, Map<String, Boolean>> getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(Map<String, Map<String, Boolean>> occupancy) {
        this.occupancy = occupancy;
    }

    public Map<String, Map<String, Boolean>> occupancy;

    public ParkingLotProfile() {
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCurrent_occupancy() {
        return current_occupancy;
    }

    public void setCurrent_occupancy(int current_occupancy) {
        this.current_occupancy = current_occupancy;
    }

    public int getFloor_total() {
        return floor_total;
    }

    public void setFloor_total(int floor_total) {
        this.floor_total = floor_total;
    }

    public String getLot_owner() {
        return lot_owner;
    }

    public void setLot_owner(String lot_owner) {
        this.lot_owner = lot_owner;
    }

    public String getMap_size() {
        return map_size;
    }

    public void setMap_size(String map_size) {
        this.map_size = map_size;
    }

    public int getMax_occupancy() {
        return max_occupancy;
    }

    public void setMax_occupancy(int max_occupancy) {
        this.max_occupancy = max_occupancy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner_tel() {
        return owner_tel;
    }

    public void setOwner_tel(String owner_tel) {
        this.owner_tel = owner_tel;
    }

    public int getPlaced_occupancy() {
        return placed_occupancy;
    }

    public void setPlaced_occupancy(int placed_occupancy) {
        this.placed_occupancy = placed_occupancy;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

}
