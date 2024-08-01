package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LAAutoLoanCustNameAddrFormat2Response {
    @NotNull
    @JsonProperty("WIARCN2OperationResponse")
    public WIARCN2OperationResponse wiarcn2OperationResponse;
}
