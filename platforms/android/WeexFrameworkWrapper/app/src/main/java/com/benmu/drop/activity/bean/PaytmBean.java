package com.benmu.drop.activity.bean;

/**
 * Created by lzq on 2018/7/25.
 */

public class PaytmBean {
    private int code ;
    private String STATUS;
    private String CHECKSUMHASH;
    private String BANKNAME;
    private String ORDERID;
    private String TXNAMOUNT;
    private String TXNDATE;
    private String MID;
    private String TXNID;
    private String RESPCODE;
    private String PAYMENTMODE;
    private String BANKTXNID;
    private String GATEWAYNAME;
    private String RESPMSG;
    private String CURRENCY;
    private String errmsg;

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getCHECKSUMHASH() {
        return CHECKSUMHASH;
    }

    public void setCHECKSUMHASH(String CHECKSUMHASH) {
        this.CHECKSUMHASH = CHECKSUMHASH;
    }

    public String getBANKNAME() {
        return BANKNAME;
    }

    public void setBANKNAME(String BANKNAME) {
        this.BANKNAME = BANKNAME;
    }

    public String getORDERID() {
        return ORDERID;
    }

    public void setORDERID(String ORDERID) {
        this.ORDERID = ORDERID;
    }

    public String getTXNAMOUNT() {
        return TXNAMOUNT;
    }

    public void setTXNAMOUNT(String TXNAMOUNT) {
        this.TXNAMOUNT = TXNAMOUNT;
    }

    public String getTXNDATE() {
        return TXNDATE;
    }

    public void setTXNDATE(String TXNDATE) {
        this.TXNDATE = TXNDATE;
    }

    public String getMID() {
        return MID;
    }

    public void setMID(String MID) {
        this.MID = MID;
    }

    public String getTXNID() {
        return TXNID;
    }

    public void setTXNID(String TXNID) {
        this.TXNID = TXNID;
    }

    public String getRESPCODE() {
        return RESPCODE;
    }

    public void setRESPCODE(String RESPCODE) {
        this.RESPCODE = RESPCODE;
    }

    public String getPAYMENTMODE() {
        return PAYMENTMODE;
    }

    public void setPAYMENTMODE(String PAYMENTMODE) {
        this.PAYMENTMODE = PAYMENTMODE;
    }

    public String getBANKTXNID() {
        return BANKTXNID;
    }

    public void setBANKTXNID(String BANKTXNID) {
        this.BANKTXNID = BANKTXNID;
    }

    public String getGATEWAYNAME() {
        return GATEWAYNAME;
    }

    public void setGATEWAYNAME(String GATEWAYNAME) {
        this.GATEWAYNAME = GATEWAYNAME;
    }

    public String getRESPMSG() {
        return RESPMSG;
    }

    public void setRESPMSG(String RESPMSG) {
        this.RESPMSG = RESPMSG;
    }

    public String getCURRENCY() {
        return CURRENCY;
    }

    public void setCURRENCY(String CURRENCY) {
        this.CURRENCY = CURRENCY;
    }
}
