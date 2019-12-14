package edu.auburn.mvc;

import com.google.gson.Gson;
import edu.auburn.mvc.model.*;
import edu.auburn.mvc.view.StoreManagerView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class LoginUI {

    public StoreManagerMain storeManagerMain;
    public JFrame view;

    public JButton btnLogin = new JButton("Login");
    public JButton btnLogout = new JButton("Logout");

    public JTextField txtUsername = new JTextField(20);
    public JTextField txtPassword = new JPasswordField(20);

    Socket pipe;
    Scanner input;
    PrintWriter output;

    int accessToken;
    UserModel user = null;

    public LoginUI() {

        String title = "Store Manager Login Screen";
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle(title);
        view.setSize(600, 400);

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel line = new JPanel();

        // Set window's title.
        JLabel windowTitle = new JLabel (title);
        windowTitle.setFont(windowTitle.getFont().deriveFont(20.0f));
        line.add(windowTitle);
        pane.add(line);

        line = new JPanel();
        line.add(new JLabel("Username"));
        line.add(txtUsername);
        pane.add(line);

        line = new JPanel();
        line.add(new JLabel("Password"));
        line.add(txtPassword);
        pane.add(line);

        line = new JPanel();
        line.add(btnLogin);
        line.add(btnLogout);
        pane.add(line);

        btnLogin.addActionListener(new LoginActionListener());
        btnLogout.addActionListener(new LogoutActionListener());

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        LoginUI ui = new LoginUI();

        // Center the window in the computer screen.
        ui.view.setLocationRelativeTo(null);

        // Make the window visible.
        ui.view.setVisible(true);
    }

    private class LoginActionListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            String loginInfo = null;
            user = new UserModel();
            user.mUsername = txtUsername.getText();
            user.mPassword = txtPassword.getText();

            if (user.mUsername.length() == 0 || user.mPassword.length() == 0) {
                JOptionPane.showMessageDialog(null, "Username or password cannot be null!");
                return;
            }

            // Do client/server LOGIN action.
            try {
                // Gson is a Java library that can be used to convert Java Objects into their JSON representation.
                Gson gson = new Gson();

                // Establish the connection with the server.
                pipe = new Socket("localhost", 1000);
                input = new Scanner(pipe.getInputStream());
                output = new PrintWriter(pipe.getOutputStream(), true);

                // Send the LOGIN request to the server.
                MessageModel message = new MessageModel();
                message.code = MessageModel.LOGIN;
                message.data = gson.toJson(user);
                output.println(gson.toJson(message));
                System.out.println("Sending the LOGIN request to the server " + message.code + "\t" + message.data);

                // Retrieve the whole user and login data all at once.
                System.out.println("Receiving back from the Server the user and login data...");
                message = gson.fromJson(input.nextLine(), MessageModel.class);

                if (message.code == MessageModel.OPERATION_FAILED) {
                    JOptionPane.showMessageDialog(null, "Invalid username or password! Access denied!");
                } else {
                    accessToken = message.ssid;
                    user = gson.fromJson(message.data, UserModel.class);

                    System.out.println("User = " + user);
                    System.out.println("\tmUsername = " + user.mUsername);
                    System.out.println("\tmPassword = " + user.mPassword);
                    System.out.println("\tmFullname = " + user.mFullname);
                    System.out.println("\tmUserType = " + user.mUserType);
                    System.out.println("\tmCustomerID = " + user.mCustomerID);

                    if (user.mUserType == UserModel.ADMIN) {
                        loginInfo = "Access granted with access token = " + accessToken + ". User " + user.mUsername + " is ADMIN";
                    } else if (user.mUserType == UserModel.MANAGER) {
                        loginInfo = "Access granted with access token = " + accessToken + ". User " + user.mUsername + " is MANAGER";
                    } else if (user.mUserType == UserModel.CASHIER) {
                        loginInfo = "Access granted with access token = " + accessToken + ". User " + user.mUsername + " is CASHIER";
                    } else if (user.mUserType == UserModel.CUSTOMER) {
                        loginInfo = "Access granted with access token = " + accessToken + ". User " + user.mUsername + " is CUSTOMER";
                    } else {
                        JOptionPane.showMessageDialog(null, "Usertype " + user.mUserType + " NOT supported!");
                        System.exit(-1);
                    }

                    // Launch the Store Manager main application.
                    storeManagerMain = new StoreManagerMain("SQLite", loginInfo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class LogoutActionListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {

            // Do client/server LOGOUT action.
            try {
                // Gson is a Java library that can be used to convert Java Objects into their JSON representation.
                Gson gson = new Gson();

                // Establish the connection with the server.
                pipe = new Socket("localhost", 1000);
                input = new Scanner(pipe.getInputStream());
                output = new PrintWriter(pipe.getOutputStream(), true);

                // Send the LOGOUT request to the server.
                MessageModel message = new MessageModel();
                message.code = MessageModel.LOGOUT;
                message.data = Integer.toString(accessToken);
                output.println(gson.toJson(message));
                System.out.println("Sending the LOGOUT request to the server for user" + user.mUsername + " with accessToken " + accessToken + "...");

                // Receive the response of the LOGOUT request.
                int logoutResponse = input.nextInt();
                System.out.println("Receiving the response of the LOGOUT request " + logoutResponse);

                if (logoutResponse == 0) {
                    JOptionPane.showMessageDialog(null, "Invalid token for logout!");
                } else {

                    // As the logout was successfully, clear the GUI.
                    txtUsername.setText("");
                    txtPassword.setText("");

                    // Close the Store Manager GUI.
                    storeManagerMain.apView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    storeManagerMain.apView.setVisible(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}