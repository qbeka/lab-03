package com.example.listycity;

import java.io.Serializable; // Import Serializable

public class City implements Serializable { // Implement Serializable
    private String name;
    private String province;

    public City(String name, String province) {
        this.name = name;
        this.province = province;
    }

    public String getName() {
        return name;
    }

    public String getProvince() {        return province;
    }

    // Add setters to modify the city's details
    public void setName(String name) {
        this.name = name;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
