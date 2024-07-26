package view;

import business.HotelController;
import business.RoomController;
import business.SeasonController;
import core.Helper;
import entity.Hotel;
import entity.PensionType;
import entity.Room;
import entity.Season;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RoomUI extends JFrame {
    private Room room;
    private JPanel container;
    private JComboBox cmb_room_hotel;
    private JComboBox cmb_room_pension;
    private JComboBox cmb_room_type;
    private JTextField fld_adult_price;
    private JTextField fld_child_price;
    private JTextField fld_bed;
    private JTextField fld_stock;
    private JLabel lbl_title;
    private JCheckBox chck_tv;
    private JCheckBox chck_minibar;
    private JButton btn_room_save;
    private JComboBox cmb_room_season;
    private RoomController roomController;
    private HotelController hotelController;
    private SeasonController seasonController;

    public RoomUI(Room room){
        this.room = room;
        this.roomController = new RoomController();
        this.hotelController = new HotelController();
        this.seasonController = new SeasonController();

        this.add(container);
        this.setTitle("Turizm Acenta Sistemi");
        this.setSize(400,400);
        this.setVisible(true);

        int x = (Toolkit.getDefaultToolkit().getScreenSize().width-this.getSize().width)/2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height-this.getSize().height)/2;
        this.setLocation(x,y);

        loadComboBoxes();

        if(room.getId() == 0){
            lbl_title.setText("Oda Ekle");
        }else {
            lbl_title.setText("Oda Düzenle");
            this.cmb_room_hotel.setSelectedItem(room.getHotel().getName());
            this.cmb_room_type.setSelectedItem(room.getType().toString());
            this.cmb_room_pension.setSelectedItem(room.getPensionType().getName());
            this.cmb_room_season.setSelectedItem(this.room.getSeason().toString());
            this.fld_bed.setText(String.valueOf(room.getBed()));
            this.fld_adult_price.setText(String.valueOf(room.getAdultPrice()));
            this.fld_child_price.setText(String.valueOf(room.getKidPrice()));
            this.fld_stock.setText(String.valueOf(room.getStock()));

            this.chck_tv.setSelected(room.isTv());
            this.chck_minibar.setSelected(room.isMinibar());
        }

        this.btn_room_save.addActionListener(e -> {
            JTextField[] checkList = {this.fld_bed,this.fld_adult_price,this.fld_child_price,this.fld_stock};
            if(Helper.isFieldEmpty(checkList)){
                Helper.showMsg("fill");
            } else if (this.cmb_room_hotel.getSelectedItem() == null || this.cmb_room_pension.getSelectedItem() == null ||this.cmb_room_type.getSelectedItem() == null) {
                Helper.showMsg("fill");
            } else {
                boolean result = false;
                Hotel hotel = this.hotelController.getByName(cmb_room_hotel.getSelectedItem().toString());
                this.room.setHotel(hotel);
                this.room.setType(Room.TYPE.valueOf(cmb_room_type.getSelectedItem().toString()));
                this.room.setPensionType(new PensionType(cmb_room_pension.getSelectedItem().toString()));
                String datestr = cmb_room_season.getSelectedItem().toString();
                String[] dates = datestr.split("/");
                this.room.setSeason(this.seasonController.getByDates(hotel.getId(),dates[0],dates[1]));
                this.room.setBed(Integer.parseInt(fld_bed.getText()));
                this.room.setAdultPrice(Integer.parseInt(fld_adult_price.getText()));
                this.room.setKidPrice(Integer.parseInt(fld_child_price.getText()));
                this.room.setStock(Integer.parseInt(fld_stock.getText()));
                this.room.setTv(chck_tv.isSelected());
                this.room.setMinibar(chck_minibar.isSelected());

                if(this.room.getId() == 0){
                    result = this.roomController.save(room);
                }else {
                    result = this.roomController.update(room);
                }

                if(result){
                    Helper.showMsg("done");
                    dispose();
                }else {
                    Helper.showMsg("error");
                }
            }
        });


    }

    private void loadComboBoxes(){
        //cmbhotel
        DefaultComboBoxModel mdl_hotel = new DefaultComboBoxModel();
        ArrayList<Hotel> hotels = this.hotelController.findAll();
        for(Hotel hotel: hotels){mdl_hotel.addElement(hotel.getName());}
        this.cmb_room_hotel.setModel(mdl_hotel);
        cmb_room_hotel.setSelectedItem(null);

        cmb_room_hotel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //cmbpension
                ArrayList<Hotel> hotels = hotelController.filter((String) cmb_room_hotel.getSelectedItem(),"");
                ArrayList<PensionType> pensions = (ArrayList<PensionType>) hotels.get(0).getPension_types();
                DefaultComboBoxModel mdl_pension = new DefaultComboBoxModel();
                for(PensionType pt:pensions){
                    mdl_pension.addElement(pt.getName());
                }
                cmb_room_pension.setModel(mdl_pension);
                cmb_room_pension.setSelectedItem(null);

                //cmbroomseason
                DefaultComboBoxModel mdl_room_season = new DefaultComboBoxModel<>();
                int hotel_id = hotelController.getByName(cmb_room_hotel.getSelectedItem().toString()).getId();
                for(Season season: seasonController.getByHotelId(hotel_id)){
                    mdl_room_season.addElement(season.toString());
                }
                cmb_room_season.setModel(mdl_room_season);
                cmb_room_season.setSelectedItem(null);
            }
        });
        //cmbroomtype
        DefaultComboBoxModel mdl_room_type = new DefaultComboBoxModel<>();
        for(Room.TYPE type:Room.TYPE.values()){
            mdl_room_type.addElement(type.toString());
        }
        cmb_room_type.setModel(mdl_room_type);
        cmb_room_type.setSelectedItem(null);

    }
}
