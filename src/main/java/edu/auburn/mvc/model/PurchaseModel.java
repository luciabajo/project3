package edu.auburn.mvc.model;

public class PurchaseModel {
    public int purchaseID;
    public int productID;
    public int customerID;
    public int quantity;

    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        sb.append(purchaseID).append(",");
        sb.append("\"").append(productID).append("\"").append(",");
        sb.append(customerID).append(",");
        sb.append(quantity).append(")");
        return sb.toString();
    }
}
