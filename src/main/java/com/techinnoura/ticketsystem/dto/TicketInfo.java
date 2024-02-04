package com.techinnoura.ticketsystem.dto;

import com.techinnoura.ticketsystem.model.TicketStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketInfo {
    private String ticketId;
    @NotNull(message = "createdBy should not be empty")
    private String createdBy;
    private String assigne;
    @NotNull(message = "Description must not be empty")
    private String description;
    private TicketStatus ticketStatus;
    private LocalDateTime CreatedAt;
    private Boolean adminLock=false;
    private Boolean superAdminLock=false;
    //TicketEvent
    @NotNull(message = "Subject must not be Empty")
    private String subject;
    private String message;

    //TicketComments
    private String comment;



}
