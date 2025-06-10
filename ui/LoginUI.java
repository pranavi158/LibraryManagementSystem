package ui;

import javax.swing.*;
import dao.UserDAO;
import connect.DBConnection;

import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginUI extends JFrame {
    JTextField usernameField;
    JPasswordField passwordField;
    JComboBox<String> roleBox;

    public LoginUI() {
        setTitle("Library Login");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JLabel roleLabel = new JLabel("Role:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        roleBox = new JComboBox<>(new String[] { "ADMIN", "MEMBER" });
        JButton loginBtn = new JButton("Login");

        add(userLabel);
        add(usernameField);
        add(passLabel);
        add(passwordField);
        add(roleLabel);
        add(roleBox);
        add(new JLabel());  
        add(loginBtn);


        loginBtn.addActionListener(e -> loginUser());
        
        setLocationRelativeTo(null);
        setVisible(true);
        
    }

    private void loginUser() {
        String username = usernameField.getText().trim().toLowerCase();
        String password = new String(passwordField.getPassword()).trim();
        String role = roleBox.getSelectedItem().toString();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            UserDAO userDAO = new UserDAO(conn);

            if (userDAO.isValidUser(username, password, role)) {
                dispose();  

                if (role.equals("ADMIN")) {
                    new AdminUI(conn).setVisible(true);
                } else {
                    String memberId = userDAO.getMemberIdByUsername(username);
                    if (memberId != null) {
                        new MemberUI(conn, memberId).setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(this, "Member ID not found for user.", "Error", JOptionPane.ERROR_MESSAGE);
                        conn.close();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials or role.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                if (conn != null) conn.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginUI::new);
    }
}
