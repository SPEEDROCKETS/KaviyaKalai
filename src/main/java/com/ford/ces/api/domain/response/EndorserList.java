package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EndorserList {

    @JsonProperty("RelTypeCd")
    public String relTypeCd;
    @JsonProperty("CustCtl1")
    public int custCtl1;
    @JsonProperty("CustCtl2")
    public int custCtl2;
    @JsonProperty("CustCtl3")
    public int custCtl3;
    @JsonProperty("CustCtl4")
    public int custCtl4;
    @JsonProperty("CustId")
    public long custId;
    @JsonProperty("AddressNum")
    public String addressNum;
    @JsonProperty("EndorserShortName")
    public String endorserShortName;
    @JsonProperty("LastRptdCustInfoCd")
    public String lastRptdCustInfoCd;
    @JsonProperty("LiabilityTypeCd")
    public String liabilityTypeCd;
    @JsonProperty("OrigLiabilityTypeCd")
    public String origLiabilityTypeCd;
    @JsonProperty("LastLiabilityTypeCd")
    public String lastLiabilityTypeCd;
    @JsonProperty("CrBureauPreLiabilityTypeCd")
    public String crBureauPreLiabilityTypeCd;
    @JsonProperty("LiabilityPercent")
    public double liabilityPercent;
    @JsonProperty("SendBillInd")
    public String sendBillInd;
    @JsonProperty("SendNoticeInd")
    public String sendNoticeInd;
    @JsonProperty("CreditBureauReport")
    public String creditBureauReport;
    @JsonProperty("State")
    public String state;
    @JsonProperty("NoticeSentDt")
    public String noticeSentDt;
    @JsonProperty("CrBureauAssocChgCd")
    public String crBureauAssocChgCd;
    @JsonProperty("PersonalLiabilityInd")
    public String personalLiabilityInd;
    @JsonProperty("CrBureauCustInfoCd")
    public String crBureauCustInfoCd;
    @JsonProperty("LegalStatus")
    public String legalStatus;
    @JsonProperty("EndorserBankruptcyDt")
    public String endorserBankruptcyDt;
    @JsonProperty("CellPhoneOptionCd")
    public String cellPhoneOptionCd;
    @JsonProperty("CellPhoneOptionDt")
    public String cellPhoneOptionDt;
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
