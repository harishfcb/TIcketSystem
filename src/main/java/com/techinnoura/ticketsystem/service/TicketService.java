package com.techinnoura.ticketsystem.service;

import com.techinnoura.ticketsystem.dto.TicketInfo;
import com.techinnoura.ticketsystem.handler.TicketHandler;
import com.techinnoura.ticketsystem.dao.Ticket;

import com.techinnoura.ticketsystem.dao.TicketEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TicketService {

    @Autowired
    TicketHandler ticketHandler;
    public TicketInfo createTicket(TicketInfo ticketInfo) throws Exception {
        return ticketHandler.createTicket(ticketInfo);

    }

    public List<TicketEvent> getTicketEvent(String ticketId,int pageNumber,int count) {
      return ticketHandler.getTicketEvent(ticketId,pageNumber,count);
    }

    public Ticket updateTicket(Ticket ticket) throws Exception {
        return ticketHandler.updateTicket(ticket);
    }
}
