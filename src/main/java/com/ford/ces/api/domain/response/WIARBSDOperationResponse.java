package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class WIARBSDOperationResponse {

    @JsonProperty("TSRsHdr")
    private TSRsHdr tSRsHdr;


    @JsonProperty("AutoLoanBillingSchedDetailRs")
    private List<AutoLoanBillingSchedDetailRs> autoLoanBillingSchedDetailRs;


}
