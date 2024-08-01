package com.ford.ces.api.domain.request.extension;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LaAutoLoanProcessExtRequest {
    @JsonProperty("WIAR2EXOperation")
    WIAR2EXOperation wiar2EXOperation;
}
