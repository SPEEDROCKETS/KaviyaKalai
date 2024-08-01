package com.ford.ces.api.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WIAREDROperation {

    @JsonProperty("RqstHdr")
    public RqstHdr rqstHdr;

    @JsonProperty("TSRqHdr")
    public TSRqHdr tSRqHdr;

    @JsonProperty("AutoLoanEndorserRq")
    public AutoLoanEndorserRq autoLoanEndorserRq;

}
