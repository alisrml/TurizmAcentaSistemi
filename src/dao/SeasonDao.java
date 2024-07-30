package dao;

import core.Database;
import entity.Season;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class SeasonDao {
    private Connection connection = Database.getInstance();

    public SeasonDao() {
    }

    public Season getById(int id) {
        Season season = null;
        String query = "SELECT * FROM season WHERE id = ?";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                season = this.match(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return season;
    }

    private Season match(ResultSet rs) throws SQLException {
        Season season = new Season();
        season.setId(rs.getInt("id"));
        season.setHotel_id(rs.getInt("hotel_id"));
        season.setStart_date(rs.getDate("start_date").toLocalDate());
        season.setEnd_date(rs.getDate("end_date").toLocalDate());

        return season;
    }

    public ArrayList<Season> getByHotelId(int id) {
        ArrayList<Season> seasons = new ArrayList<>();
        String query = "SELECT * FROM season WHERE hotel_id = ?";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                seasons.add(this.match(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return seasons;
    }

    public Season getByDates(int hotel_id,String startDate, String endDate) {
        Season season = null;
        String query = "SELECT * FROM season WHERE hotel_id = ? AND start_date = ? AND end_date = ?";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1,hotel_id);
            java.sql.Date startDateSql = java.sql.Date.valueOf(startDate);
            java.sql.Date endDateSql = java.sql.Date.valueOf(endDate);
            pr.setDate(2, Date.valueOf(startDateSql.toLocalDate()));
            pr.setDate(3, Date.valueOf(endDateSql.toLocalDate()));
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                season = this.match(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return season;
    }

    public ArrayList<Season> findAll() {
        ArrayList<Season>  seasons= new ArrayList<>();
        String query = "SELECT * FROM season";

        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()){
                seasons.add(this.match(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return seasons;
    }

    public ArrayList<Season> query(String query) {
        ArrayList<Season> seasons = new ArrayList<>();

        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()){
                seasons.add(this.match(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return seasons;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM season WHERE id = ?";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean save(Season season) {
        String query = "INSERT INTO season (id,hotel_id,start_date,end_date) VALUES (?,?,?,?)";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1,season.getId());
            pr.setInt(2,season.getHotel_id());
            pr.setDate(3, Date.valueOf(season.getStart_date()));
            pr.setDate(4, Date.valueOf(season.getEnd_date()));

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Season season) {
        String query = "UPDATE season SET hotel_id = ?,start_date = ?,end_date = ? WHERE id = ?";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1,season.getHotel_id());
            pr.setDate(2, Date.valueOf(season.getStart_date()));
            pr.setDate(3, Date.valueOf(season.getEnd_date()));
            pr.setInt(4,season.getId());

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
