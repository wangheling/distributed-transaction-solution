package com.heling.model;

public class AirTicketOrder {
    private Integer id;

    private String phone;

    private String name;

    private String orderNo;

    private String fr;

    private String to;

    private String airLineNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getFr() {
        return fr;
    }

    public void setFr(String fr) {
        this.fr = fr;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getAirLineNo() {
        return airLineNo;
    }

    public void setAirLineNo(String airLineNo) {
        this.airLineNo = airLineNo;
    }
}