package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class WIARAFLOperationResponse {

    @JsonProperty("TSRsHdr")
    private TSRsHdr tSRsHdr;

    @JsonProperty("AutoLoanAffiliateRs")
    private List<AutoLoanAffiliateRs> autoLoanAffiliateRs;
}
