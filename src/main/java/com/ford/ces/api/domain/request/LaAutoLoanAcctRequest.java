package com.ford.ces.api.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LaAutoLoanAcctRequest {

    @JsonProperty("WIARACTOperation")
    private WIARACTOperation wiaractOperation;
}
