package com.heling.mapper;

import com.heling.model.AirTicketOrder;

public interface AirTicketOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AirTicketOrder record);

    int insertSelective(AirTicketOrder record);

    AirTicketOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AirTicketOrder record);

    int updateByPrimaryKey(AirTicketOrder record);
}