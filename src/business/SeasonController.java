package business;

import core.Helper;
import dao.SeasonDao;
import entity.Season;

import java.time.LocalDate;
import java.util.ArrayList;

public class SeasonController {
    private SeasonDao seasonDao = new SeasonDao();

    public SeasonController() {
    }

    public Season getById(int id) {
        return seasonDao.getById(id);
    }

    public ArrayList<Season> getByHotelId(int id) {return seasonDao.getByHotelId(id);}

    public Season getByDates(int hotel_id,String  startDate,String endDate){return seasonDao.getByDates(hotel_id,startDate,endDate);}

    public ArrayList<Season> findAll() {
        return this.seasonDao.findAll();
    }

    public ArrayList<Season> filter(String hotelName) {
        String query = "SELECT * FROM season s JOIN hotel h ON s.hotel_id = h.id WHERE h.name LIKE '%"+hotelName+"%'";

        return this.seasonDao.query(query);
    }

    public boolean delete(int id) {
        return this.seasonDao.delete(id);
    }

    public boolean save(Season season) {
        return this.seasonDao.save(season);
    }

    public boolean update(Season season) {
        if(this.getById(season.getId()) == null){
            Helper.showMsg(season.getId() +"ID kayıtlı sezon bulunamadı!");
            return false;
        }
        return this.seasonDao.update(season);
    }
}
