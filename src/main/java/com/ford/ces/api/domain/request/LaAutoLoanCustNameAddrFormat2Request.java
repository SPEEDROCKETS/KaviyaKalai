package com.ford.ces.api.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LaAutoLoanCustNameAddrFormat2Request {

    @JsonProperty("WIARCN2Operation")
    public WIARCN2Operation wiarcn2Operation;
}
