package com.ford.ces.api.domain.request.extension;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AutoLoanAcctQuickViewRq {
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
    @JsonProperty("RecsRequested")
    public int recsRequested;
    @JsonProperty("BillingDefinitionId")
    public String billingDefinitionId;
    @JsonProperty("PlanId")
    public String planId;
    @JsonProperty("ExternalSeq")
    public int externalSeq;
    @JsonProperty("InternalSeq")
    public int internalSeq;
    @JsonProperty("ReversalCd")
    public String reversalCd;
    @JsonProperty("MoreInd")
    public String moreInd;
    @JsonProperty("RecsRequested2")
    public int recsRequested2;
    @JsonProperty("StartDt")
    public String startDt;
    @JsonProperty("Dept")
    public String dept;
    @JsonProperty("Batch")
    public int batch;
    @JsonProperty("SeqNbr")
    public int seqNbr;
    @JsonProperty("TrnComment")
    public String trnComment;
    @JsonProperty("EndDt")
    public String endDt;
    @JsonProperty("TrnType1")
    public String trnType1;
    @JsonProperty("NewtoOld")
    public String newtoOld;
    @JsonProperty("RepostInd")
    public String repostInd;
    @JsonProperty("NsfCd")
    public String nsfCd;
    @JsonProperty("StatementInd")
    public String statementInd;
    @JsonProperty("ReferenceNum")
    public String referenceNum;
    @JsonProperty("DeleteBillHistInd")
    public String deleteBillHistInd;
    @JsonProperty("DelinqSummary")
    public ArrayList<DelinqSummary> delinqSummary;
    @JsonProperty("AcctStatInt")
    public ArrayList<AcctStatInt> acctStatInt;

}
