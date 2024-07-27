package view;

import business.HotelController;
import business.SeasonController;
import core.Helper;
import entity.Hotel;
import entity.Season;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class SeasonUI extends JFrame {
    private JPanel container;
    private JComboBox cmb_season_hotel;
    private JButton btn_season_save;
    private JLabel lbl_title;
    private JFormattedTextField fld_season_startdate;
    private JFormattedTextField fld_season_enddate;
    private Season season;
    private SeasonController seasonController;
    private HotelController hotelController;

    public SeasonUI(Season season){
        this.season = season;
        this.seasonController = new SeasonController();
        this.hotelController = new HotelController();

        this.add(container);
        this.setTitle("Turizm Acenta Sistemi");
        this.setSize(300,300);
        this.setVisible(true);

        int x = (Toolkit.getDefaultToolkit().getScreenSize().width-this.getSize().width)/2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height-this.getSize().height)/2;
        this.setLocation(x,y);

        //cmbhotel
        DefaultComboBoxModel mdl_hotel = new DefaultComboBoxModel();
        ArrayList<Hotel> hotels = this.hotelController.findAll();
        for(Hotel hotel: hotels){mdl_hotel.addElement(hotel.getName());}
        this.cmb_season_hotel.setModel(mdl_hotel);
        cmb_season_hotel.setSelectedItem(null);

        if(season.getId() == 0){
            lbl_title.setText("Sezon Ekle");
        }else {
            lbl_title.setText("Sezon Düzenle");
            this.cmb_season_hotel.setSelectedItem(hotelController.getById(season.getHotel_id()).getName());
            this.fld_season_startdate.setText(season.getStart_date().toString());
            this.fld_season_enddate.setText(season.getEnd_date().toString());
        }

        this.btn_season_save.addActionListener(e -> {
            JTextField[] checkList = {this.fld_season_startdate,this.fld_season_enddate};
            if(Helper.isFieldEmpty(checkList) || this.cmb_season_hotel.getSelectedItem() == null){
                Helper.showMsg("fill");
            }else {
                boolean result = false;
                Hotel hotel = this.hotelController.getByName(cmb_season_hotel.getSelectedItem().toString());
                this.season.setHotel_id(hotel.getId());
                this.season.setStart_date(LocalDate.parse(fld_season_startdate.getText()));
                this.season.setEnd_date(LocalDate.parse(fld_season_enddate.getText()));

                if(this.season.getId() == 0){
                    result = this.seasonController.save(season);
                }else {
                    result = this.seasonController.update(season);
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
}
