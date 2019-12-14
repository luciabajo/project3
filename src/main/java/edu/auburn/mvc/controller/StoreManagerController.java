package edu.auburn.mvc.controller;

import com.google.gson.Gson;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import edu.auburn.mvc.model.*;
import edu.auburn.mvc.view.StoreManagerView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoreManagerController {
    public AddButtonListener addButtonListener = new AddButtonListener();
    public AddCustomerButtonListener addCustomerButtonListener = new AddCustomerButtonListener();
    public AddPurchaseButtonListener addPurchaseButtonListener = new AddPurchaseButtonListener();
    public CancelButtonListener cancelButtonListener = new CancelButtonListener();
    public UpdateCustomerButtonListener updateCustomerButtonListener = new UpdateCustomerButtonListener();
    public UpdatePurchaseButtonListener updatePurchaseButtonListener = new UpdatePurchaseButtonListener();
    public UpdateProductButtonListener updateProductButtonListener = new UpdateProductButtonListener();
    public LoadCustomerButtonListener loadCustomerButtonListener = new LoadCustomerButtonListener();
    public LoadPurchaseButtonListener loadPurchaseButtonListener = new LoadPurchaseButtonListener();
    public LoadProductButtonListener loadProductButtonListener = new LoadProductButtonListener();

    ProductModel product;
    CustomerModel customer;
    PurchaseModel purchase;
    StoreManagerView apView;

    // Constructor.
    public StoreManagerController(ProductModel product, CustomerModel customer, PurchaseModel purchase, StoreManagerView apView) {
        this.product = product;
        this.customer = customer;
        this.purchase = purchase;
        this.apView = apView;
    }

    class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {

            // Populate the product object with the dataentry.
            try {
                product.mProductID = Integer.parseInt(apView.productIdText.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Product ID is invalid!");
                return;
            }
            product.mName = apView.nameText.getText();
            try {
                product.mPrice = Double.parseDouble(apView.priceText.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Price is invalid!");
                return;
            }
            try {
                product.mQuantity = Double.parseDouble(apView.quantityText.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "mQuantity is invalid!");
                return;
            }

            // Do client/server PUT_PRODUCT action.
            try {

                // Gson is a Java library that can be used to convert Java Objects into their JSON representation.
                Gson gson = new Gson();

                // Establish the connection with the server.
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                // Send the PUT_PRODUCT request to the server.
                MessageModel message = new MessageModel();
                message.code = MessageModel.PUT_PRODUCT;
                message.data = gson.toJson(product);
                output.println(gson.toJson(message));
                System.out.println("Sending from the client a PUT_PRODUCT request with product = " + product);

                // Retrieve the response of adding a new product into the database.
                MessageModel receivedMessageFromServer = gson.fromJson(input.nextLine(), MessageModel.class);
                System.out.println("The response of adding a new product into the database is " + receivedMessageFromServer.code);

                if (receivedMessageFromServer.code == IDataAdapter.PRODUCT_SAVED_OK) {
                    JOptionPane.showMessageDialog(null, "Product added:\n" +
                            "Product ID = " + product.mProductID + "\n" +
                            "Name = " + product.mName + "\n" +
                            "Price = " + product.mPrice + "\n" +
                            "Quantity = " + product.mQuantity);

                    // As the product was successfully added, clear the GUI.
                    apView.productIdText.setText("");
                    apView.nameText.setText("");
                    apView.priceText.setText("");
                    apView.quantityText.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Product CANNOT be added:\n" +
                            "Product ID = " + product.mProductID + "\n" +
                            "Name = " + product.mName + "\n" +
                            "Price = " + product.mPrice + "\n" +
                            "Quantity = " + product.mQuantity);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Cannot establish the connection with the server.");
                apView.productIdText.setText("");
                apView.nameText.setText("");
                apView.priceText.setText("");
                apView.quantityText.setText("");
            }
        }
    }

    class AddCustomerButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {

            // Populate the customer object with the dataentry.
            try {
                customer.customerID = Integer.parseInt(apView.customerIdText.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Customer ID is invalid!");
                return;
            }
            customer.name = apView.customerNameText.getText();
            try {
                customer.zipCode = Integer.parseInt(apView.zipCodeText.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ZIP code is invalid!");
                return;
            }
            customer.phone = apView.phoneText.getText();

            // Do client/server PUT_CUSTOMER action.
            try {

                // Gson is a Java library that can be used to convert Java Objects into their JSON representation.
                Gson gson = new Gson();

                // Establish the connection with the server.
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                // Send the PUT_CUSTOMER request to the server.
                MessageModel message = new MessageModel();
                message.code = MessageModel.PUT_CUSTOMER;
                message.data = gson.toJson(customer);
                output.println(gson.toJson(message));
                System.out.println("Sending from the client a PUT_CUSTOMER request with customer = " + customer);

                // Retrieve the response of adding a new customer into the database.
                MessageModel receivedMessageFromServer = gson.fromJson(input.nextLine(), MessageModel.class);
                System.out.println("The response of adding a new customer into the database is " + receivedMessageFromServer.code);

                if (receivedMessageFromServer.code == IDataAdapter.CUSTOMER_SAVED_OK) {
                    JOptionPane.showMessageDialog(null, "Customer added:\n" +
                            "Customer ID = " + customer.customerID + "\n" +
                            "Name = " + customer.name + "\n" +
                            "Zip Code = " + customer.zipCode + "\n" +
                            "Phone = " + customer.phone);

                    // As the customer was successfully added, clear the GUI.
                    apView.customerIdText.setText("");
                    apView.customerNameText.setText("");
                    apView.zipCodeText.setText("");
                    apView.phoneText.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Customer CANNOT be added:\n" +
                            "Customer ID = " + customer.customerID + "\n" +
                            "Name = " + customer.name + "\n" +
                            "Zip Code = " + customer.zipCode + "\n" +
                            "Phone = " + customer.phone);
                }
            } catch (Exception e) {
                System.out.println("Exception " + e);
                JOptionPane.showMessageDialog(null, "Cannot establish the connection with the server.");
                apView.customerIdText.setText("");
                apView.customerNameText.setText("");
                apView.zipCodeText.setText("");
                apView.phoneText.setText("");
            }
        }
    }

    class AddPurchaseButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            double purchaseTax = 0.0;
            double purchaseSubTotal = 0.0;
            double purchaseTotal = 0.0;
            double salesTax = 0.04; // 4%

            // Populate the purchase object with the dataentry.
            try {
                purchase.purchaseID = Integer.parseInt(apView.purchaseIdText.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Purchase ID is invalid!");
                return;
            }
            try {
                purchase.productID = Integer.parseInt(apView.purchaseProductIdText.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Product ID is invalid!");
                return;
            }
            try {
                purchase.customerID = Integer.parseInt(apView.purchaseCustomerIdText.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Customer ID is invalid!");
                return;
            }
            try {
                purchase.quantity = Integer.parseInt(apView.purchaseQuantityText.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Quantity is invalid!");
                return;
            }

            // Calculate the purchase subtotal.
            purchaseSubTotal = product.mPrice * purchase.quantity;

            // Calculate the tax on the purchase.
            // https://www.wikihow.com/Calculate-Sales-Tax
            purchaseTax = purchaseSubTotal * salesTax;

            // Calculate the purchase total.
            purchaseTotal = purchaseSubTotal + purchaseTax;

            // Do client/server PUT_PURCHASE action.
            try {

                //*** Get the product name based on the productID by sending a GET_PRODUCT request to the server.***
                Gson gsonForProduct = new Gson();

                Socket linkForProduct = new Socket("localhost", 1000);
                Scanner inputForProduct = new Scanner(linkForProduct.getInputStream());
                PrintWriter outputForProduct = new PrintWriter(linkForProduct.getOutputStream(), true);

                MessageModel messageForProduct = new MessageModel();
                messageForProduct.code = MessageModel.GET_PRODUCT;
                messageForProduct.data = Integer.toString(purchase.productID);
                outputForProduct.println(gsonForProduct.toJson(messageForProduct));
                System.out.println("Sending from the client a GET_PRODUCT request for product with id " + purchase.productID + "...");

                product = gsonForProduct.fromJson(inputForProduct.nextLine(), ProductModel.class);
                System.out.println("Receiving back from the Server the product " + product);
                //**************************************************************************************************

                //*** Get the customer name based on the customerID by sending a GET_CUSTOMER request to the server.***
                Gson gsonForCustomer = new Gson();

                Socket linkForCustomer = new Socket("localhost", 1000);
                Scanner inputForCustomer = new Scanner(linkForProduct.getInputStream());
                PrintWriter outputForCustomer = new PrintWriter(linkForProduct.getOutputStream(), true);

                MessageModel messageForCustomer = new MessageModel();
                messageForCustomer.code = MessageModel.GET_CUSTOMER;
                messageForCustomer.data = Integer.toString(purchase.customerID);
                outputForCustomer.println(gsonForCustomer.toJson(messageForCustomer));
                System.out.println("Sending from the client a GET_CUSTOMER request for customer with id " + purchase.customerID + "...");

                customer = gsonForCustomer.fromJson(inputForCustomer.nextLine(), CustomerModel.class);
                System.out.println("Receiving back from the Server the customer " + customer);
                //*****************************************************************************************************

                // Gson is a Java library that can be used to convert Java Objects into their JSON representation.
                Gson gson = new Gson();

                // Establish the connection with the server.
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                // Send the PUT_PURCHASE request to the server.
                MessageModel message = new MessageModel();
                message.code = MessageModel.PUT_PURCHASE;
                message.data = gson.toJson(purchase);
                output.println(gson.toJson(message));
                System.out.println("Sending from the client a PUT_PURCHASE request with purchase = " + purchase);

                // Retrieve the response of adding a new purchase into the database.
                MessageModel receivedMessageFromServer = gson.fromJson(input.nextLine(), MessageModel.class);
                System.out.println("The response of adding a new purchase into the database is " + receivedMessageFromServer.code);

                if (receivedMessageFromServer.code == IDataAdapter.PURCHASE_SAVED_OK) {
                    JOptionPane.showMessageDialog(null, "Purchase Receipt (purchase added):\n" +
                            "Product Name = " + product.mName + "\n" +
                            "Quantity Purchased = " + purchase.quantity + "\n" +
                            "Customer Name = " + customer.name + "\n" +
                            "Purchase Tax = " + purchaseTax + "\n" +
                            "Purchase Total = " + purchaseTotal);

                    // As the product was successfully added, clear the GUI.
                    apView.purchaseIdText.setText("");
                    apView.purchaseProductIdText.setText("");
                    apView.purchaseCustomerIdText.setText("");
                    apView.purchaseQuantityText.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Purchase CANNOT be added:\n" +
                            "Purchase ID = " + purchase.purchaseID + "\n" +
                            "Product ID = " + purchase.productID + "\n" +
                            "Customer ID = " + purchase.customerID + "\n" +
                            "Quantity = " + purchase.quantity);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Cannot establish the connection with the server.");
                apView.purchaseIdText.setText("");
                apView.purchaseProductIdText.setText("");
                apView.purchaseCustomerIdText.setText("");
                apView.purchaseQuantityText.setText("");
            }
        }
    }

    class CancelButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            System.exit(0);
        }
    }


    class UpdateCustomerButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            CustomerModel originalCustomerData = null;
            int confirmation = 0;

            // Create a SQLiteDataAdapter to use the updateCustomer() method.
            SQLiteDataAdapter adapter = new SQLiteDataAdapter();

            // Open the connection to the database because it's closed after saving each customer.
            adapter.connect();

            // Populate the customer object with the dataentry.
            try {
                customer.customerID = Integer.parseInt(apView.customerIdText.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Customer ID is invalid!");
                return;
            }
            customer.name = apView.customerNameText.getText();
            try {
                customer.zipCode = Integer.parseInt(apView.zipCodeText.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ZIP code is invalid!");
                return;
            }
            customer.phone = apView.phoneText.getText();

            // Locate the existing customer and retrieve the original data.
            originalCustomerData = adapter.loadCustomer(customer.customerID);

            if (originalCustomerData.name != null) {

                // Display the customer original data and ask for confirmation.
                confirmation = JOptionPane.showConfirmDialog(null, "You are going to update this data:\n"
                        + "Name = " + originalCustomerData.name + "\n"
                        + "Zip Code = " + originalCustomerData.zipCode + "\n"
                        + "Phone = " + originalCustomerData.phone + "\n"
                        + "with this data:\n"
                        + "Name = " + customer.name + "\n"
                        + "Zip Code = " + customer.zipCode + "\n"
                        + "Phone = " + customer.phone + "\n"
                        + "Are you sure?");

                if (confirmation == JOptionPane.YES_OPTION) {

                    // Update the customer's data in the database.
                    if (adapter.updateCustomer(customer) == IDataAdapter.CUSTOMER_UPDATED_OK) {
                        JOptionPane.showMessageDialog(null, "Customer with ID " + customer.customerID + " successfully updated.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Cannot update customer with ID " + customer.customerID);
                    }
                } else if (confirmation == JOptionPane.CANCEL_OPTION) {

                    // As the user decided to cancel the update, clear the GUI.
                    apView.customerIdText.setText("");
                    apView.customerNameText.setText("");
                    apView.zipCodeText.setText("");
                    apView.phoneText.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Cannot find customer with ID " + customer.customerID);
            }

            // Close the database connection after saving each customer.
            adapter.disconnect();
        }
    }

    class UpdatePurchaseButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            PurchaseModel originalPurchaseData = null;
            int confirmation = 0;

            // Create a SQLiteDataAdapter to use the updatePurchase() method.
            SQLiteDataAdapter adapter = new SQLiteDataAdapter();

            // Open the connection to the database because it's closed after saving each customer.
            adapter.connect();

            // Populate the purchase object with the dataentry.
            try {
                purchase.purchaseID = Integer.parseInt(apView.purchaseIdText.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Purchase ID is invalid!");
                return;
            }
            try {
                purchase.productID = Integer.parseInt(apView.purchaseProductIdText.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Product ID is invalid!");
                return;
            }
            try {
                purchase.customerID = Integer.parseInt(apView.purchaseCustomerIdText.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Customer ID is invalid!");
                return;
            }
            try {
                purchase.quantity = Integer.parseInt(apView.purchaseQuantityText.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Quantity is invalid!");
                return;
            }

            // Locate the existing purchase and retrieve the original data.
            originalPurchaseData = adapter.loadPurchase(purchase.purchaseID);

            if (originalPurchaseData.productID != 0) {

                // Display the purchase original data and ask for confirmation.
                confirmation = JOptionPane.showConfirmDialog(null, "You are going to update this data:\n"
                        + "Product ID = " + originalPurchaseData.productID + "\n"
                        + "Customer ID = " + originalPurchaseData.customerID + "\n"
                        + "Quantity = " + originalPurchaseData.quantity + "\n"
                        + "with this data:\n"
                        + "Product ID = " + purchase.productID + "\n"
                        + "Customer ID = " + purchase.customerID + "\n"
                        + "Quantity = " + purchase.quantity + "\n"
                        + "Are you sure?");

                if (confirmation == JOptionPane.YES_OPTION) {

                    // Update the purchase's data in the database.
                    if (adapter.updatePurchase(purchase) == IDataAdapter.PURCHASE_UPDATED_OK) {
                        JOptionPane.showMessageDialog(null, "Purchase with ID " + purchase.purchaseID + " successfully updated.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Cannot update purchase with ID " + purchase.purchaseID);
                    }
                } else if (confirmation == JOptionPane.CANCEL_OPTION) {

                    // As the user decided to cancel the update, clear the GUI.
                    apView.purchaseIdText.setText("");
                    apView.purchaseProductIdText.setText("");
                    apView.purchaseCustomerIdText.setText("");
                    apView.purchaseQuantityText.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Cannot find purchase with ID " + purchase.purchaseID);
            }

            // Close the database connection after saving each customer.
            adapter.disconnect();
        }
    }

    class UpdateProductButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            ProductModel originalProductData = null;
            int confirmation = 0;

            // Populate the product object with the dataentry.
            try {
                product.mProductID = Integer.parseInt(apView.productIdText.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Product ID is invalid!");
                return;
            }
            product.mName = apView.nameText.getText();
            try {
                product.mPrice = Double.parseDouble(apView.priceText.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Price is invalid!");
                return;
            }
            try {
                product.mQuantity = Double.parseDouble(apView.quantityText.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "mQuantity is invalid!");
                return;
            }

            //*** Locate the existing product and retrieve the original data. ***
            Gson gsonForProduct = new Gson();

            try {
                Socket linkForOriginalProduct = new Socket("localhost", 1000);
                Scanner inputForOriginalProduct = new Scanner(linkForOriginalProduct.getInputStream());
                PrintWriter outputForOriginalProduct = new PrintWriter(linkForOriginalProduct.getOutputStream(), true);

                MessageModel messageForOriginalProduct = new MessageModel();
                messageForOriginalProduct.code = MessageModel.GET_PRODUCT;
                messageForOriginalProduct.data = Integer.toString(product.mProductID);
                outputForOriginalProduct.println(gsonForProduct.toJson(messageForOriginalProduct));
                System.out.println("Sending from the client a GET_PRODUCT request for product with id " + purchase.productID + "...");

                originalProductData = gsonForProduct.fromJson(inputForOriginalProduct.nextLine(), ProductModel.class);
                System.out.println("Receiving back from the Server the original product data " + originalProductData);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Cannot establish the connection with the server.");
            }
            //*******************************************************************

            if (originalProductData.mName != null) {

                // Display the product original data and ask for confirmation.
                confirmation = JOptionPane.showConfirmDialog(null, "You are going to update this data:\n"
                        + "Product ID = " + originalProductData.mProductID + "\n"
                        + "Name = " + originalProductData.mName + "\n"
                        + "Price = " + originalProductData.mPrice + "\n"
                        + "Quantity = " + originalProductData.mQuantity + "\n"
                        + "with this data:\n"
                        + "Product ID = " + product.mProductID + "\n"
                        + "Name = " + product.mName + "\n"
                        + "Price = " + product.mPrice + "\n"
                        + "Quantity = " + product.mQuantity + "\n"
                        + "Are you sure?");

                if (confirmation == JOptionPane.YES_OPTION) {

                    //**** Do client/server MODIFY_PRODUCT action. ****
                    try {

                        // Gson is a Java library that can be used to convert Java Objects into their JSON representation.
                        Gson gson = new Gson();

                        // Establish the connection with the server.
                        Socket link = new Socket("localhost", 1000);
                        Scanner input = new Scanner(link.getInputStream());
                        PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                        // Send the PUT_PRODUCT request to the server.
                        MessageModel message = new MessageModel();
                        message.code = MessageModel.MODIFY_PRODUCT;
                        message.data = gson.toJson(product);
                        output.println(gson.toJson(message));
                        System.out.println("Sending from the client a MODIFY_PRODUCT request with product = " + product);

                        // Retrieve the response of updating a product in the database.
                        MessageModel receivedMessageFromServer = gson.fromJson(input.nextLine(), MessageModel.class);
                        System.out.println("The response of updating a product in the database is " + receivedMessageFromServer.code);

                        if (receivedMessageFromServer.code == IDataAdapter.PRODUCT_UPDATED_OK) {
                            JOptionPane.showMessageDialog(null, "Product updated:\n" +
                                    "Product ID = " + product.mProductID + "\n" +
                                    "Name = " + product.mName + "\n" +
                                    "Price = " + product.mPrice + "\n" +
                                    "Quantity = " + product.mQuantity);

                            // As the product was successfully updated, clear the GUI.
                            apView.productIdText.setText("");
                            apView.nameText.setText("");
                            apView.priceText.setText("");
                            apView.quantityText.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null, "Product CANNOT be updated:\n" +
                                    "Product ID = " + product.mProductID + "\n" +
                                    "Name = " + product.mName + "\n" +
                                    "Price = " + product.mPrice + "\n" +
                                    "Quantity = " + product.mQuantity);
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Cannot establish the connection with the server.");
                        apView.productIdText.setText("");
                        apView.nameText.setText("");
                        apView.priceText.setText("");
                        apView.quantityText.setText("");
                    }
                    //**********************************************

                } else if (confirmation == JOptionPane.CANCEL_OPTION) {

                    // As the user decided to cancel the update, clear the GUI.
                    apView.productIdText.setText("");
                    apView.nameText.setText("");
                    apView.priceText.setText("");
                    apView.quantityText.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Cannot find product with ID " + product.mProductID);
            }
        }
    }


    class LoadProductButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {

            // Get the product ID.
            try {
                if (apView.productIdText.getText().length() == 0) {
                    JOptionPane.showMessageDialog(null, "Product ID cannot be null!");
                    return;
                }
                product.mProductID = Integer.parseInt(apView.productIdText.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Product ID is invalid!");
                return;
            }

            // Do client/server GET_PRODUCT action.
            try {

                // Gson is a Java library that can be used to convert Java Objects into their JSON representation.
                Gson gson = new Gson();

                // Establish the connection with the server.
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                // Send the GET_PRODUCT request to the server.
                MessageModel message = new MessageModel();
                message.code = MessageModel.GET_PRODUCT;
                message.data = Integer.toString(product.mProductID);
                output.println(gson.toJson(message));
                System.out.println("Sending from the client a GET_PRODUCT request for product with id " + product.mProductID + "...");

                // Retrieve the whole purchase data all at once.
                product = gson.fromJson(input.nextLine(), ProductModel.class);
                System.out.println("Receiving back from the Server the product data...");

                if (product.mName != null) {
                    // If product is NOT null, populate all the JTextFields.
                    apView.nameText.setText(product.mName);
                    apView.priceText.setText(Double.toString(product.mPrice));
                    apView.quantityText.setText(Double.toString(product.mQuantity));
                } else {
                    // Product is null which means no record found in database, so clear all the JTextField.
                    JOptionPane.showMessageDialog(null, "Product ID does not exist!");
                    apView.productIdText.setText("");
                    apView.nameText.setText("");
                    apView.priceText.setText("");
                    apView.quantityText.setText("");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Cannot establish the connection with the server.");
                apView.productIdText.setText("");
                apView.nameText.setText("");
                apView.priceText.setText("");
                apView.quantityText.setText("");
            }
        }
    }

    class LoadCustomerButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {

            // Get the customer ID.
            try {
                if (apView.customerIdText.getText().length() == 0) {
                    JOptionPane.showMessageDialog(null, "Customer ID cannot be null!");
                    return;
                }
                customer.customerID = Integer.parseInt(apView.customerIdText.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Customer ID is invalid!");
                return;
            }

            // Do client/server GET_CUSTOMER action.
            try {

                // Gson is a Java library that can be used to convert Java Objects into their JSON representation.
                Gson gson = new Gson();

                // Establish the connection with the server.
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                // Send the GET_CUSTOMER request to the server.
                System.out.println("Sending from the client a GET_CUSTOMER request for customer with id " + customer.customerID + "...");
                MessageModel message = new MessageModel();
                message.code = MessageModel.GET_CUSTOMER;
                message.data = Integer.toString(customer.customerID);
                output.println(gson.toJson(message));

                // Retrieve the whole customer data all at once.
                System.out.println("Receiving back from the Server the customer data...");
                customer = gson.fromJson(input.nextLine(), CustomerModel.class);

                if (customer.name != null) {
                    // If customer is NOT null, populate all the JTextFields.
                    apView.customerNameText.setText(customer.name);
                    apView.zipCodeText.setText(Integer.toString(customer.zipCode));
                    apView.phoneText.setText(customer.phone);
                } else {
                    // Customer is null which means no record found in database, so clear all the JTextField.
                    JOptionPane.showMessageDialog(null, "Customer ID does not exist!");
                    apView.customerIdText.setText("");
                    apView.customerNameText.setText("");
                    apView.zipCodeText.setText("");
                    apView.phoneText.setText("");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Cannot establish the connection with the server.");
                apView.customerIdText.setText("");
                apView.customerNameText.setText("");
                apView.zipCodeText.setText("");
                apView.phoneText.setText("");
            }
        }
    }

    class LoadPurchaseButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {

            // Get the purchase ID.
            try {
                if (apView.purchaseIdText.getText().length() == 0) {
                    JOptionPane.showMessageDialog(null, "Purchase ID cannot be null!");
                    return;
                }
                purchase.purchaseID = Integer.parseInt(apView.purchaseIdText.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Purchase ID is invalid!");
                return;
            }

            // Do client/server GET_PURCHASE action.
            try {

                // Gson is a Java library that can be used to convert Java Objects into their JSON representation.
                Gson gson = new Gson();

                // Establish the connection with the server.
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                // Send the GET_PURCHASE request to the server.
                System.out.println("Sending from the client a GET_PURCHASE request for purchase with id " + purchase.purchaseID + "...");
                MessageModel message = new MessageModel();
                message.code = MessageModel.GET_PURCHASE;
                message.data = Integer.toString(purchase.purchaseID);
                output.println(gson.toJson(message));

                // Retrieve the whole purchase data all at once.
                System.out.println("Receiving back from the Server the purchase data...");
                purchase = gson.fromJson(input.nextLine(), PurchaseModel.class);

                if (purchase.productID != 0) {
                    // If purchase is NOT null, populate all the JTextFields.
                    apView.purchaseProductIdText.setText(Integer.toString(purchase.productID));
                    apView.purchaseCustomerIdText.setText(Integer.toString(purchase.customerID));
                    apView.purchaseQuantityText.setText(Integer.toString(purchase.quantity));
                } else {
                    // Purchase is null which means no record found in database, so clear all the JTextField.
                    JOptionPane.showMessageDialog(null, "Purchase ID does not exist!");
                    apView.purchaseIdText.setText("");
                    apView.purchaseProductIdText.setText("");
                    apView.purchaseCustomerIdText.setText("");
                    apView.purchaseQuantityText.setText("");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Cannot establish the connection with the server.");
                apView.purchaseIdText.setText("");
                apView.purchaseProductIdText.setText("");
                apView.purchaseCustomerIdText.setText("");
                apView.purchaseQuantityText.setText("");
            }
        }
    }
}