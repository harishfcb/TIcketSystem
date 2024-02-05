package com.techinnoura.ticketsystem.repository;

import com.techinnoura.ticketsystem.dao.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TicketRepository extends MongoRepository<Ticket,String> {
}
