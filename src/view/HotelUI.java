package view;

import business.HotelController;
import core.Helper;
import entity.Hotel;
import entity.PensionType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class HotelUI extends JFrame{
    private JPanel container;
    private JTextField fld_hotel_name;
    private JTextField fld_hotel_city;
    private JTextField fld_hotel_region;
    private JTextField fld_hotel_mail;
    private JTextField fld_hotel_phone;
    private JComboBox<Integer> cmb_hotel_star;
    private JTextArea txt_hotel_adress;
    private JCheckBox chcbx_carpark;
    private JCheckBox chcbx_roomservice;
    private JCheckBox chcbx_spa;
    private JCheckBox ultraHerŞeyDahilCheckBox;
    private JCheckBox herŞeyDahilCheckBox;
    private JCheckBox odaKahvaltıCheckBox;
    private JCheckBox tamPansiyonCheckBox;
    private JCheckBox yarımPansiyonCheckBox;
    private JCheckBox sadeceYatakCheckBox;
    private JCheckBox alkolHariçFullCreditCheckBox;
    private JLabel lbl_title;
    private JButton btn_hotel_save;
    private JTextField fld_hotel_star;
    private Hotel hotel;
    private HotelController hotelController;

    public HotelUI(Hotel hotel){
        this.hotel = hotel;
        this.hotelController = new HotelController();

        this.add(container);
        this.setTitle("Turizm Acenta Sistemi");
        this.setSize(400,600);
        this.setVisible(true);

        int x = (Toolkit.getDefaultToolkit().getScreenSize().width-this.getSize().width)/2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height-this.getSize().height)/2;
        this.setLocation(x,y);

        if(hotel.getId() == 0){
            lbl_title.setText("Otel Ekle");
        } else {
            lbl_title.setText("Otel Düzenle");
            this.fld_hotel_name.setText(this.hotel.getName());
            this.fld_hotel_city.setText(this.hotel.getCity());
            this.fld_hotel_region.setText(this.hotel.getRegion());
            this.fld_hotel_mail.setText(this.hotel.getEmail());
            this.fld_hotel_phone.setText(this.hotel.getPhone());
            this.txt_hotel_adress.setText(this.hotel.getAddress());
            this.fld_hotel_star.setText(String.valueOf(this.hotel.getStar()));

            this.chcbx_carpark.setSelected(this.hotel.isCarpark());
            this.chcbx_roomservice.setSelected(this.hotel.isRoomservice());
            this.chcbx_spa.setSelected(this.hotel.isSpa());

            ArrayList<PensionType> pts = (ArrayList<PensionType>) this.hotel.getPension_types();
            for(PensionType pt:pts){
                if(Objects.equals(pt.getName(), "Ultra Her Şey Dahil")){this.ultraHerŞeyDahilCheckBox.setSelected(true);}
                else if (Objects.equals(pt.getName(), "Her Şey Dahil")) {this.herŞeyDahilCheckBox.setSelected(true);}
                else if(Objects.equals(pt.getName(), "Oda Kahvaltı")){this.odaKahvaltıCheckBox.setSelected(true);}
                else if(Objects.equals(pt.getName(), "Tam Pansiyon")){this.tamPansiyonCheckBox.setSelected(true);}
                else if(Objects.equals(pt.getName(), "Yarım Pansiyon")) {this.yarımPansiyonCheckBox.setSelected(true);}
                else if (Objects.equals(pt.getName(), "Sadece Yatak")){this.sadeceYatakCheckBox.setSelected(true);}
                else if (Objects.equals(pt.getName(), "Alkol Hariç Full Credit")){this.alkolHariçFullCreditCheckBox.setSelected(true);}
            }
        }


        btn_hotel_save.addActionListener(e -> {
            JTextField[] checkList = {this.fld_hotel_name,this.fld_hotel_city,this.fld_hotel_region,this.fld_hotel_phone,this.fld_hotel_mail,this.fld_hotel_star};
            if(Helper.isFieldEmpty(checkList)){
                Helper.showMsg("fill");
            } else if (!Helper.isEmailValid(this.fld_hotel_mail.getText())) {
                Helper.showMsg("Lütfen Geçerli bir e-posta adresi giriniz !");
            }else {
                boolean result = false;
                this.hotel.setName(this.fld_hotel_name.getText());
                this.hotel.setCity(this.fld_hotel_city.getText());
                this.hotel.setRegion(this.fld_hotel_region.getText());
                this.hotel.setEmail(this.fld_hotel_mail.getText());
                this.hotel.setPhone(this.fld_hotel_phone.getText());
                this.hotel.setStar(Integer.parseInt(this.fld_hotel_star.getText()));
                this.hotel.setAddress(this.txt_hotel_adress.getText());

                this.hotel.setCarpark(this.chcbx_carpark.isSelected());
                this.hotel.setRoomservice(this.chcbx_roomservice.isSelected());
                this.hotel.setSpa(this.chcbx_spa.isSelected());

                //pansiyon checkboxları için isselected ifleri yapıp pansiyonları yazdırma islemi
                ArrayList<String> pension_types = new ArrayList<>();
                if(this.ultraHerŞeyDahilCheckBox.isSelected()){pension_types.add("Ultra Her Şey Dahil");}
                if(this.herŞeyDahilCheckBox.isSelected()){pension_types.add("Her Şey Dahil");}
                if(this.odaKahvaltıCheckBox.isSelected()){pension_types.add("Oda Kahvaltı");}
                if(this.tamPansiyonCheckBox.isSelected()){pension_types.add("Tam Pansiyon");}
                if(this.yarımPansiyonCheckBox.isSelected()){pension_types.add("Yarım Pansiyon");}
                if(this.sadeceYatakCheckBox.isSelected()){pension_types.add("Sadece Yatak");}
                if(this.alkolHariçFullCreditCheckBox.isSelected()){pension_types.add("Alkol Hariç Full Credit");}

                ArrayList<PensionType> pensionTypes = new ArrayList<>();
                for(String ptname:pension_types){
                    PensionType pt = new PensionType(ptname);
                    pensionTypes.add(pt);
                }
                this.hotel.setPension_types(pensionTypes);

                if(this.hotel.getId() == 0){
                    result = this.hotelController.save(this.hotel);
                }else {
                    result = this.hotelController.update(this.hotel);
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
}
