package view;

import business.UserController;
import core.Helper;
import entity.User;

import javax.swing.*;
import java.awt.*;

public class UserUI extends JFrame{
    private JPanel container;
    private JTextField fld_user_name;
    private JPasswordField fld_user_password;
    private JComboBox cmb_user_type;
    private JButton btn_user_save;
    private JLabel lbl_title;
    private JTextField fld_user_mail;
    private User user;
    private UserController userController;

    public UserUI(User user){
        this.user = user;
        this.userController = new UserController();

        this.add(container);
        this.setTitle("Müşteri Ekle/Düzenle");
        this.setSize(300,500);

        int x = (Toolkit.getDefaultToolkit().getScreenSize().width-this.getSize().width)/2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height-this.getSize().height)/2;

        this.setLocation(x,y);
        this.setVisible(true);

        this.cmb_user_type.setModel(new DefaultComboBoxModel<>(User.TYPE.values()));
        
        if(this.user.getId() ==0){
            this.lbl_title.setText("Müşteri Ekle");
        }else {
            this.lbl_title.setText("Müşteri Güncelle");
            this.fld_user_name.setText(this.user.getName());
            this.fld_user_mail.setText(this.user.getEmail());
            this.fld_user_password.setText(this.user.getPassword());
            this.cmb_user_type.getModel().setSelectedItem(this.user.getType());
        }
        
        this.btn_user_save.addActionListener(e -> {
            JTextField[] checkList = {this.fld_user_name,this.fld_user_mail,this.fld_user_password};
            if(Helper.isFieldEmpty(checkList)){
                Helper.showMsg("fill");
            } else if (!Helper.isEmailValid(this.fld_user_mail.getText())){
                Helper.showMsg("Lütfen geçerli bir e-posta adresi giriniz !");
            } else {
                boolean result = false;
                this.user.setName(this.fld_user_name.getText());
                this.user.setPassword(this.fld_user_password.getText());
                this.user.setEmail(this.fld_user_mail.getText());
                this.user.setType((User.TYPE) this.cmb_user_type.getSelectedItem());

                if(this.user.getId() == 0){
                    result = this.userController.save(this.user);
                }else {
                    result = this.userController.update(this.user);
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
