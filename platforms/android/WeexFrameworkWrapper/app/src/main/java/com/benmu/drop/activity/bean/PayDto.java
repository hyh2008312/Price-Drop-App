package com.benmu.drop.activity.bean;

/**
 * Created by lzq on 2018/6/11.
 */

public class PayDto {
    private int id ;
    private int code ;
    private String paymentId;
    private String error ;

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

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
