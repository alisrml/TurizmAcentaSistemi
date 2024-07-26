package dao;

import business.HotelController;
import business.SeasonController;
import core.Database;
import entity.PensionType;
import entity.Room;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class RoomDao {
    private Connection connection = Database.getInstance();
    private HotelController hotelController = new HotelController();
    private SeasonController seasonController = new SeasonController();
    public ArrayList<Room> findAll() {
        ArrayList<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM room";

        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()){
                rooms.add(this.match(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rooms;
    }

    private Room match(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setId(rs.getInt("id"));
        room.setHotel(hotelController.getById(rs.getInt("hotel_id")));
        String typeStr = rs.getString("type");
        Room.TYPE type = Room.TYPE.valueOf(typeStr);
        room.setType(type);
        room.setPensionType(new PensionType(rs.getString("pension_type")));
        room.setSeason(seasonController.getById(rs.getInt("season_id")));
        room.setAdultPrice(rs.getInt("adult_price"));
        room.setKidPrice(rs.getInt("child_price"));
        room.setStock(rs.getInt("stock"));
        room.setBed(rs.getInt("bed"));
        room.setCapacity(rs.getInt("bed"));

        String[] str = rs.getString("features").split(",");
        if(Arrays.asList(str).contains("tv")){room.setTv(true);}
        if(Arrays.asList(str).contains("minibar")){room.setMinibar(true);}

        return room;
    }

    public ArrayList<Room> query(String query){
        ArrayList<Room> rooms = new ArrayList<>();

        ResultSet rs = null;
        try {
            rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()){
                rooms.add(this.match(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rooms;
    }

    public Room getById(int id) {
        Room room = null;
        String query = "SELECT * FROM room where id = ?";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                room = this.match(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return room;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM room WHERE id = ?";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean save(Room room) {
        String query = "INSERT INTO room (id,hotel_id,season_id,pension_type,type,adult_price,child_price,stock,bed,features) VALUES (?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1,room.getId());
            pr.setInt(2,room.getHotel().getId());
            pr.setInt(3,room.getSeason().getId());
            pr.setString(4,room.getPensionType().getName());
            pr.setString(5,room.getType().toString());
            pr.setInt(6,room.getAdultPrice());
            pr.setInt(7,room.getKidPrice());
            pr.setInt(8,room.getStock());
            pr.setInt(9,room.getBed());

            ArrayList<String> features = new ArrayList<>();
            if(room.isTv()){features.add("tv");}
            if(room.isMinibar()){features.add("minibar");}
            String roomFeatures = String.join(",",features);
            pr.setString(10,roomFeatures);

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Room room) {
        String query = "UPDATE room SET hotel_id = ?,season_id = ?,pension_type = ?,type = ?,adult_price = ?,child_price = ?,stock = ?,bed = ?,features = ? WHERE id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1,room.getHotel().getId());
            pr.setInt(2,room.getSeason().getId());
            pr.setString(3,room.getPensionType().getName());
            pr.setString(4,room.getType().toString());
            pr.setInt(5,room.getAdultPrice());
            pr.setInt(6,room.getKidPrice());
            pr.setInt(7,room.getStock());
            pr.setInt(8,room.getBed());

            ArrayList<String> features = new ArrayList<>();
            if(room.isTv()){features.add("tv");}
            if(room.isMinibar()){features.add("minibar");}
            String roomFeatures = String.join(",",features);
            pr.setString(9,roomFeatures);
            pr.setInt(10,room.getId());

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
