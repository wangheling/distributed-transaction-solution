package com.heling.service.impl;

import com.heling.mapper.AirInfoMapper;
import com.heling.mapper.TicketOrderMapper;
import com.heling.model.AirInfo;
import com.heling.model.TicketOrder;
import com.heling.service.TicketServie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Slf4j
public class TicketServieImpl implements TicketServie {

    @Resource
    private AirInfoMapper airInfoMapper;

    @Resource
    private TicketOrderMapper ticketOrderMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TicketOrder tryToBuy(String name, String phone, String airLine) {
        AirInfo airInfo = airInfoMapper.selectByAirLine(airLine);
        Integer tickets = airInfo.getLeftTicketNum();
        if (tickets < 1) {
            //无票
            return null;
        }
        airInfo.setLeftTicketNum(tickets - 1);
        //余票-1
        airInfoMapper.updateByPrimaryKeySelective(airInfo);
        TicketOrder order = new TicketOrder();
        order.setName(name);
        order.setPhone(phone);
        order.setTicketId(airInfo.getId());
        //订单状态：0-锁定 1-失效 2-有效
        order.setStatus("0");
        order.setOrderNo("ES" + System.currentTimeMillis());
        ticketOrderMapper.insertSelective(order);
        return order;
    }

    @Override
    public void confirmToBuy(String orderNo) {

        TicketOrder order = ticketOrderMapper.selectByOrderNo(orderNo);
        //订单状态：0-锁定 1-失效 2-有效
        order.setStatus("2");
        ticketOrderMapper.updateByPrimaryKeySelective(order);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelToBuy(String orderNo) {
        TicketOrder order = ticketOrderMapper.selectByOrderNo(orderNo);
        //订单状态：0-锁定 1-失效 2-有效
        order.setStatus("1");
        ticketOrderMapper.updateByPrimaryKeySelective(order);

        AirInfo airInfo = airInfoMapper.selectByPrimaryKey(order.getTicketId());
        //恢复余票
        airInfo.setLeftTicketNum(airInfo.getLeftTicketNum() + 1);
        airInfoMapper.updateByPrimaryKeySelective(airInfo);
    }
}
