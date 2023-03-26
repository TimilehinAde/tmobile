package com.timmy.tmobileManagementSystem.data.dtos.request;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class ConfirmTokenRequest {
        @NotNull
        private String otpNumber;
}
