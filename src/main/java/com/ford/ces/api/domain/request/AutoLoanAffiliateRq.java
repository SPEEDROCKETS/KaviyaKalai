package com.ford.ces.api.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AutoLoanAffiliateRq {


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
    @JsonProperty("SeqNum")
    public String seqNum;
    @JsonProperty("EffectiveDt")
    public String effectiveDt;
    @JsonProperty("RecsRequested")
    public int recsRequested;
    @JsonProperty("AffCtls")
    public String affCtls;
    @JsonProperty("AffName")
    public String affName;
    @JsonProperty("AffId")
    public String affId;
    @JsonProperty("SysCd")
    public String sysCd;
    @JsonProperty("AbaNum")
    public String abaNum;
    @JsonProperty("AutoDebitInd")
    public String autoDebitInd;
    @JsonProperty("AutoDebitTxt")
    public String autoDebitTxt;
    @JsonProperty("AutoCreditCd")
    public String autoCredCd;
    @JsonProperty("CompensateBalCd")
    public String compensateBalCd;
    @JsonProperty("MaturityBillInd")
    public String maturityBillInd;
    @JsonProperty("UserInd1")
    public String userInd1;
    @JsonProperty("UserInd2")
    public String userInd2;
    @JsonProperty("UserInd3")
    public String userInd3;
    @JsonProperty("AutoDebitFirstDt")
    public int autoDebitFirstDt;
    @JsonProperty("AutoDebitStopDt")
    public int autoDebitStopDt;
    @JsonProperty("AutoDebitMethod")
    public String autoDebitMethod;
    @JsonProperty("AutoDebitFreq")
    public String autoDebitFreq;
    @JsonProperty("AutoDebitIncrement")
    public Integer autoDebitIncrement;
    @JsonProperty("AutoDebitLeadDays")
    public Integer autoDebitLeadDays;
    @JsonProperty("AutoDebitDay")
    public String autoDebitDay;
}
