package com.heling.requestandresponse;

import lombok.Data;

import java.io.Serializable;

@Data
public class TicketResponse implements Serializable {

    private boolean trySuccess;

    private String orderNo;

    //订单预留时间，暂不实现
    private String expireTime;

}
