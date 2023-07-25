package com.leveltwo.partycontrol.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 7702134516418120340L;
    private Boolean success;
    private String message;
}
