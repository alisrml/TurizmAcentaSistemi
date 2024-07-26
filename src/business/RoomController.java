package business;

import core.Helper;
import dao.RoomDao;
import entity.Room;

import java.sql.SQLException;
import java.util.ArrayList;

public class RoomController {
    private RoomDao roomDao;

    public RoomController() {this.roomDao = new RoomDao();}
    public ArrayList<Room> findAll(){return this.roomDao.findAll();}

    public ArrayList<Room> filter(String hotelName) {
        String query = "SELECT * FROM room r JOIN hotel h ON r.hotel_id = h.id WHERE h.name LIKE '%"+hotelName+"%'";

        return this.roomDao.query(query);
    }

    public Room getById(int id){return this.roomDao.getById(id);}

    public boolean delete(int id){
        if(this.getById(id) == null){
            Helper.showMsg(id+" ID kayıtlı oda bulunamadı!");
            return false;
        }
        return this.roomDao.delete(id);
    }

    public boolean save(Room room){
        return this.roomDao.save(room);
    }

    public boolean update(Room room){
        if(this.getById(room.getId()) == null){
            Helper.showMsg(room.getId()+" ID kayıtlı oda bulunamadı!");
            return false;
        }
        return this.roomDao.update(room);
    }
}
