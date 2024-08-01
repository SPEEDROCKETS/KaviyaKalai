package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LaAutoLoanAutoCollaterallResponse {

    @JsonProperty("WIARACOOperationResponse")
    private WIARACOOperationResponse wIARACOOperationResponse;

}
