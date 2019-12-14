package edu.auburn.mvc.model;

import java.util.ArrayList;

public interface IDataAdapter {
    public static final int CONNECTION_OPEN_OK = 100;
    public static final int CONNECTION_OPEN_FAILED = 101;

    public static final int CONNECTION_CLOSE_OK = 200;
    public static final int CONNECTION_CLOSE_FAILED = 201;

    public static final int PRODUCT_SAVED_OK = 300;
    public static final int PRODUCT_DUPLICATE_ERROR = 301;
    public static final int PRODUCT_UPDATED_OK = 302;
    public static final int PRODUCT_NOT_FOUND_ERROR = 303;

    public static final int CUSTOMER_SAVED_OK = 400;
    public static final int CUSTOMER_DUPLICATE_ERROR = 401;
    public static final int CUSTOMER_UPDATED_OK = 402;
    public static final int CUSTOMER_NOT_FOUND_ERROR = 403;

    public static final int PURCHASE_SAVED_OK = 500;
    public static final int PURCHASE_DUPLICATE_ERROR = 501;
    public static final int PURCHASE_UPDATED_OK = 502;
    public static final int PURCHASE_NOT_FOUND_ERROR = 503;


    public int connect();
    public int disconnect();

    public ProductModel loadProduct(int id);
    public int saveProduct(ProductModel model);
    public int updateProduct(ProductModel model);
    public ArrayList<ProductModel> listProducts();

    public CustomerModel loadCustomer(int id);
    public int saveCustomer(CustomerModel model);
    public int updateCustomer(CustomerModel model);
    public ArrayList<CustomerModel> listCustomers();

    public PurchaseModel loadPurchase(int id);
    public int savePurchase(PurchaseModel model);
    public int updatePurchase(PurchaseModel model);
    public ArrayList<PurchaseModel> listPurchases();
}
