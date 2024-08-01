package com.ford.ces.api.domain.response.extension;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ford.ces.api.domain.response.TSRsHdr;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class WIARAQVOperationResponse {

    @JsonProperty("TSRsHdr")
    private TSRsHdr tsRsHdr;

    @JsonProperty("AutoLoanAcctQuickViewRs")
    private List<AutoLoanAcctQuickViewRs> autoLoanAcctQuickViewRs;
}
