package edu.auburn.mvc;

import com.google.gson.Gson;
import edu.auburn.mvc.model.*;
import edu.auburn.mvc.view.StoreManagerView;

import javax.swing.*;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class ServerStoreManager {
    public static void main(String[] args) {
        HashMap<Integer, UserModel> activeUsers = new HashMap<Integer, UserModel>();
        int totalActiveUsers = 0;

        String dbFile = "store.db";
        String url = "jdbc:sqlite:" + dbFile;
        int port = 1000;

        try {
            // Start the server at port 1000.
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server is listening at port = " + port);

            // Gson is a Java library that can be used to convert Java Objects into their JSON representation.
            Gson gson = new Gson();

            while (true) {

                // Open a "socket pipe" in the server to connect to the client.
                Socket pipe = server.accept();

                // Open an "output stream" to send the data to the client.
                PrintWriter outputStream = new PrintWriter(pipe.getOutputStream(), true);

                // Open an "input stream" to receive the client requests.
                Scanner inputStream = new Scanner(pipe.getInputStream());

                // Convert the JSON message from the client into the MessageModel object.
                MessageModel messageFromClient = gson.fromJson(inputStream.nextLine(), MessageModel.class);

                System.out.println("messageFromClient " + messageFromClient.code + "\t" + messageFromClient.data);

                if (messageFromClient.code == MessageModel.GET_PRODUCT) {
                    ProductModel product = null;

                    // Receive from the Client the data.
                    String receivedDataFromClient = messageFromClient.data;
                    int productID = Integer.parseInt(receivedDataFromClient);
                    System.out.println("Receiving from the Client a GET_PRODUCT request for product with id " + productID + "...");

                    // Create a SQLiteDataAdapter to use the saveProduct() method.
                    SQLiteDataAdapter adapter = new SQLiteDataAdapter();

                    // Open the connection to the database.
                    adapter.connect();

                    // Get ALL the product data based on the productID.
                    product = adapter.loadProduct(productID);

                    // Send back to the client the product data.
                    outputStream.println(gson.toJson(product));

                    // Close the connection to the database.
                    adapter.disconnect();
                }

                if (messageFromClient.code == MessageModel.PUT_PRODUCT) {
                    ProductModel product = null;

                    // Receive from the Client the data.
                    product = gson.fromJson(messageFromClient.data, ProductModel.class);
                    System.out.println("Receiving from the Client a PUT_PRODUCT request with product = " + product);

                    // Create a SQLiteDataAdapter to use the saveProduct() method.
                    SQLiteDataAdapter adapter = new SQLiteDataAdapter();

                    // Open the connection to the database.
                    adapter.connect();

                    // Adding a new product into the database.
                    int addingResult = adapter.saveProduct(product);
                    messageFromClient = new MessageModel();
                    messageFromClient.code = addingResult;
                    messageFromClient.data = "";

                    // Send back to the client the result of adding a new product into the database.
                    outputStream.println(gson.toJson(messageFromClient));

                    // Close the connection to the database.
                    adapter.disconnect();
                }

                if (messageFromClient.code == MessageModel.MODIFY_PRODUCT) {
                    ProductModel product = null;

                    // Receive from the Client the data.
                    product = gson.fromJson(messageFromClient.data, ProductModel.class);
                    System.out.println("Receiving from the Client a MODIFY_PRODUCT request with product = " + product);

                    // Create a SQLiteDataAdapter to use the saveProduct() method.
                    SQLiteDataAdapter adapter = new SQLiteDataAdapter();

                    // Open the connection to the database.
                    adapter.connect();

                    // Updating a product in the database.
                    int updatingResult = adapter.updateProduct(product);
                    messageFromClient = new MessageModel();
                    messageFromClient.code = updatingResult;
                    messageFromClient.data = "";

                    // Send back to the client the result of updating a product in the database.
                    outputStream.println(gson.toJson(messageFromClient));

                    // Close the connection to the database.
                    adapter.disconnect();
                }

                if (messageFromClient.code == MessageModel.GET_CUSTOMER) {
                    CustomerModel customer = null;

                    // Receive from the Client the data.
                    String receivedDataFromClient = messageFromClient.data;
                    int customerID = Integer.parseInt(receivedDataFromClient);
                    System.out.println("Receiving from the Client a GET_CUSTOMER request for customer with id " + customerID + "...");

                    // Create a SQLiteDataAdapter to use the saveProduct() method.
                    SQLiteDataAdapter adapter = new SQLiteDataAdapter();

                    // Open the connection to the database.
                    adapter.connect();

                    // Get ALL the customer data based on the customerID.
                    customer = adapter.loadCustomer(customerID);

                    // Send back to the client the customer data.
                    outputStream.println(gson.toJson(customer));

                    // Close the connection to the database.
                    adapter.disconnect();
                }

                if (messageFromClient.code == MessageModel.PUT_CUSTOMER) {
                    CustomerModel customer = null;

                    // Receive from the Client the data.
                    customer = gson.fromJson(messageFromClient.data, CustomerModel.class);
                    System.out.println("Receiving from the Client a PUT_CUSTOMER request with customer = " + customer);

                    // Create a SQLiteDataAdapter to use the saveCustomer() method.
                    SQLiteDataAdapter adapter = new SQLiteDataAdapter();

                    // Open the connection to the database.
                    adapter.connect();

                    // Adding a new customer into the database.
                    int addingResult = adapter.saveCustomer(customer);
                    messageFromClient = new MessageModel();
                    messageFromClient.code = addingResult;
                    messageFromClient.data = "";

                    // Send back to the client the result of adding a new product into the database.
                    outputStream.println(gson.toJson(messageFromClient));

                    // Close the connection to the database.
                    adapter.disconnect();
                }

                if (messageFromClient.code == MessageModel.MODIFY_CUSTOMER) {
                }

                if (messageFromClient.code == MessageModel.GET_PURCHASE) {
                    PurchaseModel purchase = null;

                    // Receive from the Client the data.
                    String receivedDataFromClient = messageFromClient.data;
                    int purchaseID = Integer.parseInt(receivedDataFromClient);
                    System.out.println("Receiving from the Client a GET_PURCHASE request for purchase with id " + purchaseID + "...");

                    // Create a SQLiteDataAdapter to use the saveProduct() method.
                    SQLiteDataAdapter adapter = new SQLiteDataAdapter();

                    // Open the connection to the database.
                    adapter.connect();

                    // Get ALL the purchase data based on the purchaseID.
                    purchase = adapter.loadPurchase(purchaseID);

                    // Send back to the client the purchase data.
                    outputStream.println(gson.toJson(purchase));

                    // Close the connection to the database.
                    adapter.disconnect();
                }

                if (messageFromClient.code == MessageModel.PUT_PURCHASE) {
                    PurchaseModel purchase = null;

                    // Receive from the Client the data.
                    purchase = gson.fromJson(messageFromClient.data, PurchaseModel.class);
                    System.out.println("Receiving from the Client a PUT_PURCHASE request with purchase = " + purchase);

                    // Create a SQLiteDataAdapter to use the saveProduct() method.
                    SQLiteDataAdapter adapter = new SQLiteDataAdapter();

                    // Open the connection to the database.
                    adapter.connect();

                    // Adding a new purchase into the database.
                    int addingResult = adapter.savePurchase(purchase);
                    messageFromClient = new MessageModel();
                    messageFromClient.code = addingResult;
                    messageFromClient.data = "";

                    // Send back to the client the result of adding a new purchase into the database.
                    outputStream.println(gson.toJson(messageFromClient));

                    // Close the connection to the database.
                    adapter.disconnect();
                }

                if (messageFromClient.code == MessageModel.MODIFY_PURCHASE) {
                }

                if (messageFromClient.code == MessageModel.LOGIN) {

                    // Receive from the Client the data.
                    String receivedDataFromClient = messageFromClient.data;
                    System.out.println("Receiving from the Client a LOGIN command for User = " + receivedDataFromClient + "...");

                    // Create a SQLiteDataAdapter to use the saveProduct() method.
                    SQLiteDataAdapter adapter = new SQLiteDataAdapter();

                    // Open the connection to the database.
                    adapter.connect();

                    // Deserialize the user's data coming from the Client.
                    UserModel userFromClient = gson.fromJson(receivedDataFromClient, UserModel.class);

                    // Get ALL the user data based on the mUsername.
                    UserModel userFromDatabase = adapter.loadUser(userFromClient.mUsername);

                    // Check if the password found in the database is the same than the one received from the client.
                    if (userFromDatabase != null && userFromDatabase.mPassword.equals(userFromClient.mPassword)) {
                        messageFromClient.code = MessageModel.OPERATION_OK;
                        totalActiveUsers++;
                        int accessToken = totalActiveUsers;
                        messageFromClient.ssid = accessToken;
                        messageFromClient.data = gson.toJson(userFromDatabase, UserModel.class);
                        activeUsers.put(accessToken, userFromDatabase);
                    } else {
                        messageFromClient.code = MessageModel.OPERATION_FAILED;
                    }

                    // Send back to the client the answer of the login command.
                    outputStream.println(gson.toJson(messageFromClient));
                }

                if (messageFromClient.code == MessageModel.LOGOUT) {
                    int logoutResult = 0;

                    // Receive from the Client the data.
                    String receivedDataFromClient = messageFromClient.data;
                    int accessToken = Integer.parseInt(receivedDataFromClient);
                    System.out.println("Receiving from the Client a LOGOUT command with accessToken = " + accessToken + "...");

                    // Remove the user with that accessToken.
                    if (activeUsers.containsKey(accessToken)) {
                        activeUsers.remove(accessToken);
                        logoutResult = 1;
                    }

                    // Send back to the client the response of the logout command.
                    // Note: Not using Gson because don't need to serialize/deserialize just one number.
                    outputStream.println(logoutResult);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}