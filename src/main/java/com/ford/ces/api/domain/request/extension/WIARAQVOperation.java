package com.ford.ces.api.domain.request.extension;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ford.ces.api.domain.request.RqstHdr;
import com.ford.ces.api.domain.request.TSRqHdr;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class WIARAQVOperation {
    @JsonProperty("RqstHdr")
    public RqstHdr rqstHdr;
    @JsonProperty("TSRqHdr")
    public TSRqHdr tSRqHdr;
    @JsonProperty("AutoLoanAcctQuickViewRq")
    public AutoLoanAcctQuickViewRq autoLoanAcctQuickViewRq;
}
