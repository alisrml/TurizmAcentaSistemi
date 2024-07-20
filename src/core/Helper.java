package core;

import javax.swing.*;

public class Helper {
    public static boolean isFieldEmpty(JTextField field){
        return field.getText().trim().isEmpty();
    }

    public static boolean isFieldEmpty(JTextField[] fields){
        for(JTextField field:fields){
            if(isFieldEmpty(field)) return true;
        }
        return false;
    }

    public static boolean isEmailValid(String mail){
        //kontrol edilecek durumlar
        //@ içerercek, @ten önce ve sonrabirdeğer içerecek, @ten sonra nokta ve bir değer olacak.
        if(mail == null || mail.trim().isEmpty()) return false;

        if(!mail.contains("@")) return false;

        String[] parts = mail.split("@");

        if (parts[0].trim().isEmpty()||parts[1].trim().isEmpty()) return false;

        if(!parts[1].contains(".")) return false;

        return true;
    }

    public static void optionPaneDialogTR(){
        UIManager.put("OptionPane.okButtonText","Tamam");
        UIManager.put("OptionPane.yesButtonText","Evet");
        UIManager.put("OptionPane.noButtonText","Hayır");
    }

    public static void showMsg(String message){
        String msg;
        String title;

        optionPaneDialogTR();
        switch (message) {
            case "fill" -> {
                msg = "Lütfen tüm alanları doldurunuz!";
                title = "HATA!";
            }
            case "done" -> {
                msg = "İşlem Başarılı !";
                title = "Sonuç";
            }
            case "error" -> {
                msg = "Bir hata oluştu !";
                title = "HATA!";
            }
            default -> {
                msg = message;
                title = "Mesaj";
            }
        }
        JOptionPane.showMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String str) {
        optionPaneDialogTR();
        String msg;
        if(str.equals("sure")){
            msg = "Bu işlemi gerçekleştirmek istediğinize emin  misiniz?";
        } else {
            msg = str;
        }

        return JOptionPane.showConfirmDialog(null,msg,"Emin misin ?",JOptionPane.YES_NO_OPTION) == 0;
    }
}
