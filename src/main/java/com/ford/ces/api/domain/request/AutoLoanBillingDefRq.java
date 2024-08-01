package com.ford.ces.api.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AutoLoanBillingDefRq {

    @JsonProperty("Ctl1")
    private String ctl1;

    @JsonProperty("Ctl2")
    private String ctl2;

    @JsonProperty("Ctl3")
    private String ctl3;

    @JsonProperty("Ctl4")
    private String ctl4;

    @JsonProperty("AcctId")
    private String acctId;

}
