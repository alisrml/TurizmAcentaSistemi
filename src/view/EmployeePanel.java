package view;

import business.HotelController;
import business.ReservationController;
import business.RoomController;
import business.SeasonController;
import core.Helper;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EmployeePanel extends JFrame {

    private JPanel container;
    private JTabbedPane tab_menu;
    private JLabel lbl_title;
    private JPanel pnl_hotel;
    private JPanel pnl_hotel_filter;
    private JTable tbl_hotel;
    private JScrollPane scrll_hotel;
    private JTextField fld_otel_name_filter;
    private JTextField fld_otel_city_filter;
    private JButton btn_otel_filter;
    private JButton btn_otel_reset;
    private JButton btn_otel_new;
    private JButton btn_logout;
    private JPanel pnl_room;
    private JTable tbl_room;
    private JScrollPane scrll_room;
    private JPanel Rezervasyon;
    private JTextField fld_room_filter;
    private JButton btn_room_filter;
    private JButton btn_room_reset;
    private JButton btn_room_new;
    private JPanel pnl_room_filter;
    private JTable tbl_season;
    private JPanel pnl_season_filter;
    private JTextField fld_season_filter;
    private JButton btn_season_filter;
    private JButton btn_season_reset;
    private JButton btn_season_new;
    private JScrollPane scrll_season;
    private JTable tbl_reservation;
    private JScrollPane scrll_reservation;
    private HotelController hotelController;
    private RoomController roomController;
    private SeasonController seasonController;
    private ReservationController reservationController;
    private DefaultTableModel tmdl_hotel = new DefaultTableModel();
    private DefaultTableModel tmdl_room = new DefaultTableModel();
    private DefaultTableModel tmdl_season = new DefaultTableModel();
    private DefaultTableModel tmdl_reservation = new DefaultTableModel();
    private  JPopupMenu popup_hotel = new JPopupMenu();
    private  JPopupMenu popup_room = new JPopupMenu();
    private JPopupMenu popup_season = new JPopupMenu();

    public EmployeePanel(){
        this.hotelController = new HotelController();
        this.roomController = new RoomController();
        this.seasonController = new SeasonController();
        this.reservationController = new ReservationController();

        this.add(container);
        this.setTitle("Turizm Acenta Sistemi");
        this.setSize(600,400);
        this.setVisible(true);

        int x = (Toolkit.getDefaultToolkit().getScreenSize().width-this.getSize().width)/2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height-this.getSize().height)/2;
        this.setLocation(x,y);

        btn_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginUI loginUI = new LoginUI();
            }
        });

        //HOTEL TAB
        loadHotelTable(null);
        loadHotelPopupMenu();
        loadHotelButtonEvent();

        //ROOM TAB
        loadRoomTable(null);
        loadRoomPopupMenu();
        loadRoomButtonEvent();

        //SEASON TAB
        loadSeasonTable(null);
        loadSeasonPopupMenu();
        loadSeasonButtonEvent();

        //RESERVATION TAB
        loadReservationTable(null);

    }
    public void loadReservationTable(ArrayList<Reservation> reservations){
        Object[] columnReservation = {"ID","Müşteri Adı","Müşteri Telefon","Giriş Tarihi","Çıkış Tarihi","Yetişkin Sayısı","Çocuk Sayısı","Toplam Fiyat"};

        if(reservations == null){
            reservations = this.reservationController.findAll();
        }

        //tablo sıfırlama
        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_reservation.getModel();
        clearModel.setRowCount(0);

        this.tmdl_reservation.setColumnIdentifiers(columnReservation);
        for(Reservation res:reservations){
            Object[] rowObject = {
                    res.getId(),
                    res.getCustomer_name(),
                    res.getCustomer_contact(),
                    res.getCheck_in_date(),
                    res.getCheck_out_date(),
                    res.getAdults(),
                    res.getChildren(),
                    res.getTotal_price()
            };
            this.tmdl_reservation.addRow(rowObject);
        }

        this.tbl_reservation.setModel(tmdl_reservation);
        this.tbl_reservation.getTableHeader().setReorderingAllowed(false);
        this.tbl_reservation.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_reservation.setEnabled(false);
    }
    private void loadSeasonPopupMenu(){
        this.tbl_season.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_season.rowAtPoint(e.getPoint());
                tbl_season.setRowSelectionInterval(selectedRow,selectedRow);
            }
        });

        this.popup_season.add("Güncelle").addActionListener(e -> {
            int selectId = Integer.parseInt(tbl_season.getValueAt(tbl_season.getSelectedRow(),0).toString());
            SeasonUI seasonUI = new SeasonUI(this.seasonController.getById(selectId));
            seasonUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadSeasonTable(null);
                    loadRoomTable(null);
                }
            });
        });

        this.popup_season.add("Sil").addActionListener(e -> {
            int selectId = Integer.parseInt(tbl_season.getValueAt(tbl_season.getSelectedRow(),0).toString());
            if(Helper.confirm("sure")){
                if(this.seasonController.delete(selectId)){
                    Helper.showMsg("done");
                    loadSeasonTable(null);
                }else {
                    Helper.showMsg("error");
                }
            }
        });

        this.tbl_season.setComponentPopupMenu(popup_season);
    }

    private void loadSeasonButtonEvent(){
        this.btn_season_filter.addActionListener(e -> {
            ArrayList<Season> filteredSeasons= this.seasonController.filter(this.fld_season_filter.getText());
            loadSeasonTable(filteredSeasons);
        });

        this.btn_season_reset.addActionListener(e -> {
            this.fld_season_filter.setText(null);
            loadSeasonTable(null);
        });

        this.btn_season_new.addActionListener(e -> {
            SeasonUI seasonUI = new SeasonUI(new Season());
            seasonUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadSeasonTable(null);
                }
            });
        });
    }

    private void loadSeasonTable(ArrayList<Season> seasons){
        Object[] columnSeason = {"ID","Otel Adı","Başlangıç Tarihi","Bitiş Tarihi"};

        if(seasons == null){
            seasons = this.seasonController.findAll();
        }

        //tablo sıfırlama
        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_season.getModel();
        clearModel.setRowCount(0);

        this.tmdl_season.setColumnIdentifiers(columnSeason);
        for(Season season: seasons){
            Hotel hotel = hotelController.getById(season.getHotel_id());
            Object[] rowObject = {
                    season.getId(),
                    hotel.getName(),
                    season.getStart_date().toString(),
                    season.getEnd_date().toString()
            };
            this.tmdl_season.addRow(rowObject);
        }

        this.tbl_season.setModel(tmdl_season);
        this.tbl_season.getTableHeader().setReorderingAllowed(false);
        this.tbl_season.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_season.setEnabled(false);
    }

    private void loadRoomPopupMenu(){
        this.tbl_room.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_room.rowAtPoint(e.getPoint());
                tbl_room.setRowSelectionInterval(selectedRow,selectedRow);
            }
        });

        this.popup_room.add("Güncelle").addActionListener(e -> {
            int selectId = Integer.parseInt(tbl_room.getValueAt(tbl_room.getSelectedRow(),0).toString());
            RoomUI roomUI = new RoomUI(this.roomController.getById(selectId));
            roomUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRoomTable(null);
                }
            });
        });

        this.popup_room.add("Sil").addActionListener(e -> {
            int selectId = Integer.parseInt(tbl_room.getValueAt(tbl_room.getSelectedRow(),0).toString());
            if(Helper.confirm("sure")){
                if(this.roomController.delete(selectId)){
                    Helper.showMsg("done");
                    loadRoomTable(null);
                }else {
                    Helper.showMsg("error");
                }
            }
        });

        this.popup_room.add("Rezervasyon Yap").addActionListener(e -> {
            int selectId = Integer.parseInt(tbl_room.getValueAt(tbl_room.getSelectedRow(),0).toString());
            Reservation reservation = new Reservation();
            reservation.setRoom_id(selectId);
            ReservationUI reservationUI = new ReservationUI(reservation);
            reservationUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRoomTable(null);
                    loadReservationTable(null);
                }
            });
        });

        this.tbl_room.setComponentPopupMenu(popup_room);

    }

    private void loadRoomButtonEvent(){
        this.btn_room_filter.addActionListener(e -> {
            ArrayList<Room> filteredRooms = this.roomController.filter(this.fld_room_filter.getText());
            loadRoomTable(filteredRooms);
        });


        this.btn_room_reset.addActionListener(e -> {
            this.fld_room_filter.setText(null);
            loadRoomTable(null);
        });
        this.btn_room_new.addActionListener(e -> {
            RoomUI roomUI = new RoomUI(new Room());
            roomUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRoomTable(null);
                }
            });
        });
    }

    private void loadRoomTable(ArrayList<Room> rooms){
        Object[] columnRoom = {"ID","Otel Adı","Pansiyon Tipi","Sezon","Yetişkin Fiyat","Çocuk Fiyat","Kapasite","Özellikler","Stok"};

        if(rooms == null){
            rooms = this.roomController.findAll();
        }

        //tablo sıfırlama
        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_room.getModel();
        clearModel.setRowCount(0);

        this.tmdl_room.setColumnIdentifiers(columnRoom);
        for(Room room :rooms){
            StringBuilder stf = new StringBuilder();
            if(room.isTv()){stf.append("TV ");}
            if(room.isMinibar()){stf.append("Minibar ");}
            StringBuilder seasonDate = new StringBuilder();
            seasonDate.append(room.getSeason().getStart_date());
            seasonDate.append("/");
            seasonDate.append(room.getSeason().getEnd_date());

            Object[] rowObject = {
                    room.getId(),
                    room.getHotel().getName(),
                    room.getPensionType().getName(),
                    seasonDate,
                    room.getAdultPrice(),
                    room.getKidPrice(),
                    room.getCapacity(),
                    stf,
                    room.getStock()
            };
            this.tmdl_room.addRow(rowObject);
        }

        this.tbl_room.setModel(tmdl_room);
        this.tbl_room.getTableHeader().setReorderingAllowed(false);
        this.tbl_room.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_room.setEnabled(false);
    }

    private void loadHotelButtonEvent() {
        this.btn_otel_filter.addActionListener(e -> {
            ArrayList<Hotel> filteredHotels = this.hotelController.filter(
                    this.fld_otel_name_filter.getText(),
                    this.fld_otel_city_filter.getText()
            );
            loadHotelTable(filteredHotels);
        });

        this.btn_otel_reset.addActionListener(e -> {
            loadHotelTable(null);
            this.fld_otel_city_filter.setText(null);
            this.fld_otel_name_filter.setText(null);
        });

        this.btn_otel_new.addActionListener(e -> {
            HotelUI hotelUI = new HotelUI(new Hotel());
            hotelUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelTable(null);
                }
            });
        });
    }

    private void loadHotelPopupMenu() {
        this.tbl_hotel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_hotel.rowAtPoint(e.getPoint());
                tbl_hotel.setRowSelectionInterval(selectedRow,selectedRow);
            }
        });

        this.popup_hotel.add("Güncelle").addActionListener(e -> {
            int selectId = Integer.parseInt(tbl_hotel.getValueAt(tbl_hotel.getSelectedRow(),0).toString());
            HotelUI hotelUI = new HotelUI(this.hotelController.getById(selectId));
            hotelUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelTable(null);
                }
            });
        });

        this.popup_hotel.add("Sil").addActionListener(e -> {
            int selectId = Integer.parseInt(tbl_hotel.getValueAt(tbl_hotel.getSelectedRow(),0).toString());
            if(Helper.confirm("sure")){
                if(this.hotelController.delete(selectId)){
                    Helper.showMsg("done");
                    loadHotelTable(null);
                }else {
                    Helper.showMsg("error");
                }
            }
        });

        this.tbl_hotel.setComponentPopupMenu(popup_hotel);
    }

    private void loadHotelTable(ArrayList<Hotel> hotels) {
        Object[] columnHotel = {"ID","Otel Adı","Şehir","Bölge","Mail","Telefon","Yıldız","Tesis Özellikleri","Pansiyon Tipleri"};

        if(hotels == null){
            hotels = this.hotelController.findAll();
        }

        //tablo sıfırlama
        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_hotel.getModel();
        clearModel.setRowCount(0);

        this.tmdl_hotel.setColumnIdentifiers(columnHotel);
        for(Hotel hotel:hotels){
            StringBuilder stf = new StringBuilder();
            if(hotel.isCarpark()){stf.append("Otopark ");}
            if(hotel.isSpa()){stf.append("Spa ");}
            if(hotel.isRoomservice()){stf.append("Oda Servisi");}

            StringBuilder stp = new StringBuilder();
            for(PensionType pt: hotel.getPension_types()){
                stp.append(pt.getName()+" ");
            }

            Object[] rowObject = {
                hotel.getId(),
                hotel.getName(),
                hotel.getCity(),
                hotel.getRegion(),
                hotel.getEmail(),
                hotel.getPhone(),
                hotel.getStar(),
                stf,
                stp
            };
            this.tmdl_hotel.addRow(rowObject);
        }

        this.tbl_hotel.setModel(tmdl_hotel);
        this.tbl_hotel.getTableHeader().setReorderingAllowed(false);
        this.tbl_hotel.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_hotel.setEnabled(false);

    }
}
