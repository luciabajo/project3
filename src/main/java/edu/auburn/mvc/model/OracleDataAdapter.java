package edu.auburn.mvc.model;

import java.util.ArrayList;

public class OracleDataAdapter implements IDataAdapter {
    public int connect() {
        //...
        return CONNECTION_OPEN_OK;
    }

    public int disconnect() {
        // ...
        return CONNECTION_CLOSE_OK;
    }

    public ProductModel loadProduct(int id) {
        return null;
    }

    public int saveProduct(ProductModel model) {
        return PRODUCT_SAVED_OK;
    }

    public int updateProduct(ProductModel model) {
        return PRODUCT_UPDATED_OK;
    }

    public ArrayList<ProductModel> listProducts() {
        return null;
    }

    public CustomerModel loadCustomer(int id) {
        return null;
    }

    public int saveCustomer(CustomerModel model) {
        return CUSTOMER_SAVED_OK;
    }

    public int updateCustomer(CustomerModel model) {
        return CUSTOMER_UPDATED_OK;
    }

    public ArrayList<CustomerModel> listCustomers() {
        return null;
    }

    public PurchaseModel loadPurchase(int id) {
        return null;
    }

    public int savePurchase(PurchaseModel model) {
        return PURCHASE_SAVED_OK;
    }

    public int updatePurchase(PurchaseModel model) {
        return PURCHASE_UPDATED_OK;
    }

    public ArrayList<PurchaseModel> listPurchases() {
        return null;
    }
}