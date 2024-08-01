package com.ford.ces.api.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AutoLoanEndorserRq {

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
    @JsonProperty("CustCtl1")
    public int custCtl1;
    @JsonProperty("CustCtl2")
    public int custCtl2;
    @JsonProperty("CustCtl3")
    public int custCtl3;
    @JsonProperty("CustCtl4")
    public int custCtl4;
    @JsonProperty("CustId")
    public int custId;
    @JsonProperty("RecsRequested")
    public int recsRequested;
    @JsonProperty("RelTypeCd")
    public String relTypeCd;
    @JsonProperty("AddressNum")
    public String addressNum;
    @JsonProperty("LiabilityTypeCd")
    public String liabilityTypeCd;
    @JsonProperty("OrigLiabilityTypeCd")
    public String origLiabilityTypeCd;
    @JsonProperty("LiabilityPercent")
    public int liabilityPercent;
    @JsonProperty("SendBillInd")
    public String sendBillInd;
    @JsonProperty("SendNoticeInd")
    public String sendNoticeInd;
    @JsonProperty("CreditBureauReport")
    public String creditBureauReport;
    @JsonProperty("PersonalLiabilityInd")
    public String personalLiabilityInd;
    @JsonProperty("CrBureauCustInfoCd")
    public String crBureauCustInfoCd;
    @JsonProperty("LegalStatus")
    public String legalStatus;
    @JsonProperty("EndorserBankruptcyDt")
    public String endorserBankruptcyDt;
    @JsonProperty("EndorserCaseNumber")
    public String endorserCaseNumber;
    @JsonProperty("CrBureauNegRptDt")
    public String crBureauNegRptDt;
    @JsonProperty("CrBureauNegRptReasonCd")
    public String crBureauNegRptReasonCd;
    @JsonProperty("ChapterCd")
    public String chapterCd;
    @JsonProperty("DodMiltaryBranch")
    public String dodMiltaryBranch;
    @JsonProperty("DodVerifiedDt")
    public String dodVerifiedDt;
    @JsonProperty("DodActiveDutyStartDt")
    public String dodActiveDutyStartDt;
    @JsonProperty("DodActiveDutyEndDt")
    public String dodActiveDutyEndDt;
    @JsonProperty("DodContractDtInd")
    public String dodContractDtInd;
    @JsonProperty("DodActiveDuty12MonthsInd")
    public String dodActiveDuty12MonthsInd;

}
