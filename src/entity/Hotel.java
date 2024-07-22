package entity;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private int id;
    private String name;
    private String city;
    private String region;
    private String address;
    private String email;
    private String phone;
    private int star;
    private boolean carpark;
    private boolean spa;
    private boolean roomservice;
    private List<PensionType> pension_types;

    public Hotel() {
        this.carpark = false;
        this.roomservice = false;
        this.spa = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public boolean isCarpark() {
        return carpark;
    }

    public void setCarpark(boolean carpark) {
        this.carpark = carpark;
    }

    public boolean isSpa() {
        return spa;
    }

    public void setSpa(boolean spa) {
        this.spa = spa;
    }

    public boolean isRoomservice() {
        return roomservice;
    }

    public void setRoomservice(boolean roomservice) {
        this.roomservice = roomservice;
    }

    public List<PensionType> getPension_types() {
        return pension_types;
    }

    public void setPension_types(List<PensionType> pension_types) {
        this.pension_types = pension_types;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", star='" + star + '\'' +
                ", carpark=" + carpark +
                ", spa=" + spa +
                ", roomservice=" + roomservice +
                ", pension_types=" + pension_types +
                '}';
    }
}
