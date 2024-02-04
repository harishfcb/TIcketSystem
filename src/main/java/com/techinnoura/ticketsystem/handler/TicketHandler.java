package com.techinnoura.ticketsystem.handler;

import com.techinnoura.ticketsystem.commonUtils.CommonUtils;
import com.techinnoura.ticketsystem.dao.Ticket;
import com.techinnoura.ticketsystem.dao.TicketEvent;
import com.techinnoura.ticketsystem.dto.TicketInfo;
import com.techinnoura.ticketsystem.helper.TicketInfoMapper;
import com.techinnoura.ticketsystem.model.RoleType;
import com.techinnoura.ticketsystem.model.TicketStatus;
import com.techinnoura.ticketsystem.repository.FilterTicketRepository;
import com.techinnoura.ticketsystem.repository.TicketEventRepository;
import com.techinnoura.ticketsystem.repository.TicketRepository;
import com.techinnoura.ticketsystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.beans.FeatureDescriptor;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Component
@Slf4j
public class TicketHandler {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketEventRepository ticketEventRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    CommonUtils commonUtils;

    @Autowired
    TicketInfoMapper mapper;

    @Autowired
    FilterTicketRepository filterTicketRepository;

    @Transactional
    public TicketInfo createTicket(TicketInfo ticketInfo) {
        try {
            String ticketId = UUID.randomUUID().toString(); //Generating UUID fot ticketId
            log.info("TicketId : " + ticketId);
            ticketInfo.setTicketId(ticketId);

            String createdMessage = "Ticket has been Created";
            Ticket ticket = mapper.mapTicketInfoToTicket(ticketInfo);
            ticket.setMessage(createdMessage);
            /*** Mapping The objects fot TicketInfo to Ticket object***/

            TicketEvent createdEvent = mapper.mapTicketInfoToTicketEvent(ticket, createdMessage);

            /*** Mapping The objects fot TicketInfo to TicketEvent Object ***/


            String managerId = getManagerId(ticketInfo.getCreatedBy());
            ticket.setAssignee(managerId);
            ticket.setTicketStatus(TicketStatus.ASSIGNED);

            String assignedMessage = "Ticket has been Assigned to " + managerId;
            ticket.setMessage(assignedMessage);

            TicketEvent assignedEvent = mapper.mapTicketInfoToTicketEvent(ticket, assignedMessage);

            /*** Mapping The objects fot TicketInfo to TicketEvent Object
             Here a manager is assigned to a ticket when coder(User) is initially created He/She will be
             Assgined to a specific Manager***/


            /** Consolidated save operation**/
            ticketRepository.save(ticket);

            /** Save ticket events **/
            ticketEventRepository.save(createdEvent);
            ticketEventRepository.save(assignedEvent);

            /** Updating  the fields to give complete response **/
            updateTicketInfo(ticketInfo, ticket);
            log.info("Ticket Created : " + ticket.getTicketId());
            return ticketInfo;
        } catch (Exception e) {
            log.error("Error creating ticket: " + e.getMessage());
            throw new RuntimeException("Error creating ticket");
        }
    }

    private String getManagerId(String createdBy) {
        return userRepository.findByEmailId(createdBy)
                .map(user -> user.getManagerId())
                .orElseThrow(() -> new RuntimeException("Manager ID not found for user: " + createdBy));
    }

    private void updateTicketInfo(TicketInfo ticketInfo, Ticket ticket) {
        ticketInfo.setMessage(ticket.getMessage());
        ticketInfo.setCreatedAt(ticket.getCreatedAt());
        ticketInfo.setAssigne(ticket.getAssignee());
        ticketInfo.setTicketStatus(ticket.getTicketStatus());
    }


    public List<TicketEvent> getTicketEvent(String ticketId, int pageNumber, int count) {
        return filterTicketRepository.getTicketEvent(ticketId,pageNumber,count);
    }


