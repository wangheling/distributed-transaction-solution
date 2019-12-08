package com.heling.service;

import com.heling.model.TicketOrder;

public interface TicketServie {

    TicketOrder tryToBuy(String name, String phone, String airLine);

    void confirmToBuy(String orderNo);

    void cancelToBuy(String orderNo);

}
