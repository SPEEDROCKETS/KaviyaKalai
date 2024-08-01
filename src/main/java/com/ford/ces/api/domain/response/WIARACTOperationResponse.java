package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class WIARACTOperationResponse {

    @JsonProperty("TSRsHdr")
    private TSRsHdr tSRsHdr;

    @JsonProperty("AutoLoanAcctRs")
    private List<AutoLoanAcctRs> autoLoanAcctRs;
}