    public Ticket  updateTicket(Ticket ticket) throws Exception {

        TicketEvent approvalEvent=null;
        TicketEvent assigneEvent=null;
        Ticket savedTicket= ticketRepository.findById(ticket.getTicketId()).get();
        final BeanWrapper wrappedSource=new BeanWrapperImpl(ticket);
        log.info("Wrapped Source : " + wrappedSource);
        String array[] = Stream.of(wrappedSource.getPropertyDescriptors()).map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
        if ((ticket.getTicketStatus()!=null &&ticket.getTicketStatus().equals(TicketStatus.ASSIGNED_WITH_ADMIN))
                && ticket.getAssignee()!=null) {
            String roleId=userRepository.findByEmailId(ticket.getAssignee()).get().getRoleId();
            RoleType roleType=commonUtils.roleTypeFromRoleId(roleId);
            log.info("RoleType : " +roleType);
            if(!roleType.equals(RoleType.ADMIN)){
                throw new Exception("Only admins can be assigned");
            }
            Boolean existingTicket = savedTicket.getAdminLock();
            log.info("exists " + existingTicket);
            if (existingTicket) {
                throw new Exception("Ticket has already been assigned to an admin");
            } else {
                ticket.setAdminLock(true);
                assigneEvent= mapper.mapTicketInfoToTicketEvent(ticket, "Awaiting approval with admin " + ticket.getAssignee());

            }
        }

        if ((ticket.getTicketStatus()!=null &&
                ticket.getTicketStatus().equals(TicketStatus.ASSIGNED_WITH_SUPER_ADMIN) && ticket.getAssignee() != null)) {
            Boolean existingTicket = savedTicket.getSuperAdminLock();
            String roleId=userRepository.findByEmailId(ticket.getAssignee()).get().getRoleId();
            RoleType roleType=commonUtils.roleTypeFromRoleId(roleId);
            if(!roleType.equals(RoleType.SUPERADMIN)){
                throw new Exception("Only SuperAdmins can be assigned");
            }
            if (existingTicket) {
                throw new Exception("Ticket has already been assigned to a super admin");
            } else {
                ticket.setSuperAdminLock(true);
                assigneEvent= mapper.mapTicketInfoToTicketEvent(ticket, "Awaiting approval with admin " + ticket.getAssignee());
            }
        }


        if (ticket.getTicketStatus()!=null &&
                ticket.getTicketStatus().equals(TicketStatus.APPROVED)) {
            String assigneRoleId = userRepository.findByEmailId(ticket.getAssignee()).get().getRoleId();
            String assignedUserRole = commonUtils.roleTypeFromRoleId(assigneRoleId).toString();
            switch (assignedUserRole) {
                case "MANAGER":
                    ticket.setTicketStatus(TicketStatus.AWAITING_ADMIN_APPROVAL);

                    BeanUtils.copyProperties(ticket, savedTicket, array);
                    ticketRepository.save(savedTicket);
                    approvalEvent=mapper.mapTicketInfoToTicketEvent(savedTicket, "Approved by " + ticket.getAssignee());
                    assigneEvent= mapper.mapTicketInfoToTicketEvent(savedTicket, "Awaiting approval with admin");
                    ticketEventRepository.save(approvalEvent);
                    ticketEventRepository.save(assigneEvent);
                    return savedTicket;

                case "ADMIN":
                    ticket.setTicketStatus(TicketStatus.AWAITING_SUPER_ADMIN_APPROVAL);

                    BeanUtils.copyProperties(ticket, savedTicket, array);
                    ticketRepository.save(savedTicket);
                    approvalEvent= mapper.mapTicketInfoToTicketEvent(savedTicket, "Approved by " + ticket.getAssignee());
                    assigneEvent=mapper.mapTicketInfoToTicketEvent(savedTicket, "Awaiting approval with super admin");
                    ticketEventRepository.save(approvalEvent);
                    ticketEventRepository.save(assigneEvent);
                    return savedTicket;
                case "SUPERADMIN":
                    ticket.setTicketStatus(TicketStatus.CLOSED);
                    BeanUtils.copyProperties(ticket, savedTicket, array);
                    ticketRepository.save(savedTicket);
                    approvalEvent=mapper.mapTicketInfoToTicketEvent(savedTicket, "Closed by " + ticket.getAssignee());
                    ticketEventRepository.save(approvalEvent);
                    return savedTicket;
            }

        }

        BeanUtils.copyProperties(ticket, savedTicket, array);
        ticketRepository.save(savedTicket);
        if(assigneEvent!=null) {
            ticketEventRepository.save(assigneEvent);
        }
        return savedTicket;

    }


}
