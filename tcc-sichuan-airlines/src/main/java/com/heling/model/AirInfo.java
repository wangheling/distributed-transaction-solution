package com.heling.model;

public class AirInfo {
    private Integer id;

    private String airLine;

    private Integer leftTicketNum;

    private String fr;

    private String des;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAirLine() {
        return airLine;
    }

    public void setAirLine(String airLine) {
        this.airLine = airLine;
    }

    public Integer getLeftTicketNum() {
        return leftTicketNum;
    }

    public void setLeftTicketNum(Integer leftTicketNum) {
        this.leftTicketNum = leftTicketNum;
    }

    public String getFr() {
        return fr;
    }

    public void setFr(String fr) {
        this.fr = fr;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}