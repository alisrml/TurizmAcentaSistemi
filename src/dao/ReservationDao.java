package dao;

import core.Database;
import entity.Reservation;

import java.sql.*;
import java.util.ArrayList;

public class ReservationDao {
    private Connection connection = Database.getInstance();
    public ArrayList<Reservation> findAll() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservation";

        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()){
                reservations.add(this.match(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservations;
    }

    private Reservation match(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(rs.getInt("id"));
        reservation.setRoom_id(rs.getInt("room_id"));
        reservation.setCustomer_name(rs.getString("customer_name"));
        reservation.setCustomer_contact(rs.getString("customer_contact"));
        reservation.setCheck_in_date(rs.getDate("check_in_date").toLocalDate());
        reservation.setCheck_out_date(rs.getDate("check_out_date").toLocalDate());
        reservation.setAdults(rs.getInt("adults"));
        reservation.setChildren(rs.getInt("children"));
        reservation.setTotal_price(rs.getInt("total_price"));

        return reservation;
    }

    public boolean save(Reservation reservation) {
        String query = "INSERT INTO reservation (id,room_id,customer_name,customer_contact,check_in_date,check_out_date,adults,children,total_price) VALUES (?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1,reservation.getId());
            pr.setInt(2,reservation.getRoom_id());
            pr.setString(3,reservation.getCustomer_name());
            pr.setString(4,reservation.getCustomer_contact());
            pr.setDate(5, Date.valueOf(reservation.getCheck_in_date()));
            pr.setDate(6, Date.valueOf(reservation.getCheck_out_date()));
            pr.setInt(7,reservation.getAdults());
            pr.setInt(8,reservation.getChildren());
            pr.setInt(9,reservation.getTotal_price());

            return  pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Reservation getById(int id) {
        Reservation reservation = null;
        String query = "SELECT * FROM reservation WHERE id = ?";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                reservation = this.match(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservation;
    }

    public boolean update(Reservation reservation) {
        String query = "UPDATE reservation SET room_id = ?,customer_name = ?,customer_contact = ?,check_in_date = ?,check_out_date = ?,adults = ?, children = ?,total_price = ? WHERE id = ?";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1,reservation.getRoom_id());
            pr.setString(2,reservation.getCustomer_name());
            pr.setString(3,reservation.getCustomer_contact());
            pr.setDate(4, Date.valueOf(reservation.getCheck_in_date()));
            pr.setDate(5, Date.valueOf(reservation.getCheck_out_date()));
            pr.setInt(6,reservation.getAdults());
            pr.setInt(7,reservation.getChildren());
            pr.setInt(8,reservation.getTotal_price());
            pr.setInt(9,reservation.getId());

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int id){
        String query = "DELETE reservation WHERE id = ?";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
