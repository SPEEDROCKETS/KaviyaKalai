package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class WIARCN2OperationResponse {

    @JsonProperty("TSRsHdr")
    private TSRsHdr tSRsHdr;

    @JsonProperty("AutoLoanCustNameAddrFormat2Rs")
    public ArrayList<AutoLoanCustNameAddrFormat2Rs> autoLoanCustNameAddrFormat2Rs;
}
