package com.ufcg.psoft.pitsA.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.pitsA.exception.PitsAException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    @JsonProperty("message")
    private String message;
    @JsonProperty("errors")
    private List<String> errors;
    public ErrorMessage(PitsAException e) {
        this.message = e.getMessage();
        this.errors = new ArrayList<>();
    }
}