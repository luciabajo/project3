package edu.auburn.mvc;

import edu.auburn.mvc.controller.StoreManagerController;
import edu.auburn.mvc.model.ProductModel;
import edu.auburn.mvc.model.CustomerModel;
import edu.auburn.mvc.model.PurchaseModel;
import edu.auburn.mvc.model.SQLiteDataAdapter;
import edu.auburn.mvc.model.OracleDataAdapter;
import edu.auburn.mvc.model.IDataAdapter;
import edu.auburn.mvc.view.StoreManagerView;

import javax.swing.*;

public class StoreManagerMain {

    StoreManagerView apView = null;
    public String loginInfo;

    public StoreManagerMain(String dbms, String loginInfo) {
        this.loginInfo = loginInfo;
        ProductModel product = null;
        CustomerModel customer = null;
        PurchaseModel purchase = null;
        IDataAdapter adapter = null;

        if (dbms.equals("Oracle")) {
            adapter = new OracleDataAdapter();
        } else if (dbms.equals("SQLite")) {
            adapter = new SQLiteDataAdapter();
        } else {
            System.out.println("ERROR: DBMS " + dbms + " not recognized!");
            System.exit(-1);
        }

        // Open the database connection before access it.
        adapter.connect();

        /*
         * Showing Products data.
         */
        System.out.println("Showing first product in database...");
        product = adapter.loadProduct(1);
        System.out.println("Product ID: " + product.mProductID);
        System.out.println("Name: " + product.mName);
        System.out.println("Price: " + product.mPrice);
        System.out.println("Quantity: " + product.mQuantity);
        System.out.println();

        System.out.println("Showing all the products in database...");
        adapter.listProducts();

        /*
         * Showing Customers data.
         */
        System.out.println("Showing first customer in database...");
        customer = adapter.loadCustomer(1);
        System.out.println("Customer ID: " + customer.customerID);
        System.out.println("Name: " + customer.name);
        System.out.println("Zip Code: " + customer.zipCode);
        System.out.println("Phone: " + customer.phone);
        System.out.println();

        System.out.println("Showing all the customers in database...");
        adapter.listCustomers();

        /*
         * Showing Purchase data.
         */
        System.out.println("Showing first purchase in database...");
        purchase = adapter.loadPurchase(1);
        System.out.println("Purchase ID: " + purchase.purchaseID);
        System.out.println("Product ID: " + purchase.productID);
        System.out.println("Customer ID: " + purchase.customerID);
        System.out.println("Quantity: " + purchase.quantity);
        System.out.println();

        System.out.println("Showing all the purchases in database...");
        adapter.listPurchases();

        // Close the database connection after access it.
        adapter.disconnect();

        apView = new StoreManagerView(loginInfo);
        apView.setVisible(true);

        StoreManagerController controller = new StoreManagerController(product, customer, purchase,  apView);
        apView.btnAdd.addActionListener(controller.addButtonListener);
        apView.addCustomerButton.addActionListener(controller.addCustomerButtonListener);
        apView.addPurchaseButton.addActionListener(controller.addPurchaseButtonListener);
        apView.btnCancel.addActionListener(controller.cancelButtonListener);
        apView.updateCustomerButton.addActionListener(controller.updateCustomerButtonListener);
        apView.updatePurchaseButton.addActionListener(controller.updatePurchaseButtonListener);
        apView.updateProductButton.addActionListener(controller.updateProductButtonListener);
        apView.loadCustomerButton.addActionListener(controller.loadCustomerButtonListener);
        apView.loadPurchaseButton.addActionListener(controller.loadPurchaseButtonListener);
        apView.loadProductButton.addActionListener(controller.loadProductButtonListener);

    }
}
