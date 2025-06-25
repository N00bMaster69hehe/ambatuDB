package com.mycompany.mavenproject3;

import javax.swing.*;
import java.sql.*;

public class Login extends JDialog {
    private JTextField UsernameField;
    private JPasswordField PasswordField;
    private JButton LoginButton;
    private Mavenproject3 parent;  // tambahkan ini

    public Login(Mavenproject3 parent) {
        super(parent, "Login", true);  // gunakan JDialog, bukan JFrame
        this.parent = parent;

        setSize(300, 180);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel labelUser = new JLabel("Username:");
        labelUser.setBounds(30, 20, 80, 25);
        add(labelUser);

        UsernameField = new JTextField();
        UsernameField.setBounds(110, 20, 140, 25);
        add(UsernameField);

        JLabel labelPassword = new JLabel("Password:");
        labelPassword.setBounds(30, 55, 80, 25);
        add(labelPassword);

        PasswordField = new JPasswordField();
        PasswordField.setBounds(110, 55, 140, 25);
        add(PasswordField);

        LoginButton = new JButton("Login");
        LoginButton.setBounds(100, 95, 80, 30);
        add(LoginButton);

        LoginButton.addActionListener(e -> checkLogin());
    }

    private void checkLogin() {
        String username = UsernameField.getText();
        String password = String.valueOf(PasswordField.getPassword());

        try (Connection conn = db.connect()) {
            String sql = "SELECT * FROM admin WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Session.username = rs.getString("username");
                JOptionPane.showMessageDialog(this, "Login berhasil, Selamat Datang Tuan " + Session.username);

                parent.onLoginSuccess(username);  // panggil parent method
                dispose(); // tutup login dialog
            } else {
                JOptionPane.showMessageDialog(this, "Username atau password salah.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
