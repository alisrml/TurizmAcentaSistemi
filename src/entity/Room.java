package entity;

public class Room {
    private int id;
    private Hotel hotel;
    private TYPE type;
    private PensionType pensionType;
    private Season season;
    private int adultPrice;
    private int kidPrice;
    private int stock;
    private int bed;
    private boolean tv;
    private boolean minibar;
    private int capacity = this.bed;

    public enum TYPE{
        SINGLEROOM,
        DOUBLEROOM,
        JUNIORSUITEROOM,
        SUITEROOM
    }

    public Room() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public PensionType getPensionType() {
        return pensionType;
    }

    public void setPensionType(PensionType pensionType) {
        this.pensionType = pensionType;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public int getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(int adultPrice) {
        this.adultPrice = adultPrice;
    }

    public int getKidPrice() {
        return kidPrice;
    }

    public void setKidPrice(int kidPrice) {
        this.kidPrice = kidPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getBed() {
        return bed;
    }

    public void setBed(int bed) {
        this.bed = bed;
    }

    public boolean isTv() {
        return tv;
    }

    public void setTv(boolean tv) {
        this.tv = tv;
    }

    public boolean isMinibar() {
        return minibar;
    }

    public void setMinibar(boolean minibar) {
        this.minibar = minibar;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
