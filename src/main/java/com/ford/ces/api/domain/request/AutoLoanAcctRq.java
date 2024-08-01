package com.ford.ces.api.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AutoLoanAcctRq {

    @JsonProperty("Ctl1")
    public String ctl1;
    @JsonProperty("Ctl2")
    public String ctl2;
    @JsonProperty("Ctl3")
    public String ctl3;
    @JsonProperty("Ctl4")
    public String ctl4;
    @JsonProperty("AcctId")
    public String acctId;
    @JsonProperty("EffectiveDt")
    public String effectiveDt;

}
