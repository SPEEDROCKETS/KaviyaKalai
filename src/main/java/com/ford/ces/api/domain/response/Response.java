package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Response {

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;
}
