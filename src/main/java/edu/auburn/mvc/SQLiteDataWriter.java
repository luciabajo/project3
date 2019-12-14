package edu.auburn.mvc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Database: store.db
 * Tables: Products, Customers and Purchases.
 */
public class SQLiteDataWriter {
    public static String databaseName = "store.db";

    public static void main(String args[]) {
        Connection connection = null;
        Statement statement = null;
        String sqlStatement = null;

        try {

            /*
             * Open database connection.
             */
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            System.out.println("Database " + databaseName + " successfully opened.");

            /*
             * Create the Products table.
             */
            sqlStatement = "CREATE TABLE Products "
                    + "(ProductId INT      PRIMARY KEY  NOT NULL,"
                    + " Name      CHAR(200)             NOT NULL, "
                    + " Price     REAL                  NOT NULL, "
                    + " Quantity  INT                   NOT NULL)";
            statement.executeUpdate(sqlStatement);
            System.out.println("Table Products successfully created.");

            /*
             * Create the Customers table.
             */
            sqlStatement = "CREATE TABLE Customers "
                    + "(CustomerId INT    PRIMARY KEY  NOT NULL,"
                    + " Name      CHAR(200)            NOT NULL, "
                    + " ZipCode   INT                  NOT NULL, "
                    + " Phone     CHAR(10)             NOT NULL)";
            statement.executeUpdate(sqlStatement);
            System.out.println("Table Customers successfully created.");

            /*
             * Create the Purchases table.
             */
            sqlStatement = "CREATE TABLE Purchases "
                    + "(PurchaseId INT    PRIMARY KEY  NOT NULL,"
                    + " ProductId  INT                 NOT NULL, "
                    + " CustomerId INT                 NOT NULL, "
                    + " Quantity   INT                 NOT NULL)";
            statement.executeUpdate(sqlStatement);
            System.out.println("Table Purchases successfully created.");

            /*
             * Create the Users table.
             */
            sqlStatement = "CREATE TABLE Users "
                    + "(UserId     INT     PRIMARY KEY  NOT NULL,"
                    + " Username   CHAR(20)             NOT NULL, "
                    + " Password   CHAR(10)             NOT NULL, "
                    + " Fullname   CHAR(50)             NOT NULL, "
                    + " Usertype   INT                  NOT NULL, "
                    + " CustomerID INT                  NOT NULL)";
            statement.executeUpdate(sqlStatement);
            System.out.println("Table Users successfully created.");

            /*
             * Write 5 products into the Products table.
             */
            sqlStatement = "INSERT INTO Products (ProductId, Name, Price, Quantity) " +
                    "VALUES (1, 'New Balance', 100.00, 10 );";
            statement.executeUpdate(sqlStatement);

            sqlStatement = "INSERT INTO Products (ProductId, Name, Price, Quantity) " +
                    "VALUES (2, 'Nike', 200.00, 20 );";
            statement.executeUpdate(sqlStatement);

            sqlStatement = "INSERT INTO Products (ProductId, Name, Price, Quantity) " +
                    "VALUES (3, 'Adidas', 300.00, 30 );";
            statement.executeUpdate(sqlStatement);

            sqlStatement = "INSERT INTO Products (ProductId, Name, Price, Quantity) " +
                    "VALUES (4, 'Converse', 400.00, 40 );";
            statement.executeUpdate(sqlStatement);

            sqlStatement = "INSERT INTO Products (ProductId, Name, Price, Quantity) " +
                    "VALUES (5, 'Puma', 500.00, 50 );";
            statement.executeUpdate(sqlStatement);
            System.out.println("Five products added successfully.");

            /*
             * Write 5 customers into the Customers table.
             */
            sqlStatement = "INSERT INTO Customers (CustomerId, Name, ZipCode, Phone) " +
                    "VALUES (1, 'Candela Bajo', 11111, '205-111-1111');";
            statement.executeUpdate(sqlStatement);

            sqlStatement = "INSERT INTO Customers (CustomerId, Name, ZipCode, Phone) " +
                    "VALUES (2, 'Lucia Bajo', 22222, '205-222-2222');";
            statement.executeUpdate(sqlStatement);

            sqlStatement = "INSERT INTO Customers (CustomerId, Name, ZipCode, Phone) " +
                    "VALUES (3, 'Pablo Bajo', 33333, '205-333-3333');";
            statement.executeUpdate(sqlStatement);

            sqlStatement = "INSERT INTO Customers (CustomerId, Name, ZipCode, Phone) " +
                    "VALUES (4, 'Nacho Bajo', 44444, '205-444-4444');";
            statement.executeUpdate(sqlStatement);

            sqlStatement = "INSERT INTO Customers (CustomerId, Name, ZipCode, Phone) " +
                    "VALUES (5, 'Dany Bajo', 55555, '205-555-5555');";
            statement.executeUpdate(sqlStatement);
            System.out.println("Five customers added successfully.");

            /*
             * Write 10 purchases into the Purchases table.
             */
            sqlStatement = "INSERT INTO Purchases (PurchaseId, ProductId, CustomerId, Quantity) " +
                    "VALUES (1, 5, 1, 10);";
            statement.executeUpdate(sqlStatement);

            sqlStatement = "INSERT INTO Purchases (PurchaseId, ProductId, CustomerId, Quantity) " +
                    "VALUES (2, 4, 1, 20);";
            statement.executeUpdate(sqlStatement);

            sqlStatement = "INSERT INTO Purchases (PurchaseId, ProductId, CustomerId, Quantity) " +
                    "VALUES (3, 3, 2, 30);";
            statement.executeUpdate(sqlStatement);

            sqlStatement = "INSERT INTO Purchases (PurchaseId, ProductId, CustomerId, Quantity) " +
                    "VALUES (4, 2, 2, 40);";
            statement.executeUpdate(sqlStatement);

            sqlStatement = "INSERT INTO Purchases (PurchaseId, ProductId, CustomerId, Quantity) " +
                    "VALUES (5, 1, 3, 50);";
            statement.executeUpdate(sqlStatement);

            sqlStatement = "INSERT INTO Purchases (PurchaseId, ProductId, CustomerId, Quantity) " +
                    "VALUES (6, 5, 3, 60);";
            statement.executeUpdate(sqlStatement);

            sqlStatement = "INSERT INTO Purchases (PurchaseId, ProductId, CustomerId, Quantity) " +
                    "VALUES (7, 4, 4, 70);";
            statement.executeUpdate(sqlStatement);

            sqlStatement = "INSERT INTO Purchases (PurchaseId, ProductId, CustomerId, Quantity) " +
                    "VALUES (8, 3, 4, 80);";
            statement.executeUpdate(sqlStatement);

            sqlStatement = "INSERT INTO Purchases (PurchaseId, ProductId, CustomerId, Quantity) " +
                    "VALUES (9, 2, 5, 90);";
            statement.executeUpdate(sqlStatement);

            sqlStatement = "INSERT INTO Purchases (PurchaseId, ProductId, CustomerId, Quantity) " +
                    "VALUES (10, 1, 5, 100);";
            statement.executeUpdate(sqlStatement);
            System.out.println("Ten purchases added successfully.");

            /*
             * Write 4 users into the Users table.
             */
            sqlStatement = "INSERT INTO Users (UserId, Username, Password, Fullname, Usertype, CustomerID) VALUES (1, 'LUCIA', 'abc123', 'Lucia Bajo', 3, 0);";
            statement.executeUpdate(sqlStatement);

            sqlStatement = "INSERT INTO Users (UserId, Username, Password, Fullname, Usertype, CustomerID) VALUES (2, 'CANDELA', 'abc123', 'Candela Bajo', 2, 0);";
            statement.executeUpdate(sqlStatement);

            sqlStatement = "INSERT INTO Users (UserId, Username, Password, Fullname, Usertype, CustomerID) VALUES (3, 'DANIEL', 'abc123', 'Daniel Bajo', 1, 0);";
            statement.executeUpdate(sqlStatement);

            sqlStatement = "INSERT INTO Users (UserId, Username, Password, Fullname, Usertype, CustomerID) VALUES (4, 'NACHO', 'abc123', 'Nacho Bajo', 0, 1);";
            statement.executeUpdate(sqlStatement);

            sqlStatement = "INSERT INTO Users (UserId, Username, Password, Fullname, Usertype, CustomerID) VALUES (5, 'PABLO', 'abc123', 'Pablo Bajo', 0, 2);";
            statement.executeUpdate(sqlStatement);
            System.out.println("Five users added successfully.");

            /*
             * Close database connection.
             */
            statement.close();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}