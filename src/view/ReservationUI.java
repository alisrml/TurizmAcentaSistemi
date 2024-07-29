package view;

import business.ReservationController;
import business.RoomController;
import core.Helper;
import entity.Reservation;
import entity.Room;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ReservationUI extends JFrame {
    private JPanel container;
    private JTextField fld_rez_name;
    private JTextField fld_rez_phone;
    private JTextField fld_rez_checkindate;
    private JTextField fld_rez_checkoutdate;
    private JTextField fld_rez_adult;
    private JTextField fld_rez_child;
    private JButton btn_rez_save;
    private JLabel lbl_total_price;
    private JLabel lbl_rez_info;
    private JLabel lbl_hotel_name;
    private JLabel lbl_room_type;
    private JLabel lbl_pension_type;
    private JLabel lbl_title;
    private JLabel lbl_rez_capacity;
    private JButton btn_calc_price;
    private Reservation reservation;
    private ReservationController reservationController;
    private RoomController roomController;

    public ReservationUI(Reservation reservation){
        this.reservation = reservation;
        this.reservationController = new ReservationController();
        this.roomController = new RoomController();

        this.add(container);
        this.setTitle("Turizm Acenta Sistemi");
        this.setSize(400,600);
        this.setVisible(true);

        int x = (Toolkit.getDefaultToolkit().getScreenSize().width-this.getSize().width)/2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height-this.getSize().height)/2;
        this.setLocation(x,y);

        if(reservation.getId() == 0){
            lbl_title.setText("Rezervasyon Yap");
        }else {
            lbl_title.setText("Rezervasyon Düzenle");
            fld_rez_name.setText(reservation.getCustomer_name());
            fld_rez_phone.setText(reservation.getCustomer_contact());
            fld_rez_checkindate.setText(reservation.getCheck_in_date().toString());
            fld_rez_checkoutdate.setText(reservation.getCheck_out_date().toString());
            fld_rez_adult.setText(String.valueOf(reservation.getAdults()));
            fld_rez_child.setText(String.valueOf(reservation.getChildren()));

        }
        //Oda bilgilerini yazdir.
        Room room = roomController.getById(this.reservation.getRoom_id());
        lbl_hotel_name.setText(room.getHotel().getName());
        lbl_room_type.setText(String.valueOf(room.getType()));
        lbl_pension_type.setText(room.getPensionType().getName());
        lbl_rez_capacity.setText("Yatak Sayısı:"+String.valueOf(room.getBed()));

        btn_calc_price.addActionListener(e -> {lbl_total_price.setText("Fiyat: "+String.valueOf(roomPriceCalc()));});

        btn_rez_save.addActionListener(e -> {
            JTextField[] checkList = {this.fld_rez_name,this.fld_rez_phone,this.fld_rez_checkindate,this.fld_rez_checkoutdate,this.fld_rez_adult,this.fld_rez_child};
            if(Helper.isFieldEmpty(checkList)){
                Helper.showMsg("fill");
            } else if (Integer.parseInt(fld_rez_adult.getText())+ Integer.parseInt(fld_rez_child.getText()) > room.getBed()) {
                Helper.showMsg("Kişi Sayısı Yatak Sayısını Geçemez!");
            }else {
                boolean result = false;
                this.reservation.setRoom_id(room.getId());
                this.reservation.setCustomer_name(fld_rez_name.getText());
                this.reservation.setCustomer_contact(fld_rez_phone.getText());
                this.reservation.setCheck_in_date(LocalDate.parse(fld_rez_checkindate.getText()));
                this.reservation.setCheck_out_date(LocalDate.parse(fld_rez_checkoutdate.getText()));
                this.reservation.setAdults(Integer.parseInt(fld_rez_adult.getText()));
                this.reservation.setChildren(Integer.parseInt(fld_rez_child.getText()));
                this.reservation.setTotal_price(roomPriceCalc());

                if(this.reservation.getId() == 0){
                    result = this.reservationController.save(this.reservation);
                }else {
                    result = this.reservationController.update(this.reservation);
                }

                if(result){
                    Helper.showMsg("done");
                    dispose();
                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }

    private int roomPriceCalc(){
        Room room = this.roomController.getById(this.reservation.getRoom_id());

        LocalDate checkinDate = LocalDate.parse(fld_rez_checkindate.getText());
        LocalDate checkoutDate = LocalDate.parse(fld_rez_checkoutdate.getText());
        int daysBetween = (int) ChronoUnit.DAYS.between(checkinDate,checkoutDate);

        int adult = Integer.parseInt(fld_rez_adult.getText());
        int child = Integer.parseInt(fld_rez_child.getText());
        int Price = (adult*room.getAdultPrice() + child*room.getKidPrice())*daysBetween;

        return Price;
    }
}
