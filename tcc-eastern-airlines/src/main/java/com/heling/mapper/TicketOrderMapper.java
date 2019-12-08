package com.heling.mapper;

import com.heling.model.TicketOrder;

public interface TicketOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TicketOrder record);

    int insertSelective(TicketOrder record);

    TicketOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TicketOrder record);

    int updateByPrimaryKey(TicketOrder record);

    TicketOrder selectByOrderNo(String orderNo);
}