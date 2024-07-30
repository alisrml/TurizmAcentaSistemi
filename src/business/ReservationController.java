package business;

import core.Helper;
import dao.ReservationDao;
import entity.Reservation;

import java.util.ArrayList;

public class ReservationController {
    private ReservationDao reservationDao;

    public ReservationController(){this.reservationDao = new ReservationDao();}
    public ArrayList<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public boolean save(Reservation reservation) {
        return this.reservationDao.save(reservation);
    }

    public boolean update(Reservation reservation) {
        if(this.getById(reservation.getId()) == null){
            Helper.showMsg(reservation.getId() + " ID kayıtlı rezervasyon bulunamadı!");
            return false;
        }
        return this.reservationDao.update(reservation);
    }

    public Reservation getById(int id) {
        return reservationDao.getById(id);
    }

    public boolean delete(int selectId) {
        return reservationDao.delete(selectId);
    }
}
