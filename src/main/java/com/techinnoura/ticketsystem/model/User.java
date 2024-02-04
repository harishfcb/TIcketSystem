package com.techinnoura.ticketsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String userId;
    @NotNull(message = "userName cannot be empty")
    private String userName;
    @NotNull(message = "EmailId cannot be empty")
    private String emailId;
    @NotNull(message = "EmailId cannot be empty")
    private String roleId;
    private String managerId;
}
