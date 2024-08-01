package com.ford.ces.api.domain.request.extension;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ford.ces.api.domain.request.RqstHdr;
import com.ford.ces.api.domain.request.TSRqHdr;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class WIAR2EXOperation {
    @JsonProperty("RqstHdr")
    RqstHdr rqstHdr;
    @JsonProperty("TSRqHdr")
    TSRqHdr tSRqHdr;
    @JsonProperty("AutoLoanProcessExtRq")
    List<AutoLoanProcessExtRq> autoLoanProcessExtRq;
}
