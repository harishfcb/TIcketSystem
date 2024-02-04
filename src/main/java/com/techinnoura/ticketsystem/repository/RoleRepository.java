package com.techinnoura.ticketsystem.repository;

import com.techinnoura.ticketsystem.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role,String> {
}
