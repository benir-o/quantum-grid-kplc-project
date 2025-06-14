package PageUI;

import SystemFunctionality.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ButtonListener implements ActionListener {
    protected User commonUser;
    protected JTextField nameField;
    protected JTextField emailField;
    protected JPasswordField passwordField;

    public static int findUserCount(User newUser){
        return newUser.getCount();
    }
    public ButtonListener(JTextField nameField, JTextField emailField, JPasswordField passwordField,User u1){
        this.nameField =nameField;
        this.emailField=emailField;
        this.passwordField=passwordField;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String name=nameField.getText();
        String email=emailField.getText();
        String password=new String(passwordField.getPassword());
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/user_db",
                    "root",
                    "ManCity@254"
            );
            String sql="INSERT INTO users (name,email,password) VALUES (?,?,?)";
            PreparedStatement statement=conn.prepareStatement(sql);
            statement.setString(1,name);
            statement.setString(2,email);
            statement.setString(3,password);
            //We then add the user to the database
            statement.executeUpdate();

            //System.out.println(findUserCount(newUser));
            JOptionPane.showMessageDialog(null,"SystemFunctionality.User "+name+" saved successfully");
            //We then close the connection
            statement.close();
            conn.close();

            } catch (Exception ex) {
                System.out.println("An error occurred in connection");


        }

    }
//    public void LoginPerformed(ActionEvent e){
//
//        String email=emailField.getText();
//        String password=new String(passwordField.getPassword());
//    }
}
