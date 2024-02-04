package com.techinnoura.ticketsystem.repository;

import com.techinnoura.ticketsystem.dao.TicketEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FilterTicketRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<TicketEvent> getTicketEvent(String ticketId, int pageNumber, int count) {
        Query query = new Query();
        Sort sort = Sort.by(Sort.Order.desc("timeStamp"));
        Pageable pageable = PageRequest.of(pageNumber - 1, count, sort);

        Criteria poCriteria = new Criteria();
        poCriteria.andOperator(Criteria.where("ticketId").is(ticketId));
        query.addCriteria(poCriteria);
        return mongoTemplate.find(query.with(pageable), TicketEvent.class, "ticketEvent");


    }


}
