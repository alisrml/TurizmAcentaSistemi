package dao;

import business.RoomController;
import business.SeasonController;
import core.Database;
import entity.Hotel;
import entity.PensionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class HotelDao {
    private Connection connection = Database.getInstance();
    public ArrayList<Hotel> findAll() {
        ArrayList<Hotel> hotels = new ArrayList<>();
        String query = "SELECT * FROM hotel";

        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()){
                hotels.add(this.match(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hotels;
    }

    private Hotel match(ResultSet rs) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setId(rs.getInt("id"));
        hotel.setName(rs.getString("name"));
        hotel.setCity(rs.getString("city"));
        hotel.setRegion(rs.getString("region"));
        hotel.setAddress(rs.getString("address"));
        hotel.setEmail(rs.getString("email"));
        hotel.setPhone(rs.getString("phone"));
        hotel.setStar(rs.getInt("star"));

        String[] facilities = rs.getString("facilities").split(",");
        if(Arrays.asList(facilities).contains("carpark")) {hotel.setCarpark(true);}
        if(Arrays.asList(facilities).contains("spa")) {hotel.setSpa(true);}
        if(Arrays.asList(facilities).contains("roomservice")) {hotel.setRoomservice(true);}

        ArrayList<PensionType> pensiontypes = new ArrayList<>();
        String[] pensionTypes = rs.getString("pension_types").split(",");
        for(String pt: pensionTypes){
            PensionType pensionType = new PensionType(pt);
            pensiontypes.add(pensionType);
        }
        hotel.setPension_types(pensiontypes);

        return hotel;
    }

    public ArrayList<Hotel> query(String query) {
        ArrayList<Hotel> hotels = new ArrayList<>();

        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()){
                hotels.add(this.match(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hotels;
    }

    public Hotel getById(int id) {
        Hotel hotel = null;
        String query = "SELECT * FROM hotel WHERE id = ?";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                hotel = this.match(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hotel;
    }

    public Hotel getByName(String name){
        Hotel hotel = null;
        String query = "SELECT * FROM hotel WHERE name LIKE'%"+ name+"%'";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                hotel = this.match(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hotel;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM hotel WHERE id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean save(Hotel hotel) {
        String query = "INSERT INTO hotel (name,city,region,address,email,phone,star,facilities,pension_types) VALUES (?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1,hotel.getName());
            pr.setString(2,hotel.getCity());
            pr.setString(3,hotel.getRegion());
            pr.setString(4,hotel.getAddress());
            pr.setString(5,hotel.getEmail());
            pr.setString(6,hotel.getPhone());
            pr.setInt(7,hotel.getStar());

            ArrayList<String> facilities = new ArrayList<>();
            if(hotel.isCarpark()){facilities.add("carpark");}
            if(hotel.isSpa()){facilities.add("spa");}
            if(hotel.isRoomservice()){facilities.add("roomservice");}
            String strfacilities = String.join(",",facilities);
            pr.setString(8,strfacilities);

            ArrayList<String> pensions = new ArrayList<>();
            for(PensionType pt: hotel.getPension_types()){
                pensions.add(pt.getName());
            }
            String strpensions = String.join(",",pensions);
            pr.setString(9,strpensions);


            return  pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Hotel hotel) {
        String query = "UPDATE hotel SET name = ?, city = ?, region = ?, address = ?, email = ?, phone = ?, star = ?, facilities = ?, pension_types = ? WHERE id = ?";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1,hotel.getName());
            pr.setString(2,hotel.getCity());
            pr.setString(3,hotel.getRegion());
            pr.setString(4,hotel.getAddress());
            pr.setString(5,hotel.getEmail());
            pr.setString(6,hotel.getPhone());
            pr.setInt(7,hotel.getStar());

            ArrayList<String> facilities = new ArrayList<>();
            if(hotel.isCarpark()){facilities.add("carpark");}
            if(hotel.isSpa()){facilities.add("spa");}
            if(hotel.isRoomservice()){facilities.add("roomservice");}
            String strfacilities = String.join(",",facilities);
            pr.setString(8,strfacilities);

            ArrayList<String> pensions = new ArrayList<>();
            for(PensionType pt: hotel.getPension_types()){
                pensions.add(pt.getName());
            }
            String strpensions = String.join(",",pensions);
            pr.setString(9,strpensions);
            pr.setInt(10,hotel.getId());


            return  pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
