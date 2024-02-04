package com.techinnoura.ticketsystem.repository;

import com.techinnoura.ticketsystem.dao.TicketEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketEventRepository extends MongoRepository<TicketEvent,String> {
}
