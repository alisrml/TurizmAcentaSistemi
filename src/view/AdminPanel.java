package view;

import business.UserController;
import core.Helper;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AdminPanel extends JFrame {

    private JLabel lbl_title;
    private JPanel container;
    private JTable tbl_user;
    private JScrollPane scrll_user;
    private JPanel pnl_user;
    private JPanel pnl_filter_user;
    private JTextField fld_f_user_name;
    private JComboBox cmb_f_user_type;
    private JButton btn_filter_user;
    private JButton btn_filter_reset_user;
    private JButton btn_user_new;
    private JButton btn_logout;
    private UserController userController;
    private DefaultTableModel tmdl_user = new DefaultTableModel();
    private JPopupMenu popup_user = new JPopupMenu();

    public AdminPanel(){
        this.userController = new UserController();

        btn_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginUI loginUI = new LoginUI();
            }
        });

        loadUserTable(null);
        loadUserPopupMenu();
        LoadUserButtonEvent();

        this.add(container);
        this.setTitle("Turizm Acenta Sistemi");
        this.setSize(600,400);
        this.setVisible(true);

        int x = (Toolkit.getDefaultToolkit().getScreenSize().width-this.getSize().width)/2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height-this.getSize().height)/2;
        this.setLocation(x,y);

    }

    private void LoadUserButtonEvent() {
        this.btn_user_new.addActionListener(e -> {
            UserUI userUI = new UserUI(new User());
            userUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadUserTable(null);
                }
            });
        });
    }

    private void loadUserPopupMenu() {
        this.tbl_user.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_user.rowAtPoint(e.getPoint());
                tbl_user.setRowSelectionInterval(selectedRow,selectedRow);
            }
        });

        this.popup_user.add("Güncelle").addActionListener(e -> {
            int selectId = Integer.parseInt(tbl_user.getValueAt(tbl_user.getSelectedRow(),0).toString());
            UserUI userUI = new UserUI(this.userController.getByID(selectId));
            userUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadUserTable(null);
                }
            });
        });

        this.popup_user.add("Sil").addActionListener(e -> {
            int selectId = Integer.parseInt(tbl_user.getValueAt(tbl_user.getSelectedRow(),0).toString());
            if(Helper.confirm("Sure")){
                if(this.userController.delete(selectId)){
                    Helper.showMsg("done");
                    loadUserTable(null);
                }else {
                    Helper.showMsg("error");
                }
            }
        });

        this.tbl_user.setComponentPopupMenu(popup_user);
    }

    private void loadUserTable(ArrayList<User> users) {
        Object[] columnUser = {"ID","İsim","Kullanıcı Tipi","Mail"};

        if(users == null){
            users = this.userController.findAll();
        }

        //tablo sıfırlama
        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_user.getModel();
        clearModel.setRowCount(0);

        this.tmdl_user.setColumnIdentifiers(columnUser);
        for(User user:users){
            Object[] rowObject = {
                    user.getId(),
                    user.getName(),
                    user.getType(),
                    user.getEmail()
            };
            this.tmdl_user.addRow(rowObject);
        }

        this.tbl_user.setModel(tmdl_user);
        this.tbl_user.getTableHeader().setReorderingAllowed(false);
        this.tbl_user.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_user.setEnabled(false);
    }
}
