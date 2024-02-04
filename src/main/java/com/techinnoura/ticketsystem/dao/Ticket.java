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
public class Ticket {

    @Id
    private String ticketId;
    private String createdBy;
    private TicketStatus ticketStatus;
    private LocalDateTime CreatedAt;
    private Boolean adminLock=false;
    private Boolean superAdminLock=false;
    private String message;
    private String assignee;
    private String comment;
    private String description;
    private String subject;




}
