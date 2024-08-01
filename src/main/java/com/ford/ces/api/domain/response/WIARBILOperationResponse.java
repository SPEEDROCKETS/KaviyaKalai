package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class WIARBILOperationResponse {

    @JsonProperty("TSRsHdr")
    public TSRsHdr tSRsHdr;

    @JsonProperty("AutoLoanBillingDefRs")
    public List<AutoLoanBillingDefRs> autoLoanBillingDefRs;
}
