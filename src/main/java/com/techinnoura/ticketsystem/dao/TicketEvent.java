package com.techinnoura.ticketsystem.dao;

import com.techinnoura.ticketsystem.model.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketEvent {
    @Id
    private  String id;
    private String ticketId;
    private String description;
    private String assigne;
    private LocalDateTime timeStamp;
    private String subject;
    private String message;
    private String comment;
    private TicketStatus ticketStatus;
    private String createdBy;

}
