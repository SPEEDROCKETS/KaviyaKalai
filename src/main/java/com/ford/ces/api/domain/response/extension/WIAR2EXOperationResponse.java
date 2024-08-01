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
public class WIAR2EXOperationResponse {
    @JsonProperty("TSRsHdr")
    private TSRsHdr tsRsHdr;
    @JsonProperty("AutoLoanProcessExtRs")
    private List<AutoLoanProcessExtRs> autoLoanProcessExtRs;
}
