package com.kiger.atcrowdfunding.potal.service;

import com.kiger.atcrowdfunding.bean.Ticket;

/**
 * @ClassName TicketService
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/10/10 19:42
 * @Version 1.0
 */

public interface TicketService {
    Ticket getTicketByMemberId(Integer id);

    void saveTicket(Ticket ticket);

    void updatePstep(Ticket ticket);

    void updatePiidAndPstep(Ticket ticket);
}
