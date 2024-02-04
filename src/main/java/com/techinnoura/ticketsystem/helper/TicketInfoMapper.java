package com.techinnoura.ticketsystem.helper;

import com.techinnoura.ticketsystem.dao.Ticket;
import com.techinnoura.ticketsystem.dao.TicketEvent;
import com.techinnoura.ticketsystem.dto.TicketInfo;
import com.techinnoura.ticketsystem.model.TicketStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TicketInfoMapper {



   public Ticket mapTicketInfoToTicket(TicketInfo ticketInfo){
            Ticket ticket=new Ticket();
            ticket.setTicketId(ticketInfo.getTicketId());
            if(ticketInfo.getAssigne()!=null) {
                ticket.setAssignee(ticketInfo.getAssigne());
            }
            ticket.setTicketStatus(TicketStatus.CREATED);
            ticket.setCreatedAt(LocalDateTime.now());
            ticket.setCreatedBy(ticketInfo.getCreatedBy());
            if(ticketInfo.getComment()!=null){
                ticket.setComment(ticket.getComment());
            }
            ticket.setDescription(ticketInfo.getDescription());
            ticket.setSubject(ticketInfo.getSubject());
            return ticket;

    }

    public TicketEvent mapTicketInfoToTicketEvent(Ticket ticket,String message) {
        TicketEvent event = new TicketEvent();
        event.setMessage(message);
        event.setTimeStamp(LocalDateTime.now());
        event.setTicketId(ticket.getTicketId());
        event.setAssigne(ticket.getAssignee());
        if(ticket.getComment()!=null){
            event.setComment(ticket.getComment());
        }
        event.setCreatedBy(ticket.getCreatedBy());

        event.setTicketStatus(ticket.getTicketStatus());

        event.setDescription(ticket.getDescription());
        event.setSubject(ticket.getSubject());
        return event;
    }
}
