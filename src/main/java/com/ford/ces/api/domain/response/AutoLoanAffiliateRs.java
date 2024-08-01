package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AutoLoanAffiliateRs {

    @JsonProperty("AffiliateAccounts")
    private List<AffiliateAccounts> affiliateAccounts;

    @JsonProperty("Ctl1")
    private String ctl1;

    @JsonProperty("Ctl2")
    private String ctl2;

    @JsonProperty("Ctl3")
    private String ctl3;

    @JsonProperty("Ctl4")
    private String ctl4;

    @JsonProperty("AcctId")
    private String acctId;

    @JsonProperty("EffectiveDt")
    private String effectiveDt;

    @JsonProperty("CurrencyCd")
    private String currencyCd;

    @JsonProperty("RecsReturned")
    private String recsReturned;

    @JsonProperty("MoreInd")
    private String moreInd;

    @JsonProperty("MoreSeqNum")
    private String moreSeqNum;
}
