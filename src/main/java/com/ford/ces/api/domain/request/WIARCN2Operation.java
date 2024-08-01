package com.ford.ces.api.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WIARCN2Operation {

    @JsonProperty("RqstHdr")
    public RqstHdr rqstHdr;

    @JsonProperty("TSRqHdr")
    public TSRqHdr tSRqHdr;

    @JsonProperty("AutoLoanCustNameAddrFormat2Rq")
    public AutoLoanCustNameAddrFormat2Rq autoLoanCustNameAddrFormat2Rq;

}
