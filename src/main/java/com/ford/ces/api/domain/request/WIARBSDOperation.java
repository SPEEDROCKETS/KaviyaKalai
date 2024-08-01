package com.ford.ces.api.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WIARBSDOperation {

    @JsonProperty("RqstHdr")
    RqstHdr rqstHdr;

    @JsonProperty("TSRqHdr")
    TSRqHdr tSRqHdr;

    @JsonProperty("AutoLoanBillingSchedDetailRq")
    AutoLoanBillingSchedDetailRq autoLoanBillingSchedDetailRq;


}
