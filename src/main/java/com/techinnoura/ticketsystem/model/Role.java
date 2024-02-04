package com.techinnoura.ticketsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    private String roleId;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
}
