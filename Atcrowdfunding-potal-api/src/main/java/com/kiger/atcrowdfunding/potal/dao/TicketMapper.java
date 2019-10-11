package com.kiger.atcrowdfunding.potal.dao;

import com.kiger.atcrowdfunding.bean.Ticket;

import java.util.List;

public interface TicketMapper {
    int deleteByPrimaryKey(Integer id);

    void saveTicket(Ticket record);

    Ticket selectByPrimaryKey(Integer id);

    List<Ticket> selectAll();

    int updateByPrimaryKey(Ticket record);

    Ticket getTicketByMemberId(Integer memberid);

    void updatePstep(Ticket ticket);

    void updatePiidAndPstep(Ticket ticket);
}