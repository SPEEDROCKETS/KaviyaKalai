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
public class ExtensionWIARACOOperationResponse {

    @JsonProperty("TSRsHdr")
    private TSRsHdr tSRsHdr;
    @JsonProperty("AutoLoanAutoCollateralRs")
    private List<AutoLoanAutoCollateralRs> autoLoanAutoCollateralRs;
}
