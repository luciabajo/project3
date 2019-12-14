package edu.auburn.mvc.model;

public class CustomerModel {
    public int customerID;
    public String name;
    public int zipCode;
    public String phone;

    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        sb.append(customerID).append(",");
        sb.append("\"").append(name).append("\"").append(",");
        sb.append(zipCode).append(",");
        sb.append("\"").append(phone).append("\"").append(")");
        return sb.toString();
    }
}