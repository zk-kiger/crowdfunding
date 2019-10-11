package com.kiger.atcrowdfunding.potal.service.impl;

import com.kiger.atcrowdfunding.bean.Ticket;
import com.kiger.atcrowdfunding.potal.service.TicketService;
import com.kiger.atcrowdfunding.potal.dao.TicketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName TicketServiceImpl
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/10/10 19:43
 * @Version 1.0
 */

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketMapper ticketMapper;


    public Ticket getTicketByMemberId(Integer id) {
        return ticketMapper.getTicketByMemberId(id);
    }

    public void saveTicket(Ticket ticket) {
        ticketMapper.saveTicket(ticket);
    }

    public void updatePstep(Ticket ticket) {
        ticketMapper.updatePstep(ticket);
    }

    public void updatePiidAndPstep(Ticket ticket) {
        ticketMapper.updatePiidAndPstep(ticket);
    }
}
