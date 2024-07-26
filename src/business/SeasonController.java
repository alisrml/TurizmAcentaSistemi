package business;

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
}
