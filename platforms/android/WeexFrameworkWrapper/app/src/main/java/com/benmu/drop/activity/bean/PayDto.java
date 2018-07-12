package com.benmu.drop.activity.bean;

/**
 * Created by lzq on 2018/6/11.
 */

public class PayDto {
    private int id ;
    private int code ;
    private String razorPaymentId;
    private String razorSignature;
    private String razorOrderId ;
    private String razorAmount ;
    private String error ;

    public String getRazorAmount() {
        return razorAmount;
    }

    public void setRazorAmount(String razorAmount) {
        this.razorAmount = razorAmount;
    }

    public String getRazorPaymentId() {
        return razorPaymentId;
    }

    public void setRazorPaymentId(String razorPaymentId) {
        this.razorPaymentId = razorPaymentId;
    }

    public String getRazorSignature() {
        return razorSignature;
    }

    public void setRazorSignature(String razorSignature) {
        this.razorSignature = razorSignature;
    }

    public String getRazorOrderId() {
        return razorOrderId;
    }

    public void setRazorOrderId(String razorOrderId) {
        this.razorOrderId = razorOrderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
