package view;

import business.UserController;
import core.Helper;
import entity.User;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {

    private JPanel container;
    private JTextField fld_mail;
    private JPasswordField fld_password;
    private JButton btn_login;
    private JLabel lbl_title;
    private JLabel lbl_mail;
    private JLabel lbl_password;
    private UserController userController;

    public LoginUI(){
        this.userController = new UserController();

        this.add(container);
        this.setTitle("Turizm Acenta Sistemi");
        this.setSize(400,400);
        this.setVisible(true);

        int x = (Toolkit.getDefaultToolkit().getScreenSize().width-this.getSize().width)/2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height-this.getSize().height)/2;
        this.setLocation(x,y);

        this.btn_login.addActionListener(e -> {
            JTextField[] checklist = {this.fld_mail,this.fld_password};
            if(Helper.isFieldEmpty(checklist)){
                //alanları doldur.
                Helper.showMsg("fill");
            }else if(!Helper.isEmailValid(this.fld_mail.getText())) {
                Helper.showMsg("Geçerli bir mail giriniz...");
            }else {
                //giriş işleminin kontrol durumu
                User user = this.userController.findByLogin(this.fld_mail.getText(),this.fld_password.getText());
                if (user.getName() == null){
                    //user nesnesi tanımlanmazsa buraya giriyor.
                    Helper.showMsg("Girdiğiniz bilgilere göre kullanıcı bulunamadı!");
                }else {
                    System.out.println(user.toString());
                    this.dispose();
                    if(user.getType() == User.TYPE.ADMIN){
                        AdminPanel adminPanel = new AdminPanel();
                    }else {
                        EmployeePanel employeePanel = new EmployeePanel();
                    }
                }
            }
        });
    }

}
