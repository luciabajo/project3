package edu.auburn.mvc.model;

public class MessageModel {
    public static final int GET_PRODUCT = 10;
    public static final int PUT_PRODUCT = 11;
    public static final int MODIFY_PRODUCT = 12;

    public static final int GET_CUSTOMER = 20;
    public static final int PUT_CUSTOMER = 21;
    public static final int MODIFY_CUSTOMER = 22;

    public static final int GET_PURCHASE = 30;
    public static final int PUT_PURCHASE = 31;
    public static final int MODIFY_PURCHASE = 32;

    public static final int LOGIN = 40;
    public static final int LOGOUT = 41;

    public static final int OPERATION_OK = 1000;
    public static final int OPERATION_FAILED = 1001;

    public int code;
    public int ssid;
    public String data;

    public MessageModel() {
        code = 0;
        data = null;
    }

    public MessageModel(int code, String data) {
        this.code = code;
        this.data = data;
    }

    public MessageModel(int code, int ssid, String data) {
        this.code = code;
        this.ssid = code;
        this.data = data;
    }
}
