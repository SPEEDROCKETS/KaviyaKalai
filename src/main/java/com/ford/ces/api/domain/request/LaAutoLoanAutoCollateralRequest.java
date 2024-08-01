package com.ford.ces.api.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LaAutoLoanAutoCollateralRequest {

    @JsonProperty("WIARACOOperation")
    private WIARACOOperation wIARACOOperation;
}
