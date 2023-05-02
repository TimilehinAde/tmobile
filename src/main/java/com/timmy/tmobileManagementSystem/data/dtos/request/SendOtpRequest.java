package com.timmy.tmobileManagementSystem.data.dtos.request;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class SendOtpRequest {
        @NotNull
        private String email;
}
