package business;

import core.Helper;
import dao.HotelDao;
import entity.Hotel;

import javax.swing.*;
import java.util.ArrayList;

public class HotelController {
    private HotelDao hotelDao;

    public HotelController(){
        this.hotelDao = new HotelDao();
    }
    public ArrayList<Hotel> findAll() {
        return this.hotelDao.findAll();
    }

    public ArrayList<Hotel> filter(String name,String city) {
        String query = "SELECT * FROM hotel";

        ArrayList<String> whereList = new ArrayList<>();

        if(name.length()>0){whereList.add("name LIKE '%"+name+"%'");}

        if(city.length()>0){whereList.add("city LIKE '%"+city+"%'");}

        if(whereList.size()>0){
            String whereQuery = String.join(" AND ",whereList);
            query += " WHERE "+whereQuery;
        }
        return this.hotelDao.query(query);
    }

    public Hotel getById(int id) {
        return this.hotelDao.getById(id);
    }

    public boolean delete(int id) {
        if(this.getById(id) == null){
            Helper.showMsg(id + "ID kayıtlı oyel bulunamadı!");
            return false;
        }
        return this.hotelDao.delete(id);
    }

    public boolean save(Hotel hotel) {
        return this.hotelDao.save(hotel);
    }

    public boolean update(Hotel hotel) {
        if(this.getById(hotel.getId()) == null){
            Helper.showMsg(hotel.getId()+ " ID kayıtlı müşteri bulunamadı!");
            return false;
        }
        return  this.hotelDao.update(hotel);
    }
}
