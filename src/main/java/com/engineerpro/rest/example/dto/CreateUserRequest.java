package com.engineerpro.rest.example.dto;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {
    @Length(max = 100 , min =1)
    @NotNull
    private String name;
    
    @PositiveOrZero
    @NotNull
    private int initialBalance;

}
