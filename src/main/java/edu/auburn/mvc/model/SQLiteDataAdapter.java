package edu.auburn.mvc.model;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLiteDataAdapter implements IDataAdapter {
    Connection conn = null;

    public int connect() {
        try {

            // To avoid the "no suitable driver found" error...
            // Your classpath is missing the jar(s) that contain the sqlite classes and driver.
            // You need something like sqlite-jdbc-3.7.2.jar or your applicable version.
            // If you are sure the jar is there, try adding this line of code before you create a connection:
            // Class.forName("org.sqlite.JDBC");
            // https://stackoverflow.com/questions/16725377/no-suitable-driver-found-sqlite
            Class.forName("org.sqlite.JDBC");

            // db parameters
            String url = "jdbc:sqlite:store.db";

            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return CONNECTION_OPEN_FAILED;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return CONNECTION_OPEN_FAILED;
        }
        return CONNECTION_OPEN_OK;
    }

    public int disconnect() {
        try {
            conn.close();
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
            return CONNECTION_CLOSE_FAILED;
        }
        return CONNECTION_CLOSE_OK;
    }

    public ProductModel loadProduct(int productID) {
        ProductModel product = new ProductModel();

        try {
            String sql = "SELECT ProductId, Name, Price, Quantity FROM Products WHERE ProductId = " + productID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            product.mProductID = rs.getInt("ProductId");
            product.mName = rs.getString("Name");
            product.mPrice = rs.getDouble("Price");
            product.mQuantity = rs.getDouble("Quantity");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return product;
    }

    public int saveProduct(ProductModel product) {
        Statement statement = null;
        String sql = null;

        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            sql = "INSERT INTO Products (ProductId,Name,Price,Quantity) "
                    + "VALUES (" + product.mProductID + ", '" + product.mName + "', " + product.mPrice + ", " + product.mQuantity + ");";
            statement.executeUpdate(sql);
            statement.close();
            conn.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return PRODUCT_DUPLICATE_ERROR;
        }
        return PRODUCT_SAVED_OK;
    }

    public int updateProduct(ProductModel product) {
        Statement statement = null;
        String sql = null;

        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            // Actual SQL update statement:
            // UPDATE Products SET Name = 'product.mName', Price = product.mPrice, Quantity = product.mQuantity WHERE ProductId = product.mProductID;
            sql = "UPDATE Products SET Name = '" + product.mName + "', Price = " + product.mPrice + ", Quantity = " + product.mQuantity + " WHERE ProductId = " + product.mProductID +";";
            statement.executeUpdate(sql);
            statement.close();
            conn.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return PRODUCT_NOT_FOUND_ERROR;
        }
        return PRODUCT_UPDATED_OK;
    }



    public ArrayList<ProductModel> listProducts() {
        ArrayList<ProductModel> productList = new ArrayList<ProductModel>();
        Statement statement = null;
        String sql = null;
        ResultSet resultSet = null;

        try {
            sql = "SELECT * FROM Products;";
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("ProductId");
                String name = resultSet.getString("Name");
                double price = resultSet.getDouble("Price");
                double quantity = resultSet.getDouble("Quantity");

                System.out.println("ProductId: " + id);
                System.out.println("Name: " + name);
                System.out.println("Price: " + price);
                System.out.println("Quantity: " + quantity);
                System.out.println();
            }
            statement.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return productList;
        }
        return productList;
    }

    public CustomerModel loadCustomer(int customerID) {
        CustomerModel customer = new CustomerModel();

        try {
            String sql = "SELECT CustomerId, Name, ZipCode, Phone FROM Customers WHERE CustomerId = " + customerID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            customer.customerID = rs.getInt("CustomerId");
            customer.name = rs.getString("Name");
            customer.zipCode = rs.getInt("ZipCode");
            customer.phone = rs.getString("Phone");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return customer;
    }

    public int saveCustomer(CustomerModel customer) {
        Statement statement = null;
        String sql = null;

        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            sql = "INSERT INTO Customers (CustomerId, Name, ZipCode, Phone) "
                    + "VALUES (" + customer.customerID + ", '" + customer.name + "', " + customer.zipCode + ", " + customer.phone + ");";
            statement.executeUpdate(sql);
            statement.close();
            conn.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return CUSTOMER_DUPLICATE_ERROR;
        }
        return CUSTOMER_SAVED_OK;
    }

    public int updateCustomer(CustomerModel customer) {
        Statement statement = null;
        String sql = null;

        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            // Actual SQL update statement:
            // UPDATE Customers SET Name = 'customer.name', ZipCode = customer.zipCode, Phone = 'customer.phone' WHERE CustomerId = customer.customerID;
            sql = "UPDATE Customers SET Name = '" + customer.name + "', ZipCode = " + customer.zipCode + ", Phone = '" + customer.phone + "'  WHERE CustomerId = " + customer.customerID +";";
            statement.executeUpdate(sql);
            statement.close();
            conn.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return CUSTOMER_NOT_FOUND_ERROR;
        }
        return CUSTOMER_UPDATED_OK;
    }

    public ArrayList<CustomerModel> listCustomers() {
        ArrayList<CustomerModel> customerList = new ArrayList<CustomerModel>();

        Statement statement = null;
        String sql = null;
        ResultSet resultSet = null;

        try {
            sql = "SELECT * FROM Customers;";
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("CustomerId");
                String name = resultSet.getString("Name");
                int zipCode  = resultSet.getInt("ZipCode");
                String phone = resultSet.getString("Phone");

                System.out.println("CustomerId: " + id);
                System.out.println("Name: " + name);
                System.out.println("ZipCode: " + zipCode);
                System.out.println("Phone: " + phone);
                System.out.println();
            }
            statement.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return customerList;
        }
        return customerList;
    }

    public PurchaseModel loadPurchase(int purchaseID) {
        PurchaseModel purchase = new PurchaseModel();

        try {
            String sql = "SELECT * FROM Purchases WHERE PurchaseId = " + purchaseID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            purchase.purchaseID = rs.getInt("PurchaseId");
            purchase.productID = rs.getInt("ProductId");
            purchase.customerID = rs.getInt("CustomerId");
            purchase.quantity = rs.getInt("Quantity");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return purchase;
    }

    public int savePurchase(PurchaseModel purchase) {
        Statement statement = null;
        String sql = null;

        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            sql = "INSERT INTO Purchases (PurchaseId, ProductId, CustomerId, Quantity) "
                    + "VALUES (" + purchase.purchaseID + ", '" + purchase.productID + "', " + purchase.customerID + ", " + purchase.quantity + ");";
            statement.executeUpdate(sql);
            statement.close();
            conn.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return PURCHASE_DUPLICATE_ERROR;
        }
        return PURCHASE_SAVED_OK;
    }

    public int updatePurchase(PurchaseModel purchase) {
        Statement statement = null;
        String sql = null;

        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            // Actual SQL update statement:
            // UPDATE Purchases SET ProductId = purchase.productID, CustomerId = purchase.customerID, Quantity = purchase.quantity WHERE PurchaseId = purchase.purchaseID;
            sql = "UPDATE Purchases SET ProductId = '" + purchase.productID + "', CustomerId = " + purchase.customerID + ", Quantity = " + purchase.quantity + " WHERE PurchaseId = " + purchase.purchaseID +";";
            statement.executeUpdate(sql);
            statement.close();
            conn.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return PURCHASE_NOT_FOUND_ERROR;
        }
        return PURCHASE_UPDATED_OK;
    }



    public ArrayList<PurchaseModel> listPurchases() {
        ArrayList<PurchaseModel> customerList = new ArrayList<PurchaseModel>();

        Statement statement = null;
        String sql = null;
        ResultSet resultSet = null;

        try {
            sql = "SELECT * FROM Purchases;";
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int purchaseId = resultSet.getInt("PurchaseId");
                int productId = resultSet.getInt("ProductId");
                int customerId  = resultSet.getInt("CustomerId");
                int quantity = resultSet.getInt("Quantity");

                System.out.println("PurchaseId: " + purchaseId);
                System.out.println("ProductId: " + productId);
                System.out.println("CustomerId: " + customerId);
                System.out.println("Quantity: " + quantity);
                System.out.println();
            }
            statement.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return customerList;
        }
        return customerList;
    }


    public static UserModel loadUser(String username) {
        UserModel user = null;
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:store.db";
            conn = DriverManager.getConnection(url);

            // Actual SQL query: SELECT * FROM Users WHERE Username = 'LUCIA'
            String sql = "SELECT * FROM Users WHERE Username = '" + username + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                user = new UserModel();
                user.mUsername = username;
                user.mPassword = rs.getString("Password");
                user.mFullname = rs.getString("Fullname");
                user.mUserType = rs.getInt("Usertype");
                if (user.mUserType == UserModel.CUSTOMER) {
                    user.mCustomerID = rs.getInt("CustomerID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
