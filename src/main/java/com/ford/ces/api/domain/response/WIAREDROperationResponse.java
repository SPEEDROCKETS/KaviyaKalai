package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class WIAREDROperationResponse {

    @JsonProperty("TSRsHdr")
    private TSRsHdr tSRsHdr;

    @JsonProperty("AutoLoanEndorserRs")
    public ArrayList<AutoLoanEndorserRs> autoLoanEndorserRs;
}
