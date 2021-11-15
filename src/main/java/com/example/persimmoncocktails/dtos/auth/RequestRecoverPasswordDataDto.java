package com.example.persimmoncocktails.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RequestRecoverPasswordDataDto {
    @NotNull(message = "Request-ID should not be empty")
    String id;
    @NotNull(message = "Person-ID should not be empty")
    Long personId;
    LocalDateTime localDateTime;
    @NotNull(message = "New password should not be empty")
    String newPassword;
}
