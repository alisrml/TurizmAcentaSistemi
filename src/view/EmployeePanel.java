package view;

import business.HotelController;
import business.RoomController;
import core.Helper;
import entity.Hotel;
import entity.PensionType;
import entity.Room;

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
    private JTable table1;
    private HotelController hotelController;
    private RoomController roomController;
    private DefaultTableModel tmdl_hotel = new DefaultTableModel();
    private DefaultTableModel tmdl_room = new DefaultTableModel();
    private  JPopupMenu popup_hotel = new JPopupMenu();
    private  JPopupMenu popup_room = new JPopupMenu();

    public EmployeePanel(){
        this.hotelController = new HotelController();
        this.roomController = new RoomController();

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
