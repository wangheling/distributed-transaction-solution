package com.heling.controller;

import com.heling.model.TicketOrder;
import com.heling.requestandresponse.TicketRequest;
import com.heling.requestandresponse.TicketResponse;
import com.heling.service.TicketServie;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("ticket")
public class TicketController {

    @Resource
    private TicketServie ticketServie;


    /**
     * 尝试购票
     *
     * @param ticketRequest
     * @return
     */
    @PostMapping("try")
    public TicketResponse tryToBuy(@RequestBody TicketRequest ticketRequest) {

        TicketOrder ticketOrder = ticketServie.tryToBuy(ticketRequest.getName(), ticketRequest.getPhone(), ticketRequest.getAirLine());

        TicketResponse response = new TicketResponse();

        boolean trySuccess = ticketOrder == null ? false : true;

        response.setTrySuccess(trySuccess);

        if (trySuccess) {
            response.setOrderNo(ticketOrder.getOrderNo());
        }


        return response;
    }


    /**
     * 确认购票
     *
     * @param orderNo
     * @return
     */
    @PostMapping("confirm")
    public Map<String, String> confirmToBuy(@RequestBody Map<String,String> map) {
        ticketServie.confirmToBuy(map.get("orderNo"));
        Map<String, String> result = new HashMap<>();
        result.put("code","000");
        return result;
    }

    /**
     * 取消购票
     *
     * @param orderNo
     * @return
     */
    @PostMapping("cancel")
    public Map<String, String> cancelToBuy(@RequestBody Map<String,String> map) {
        ticketServie.cancelToBuy(map.get("orderNo"));
        Map<String, String> result = new HashMap<>();
        result.put("code","000");
        return result;
    }


}
