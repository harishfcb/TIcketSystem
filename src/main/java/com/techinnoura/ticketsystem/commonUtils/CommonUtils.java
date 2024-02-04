package com.techinnoura.ticketsystem.commonUtils;

import com.techinnoura.ticketsystem.dao.Ticket;
import com.techinnoura.ticketsystem.dao.TicketEvent;
import com.techinnoura.ticketsystem.model.Role;
import com.techinnoura.ticketsystem.model.RoleType;
import com.techinnoura.ticketsystem.repository.RoleRepository;
import com.techinnoura.ticketsystem.repository.TicketEventRepository;
import com.techinnoura.ticketsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommonUtils {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TicketEventRepository ticketEventRepository;

    public RoleType roleTypeFromRoleId(String roleId) {
        return roleRepository.findById(roleId)
                .map(Role::getRoleType)
                .orElseThrow(() -> new RuntimeException("Role not found for roleId: " + roleId));
    }


    public void createAndSaveTicketEvent(Ticket ticket, String eventDescription) {
        TicketEvent event = new TicketEvent();
        event.setMessage(eventDescription);
        event.setTimeStamp(LocalDateTime.now());
        event.setTicketId(ticket.getTicketId());
        ticketEventRepository.save(event);
    }
}
