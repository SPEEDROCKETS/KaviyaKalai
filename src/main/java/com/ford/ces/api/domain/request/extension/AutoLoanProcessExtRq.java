package com.ford.ces.api.domain.request.extension;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AutoLoanProcessExtRq {
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
    @JsonProperty("Dept")
    private String dept;
    @JsonProperty("Batch")
    private Integer batch;
    @JsonProperty("SeqNbr")
    private Integer seqNbr;
    @JsonProperty("EffectiveDt")
    private String effectiveDt;
    @JsonProperty("ExtensionInd")
    private String extensionInd;
    @JsonProperty("GenerateAgreementLetterCd")
    private String generateAgreementLetterCd;
    @JsonProperty("FeeAmt")
    private Integer feeAmt;
    @JsonProperty("FeeAccrualTerm")
    private Integer feeAccrualTerm;
    @JsonProperty("PaymentsToExtend")
    private Integer paymentsToExtend;
    @JsonProperty("InterestFirstDueDt")
    private String interestFirstDueDt;
    @JsonProperty("BillingDefinitionId")
    private String billingDefinitionId;
    @JsonProperty("BillingDetailPlan")
    private String billingDetailPlan;
    @JsonProperty("SkipMonthsInd")
    private String skipMonthsInd;
    @JsonProperty("FirstDueAfterExtensionDt")
    private String firstDueAfterExtensionDt;
    @JsonProperty("PaymentDueDay")
    private String paymentDueDay;
    @JsonProperty("OtherDueDay")
    private String otherDueDay;
    @JsonProperty("StartExtensionDt")
    private String startExtensionDt;
    @JsonProperty("DaysToExtend")
    private Integer daysToExtend;
    @JsonProperty("ExtensionReasonCd")
    private String extensionReasonCd;
    @JsonProperty("NameAddrDet")
    private List<NameAddrDet> nameAddrDet;
    @JsonProperty("WaiveLateChargeAmt")
    private Integer waiveLateChargeAmt;
    @JsonProperty("ExtFeeUserCalcMethod")
    private String extFeeUserCalcMethod;
    @JsonProperty("ExtFeeUserCalcAmt")
    private Integer extFeeUserCalcAmt;
    @JsonProperty("ExtLetterMailCode")
    private String extLetterMailCode;
    @JsonProperty("ExtLetterFaxAreaCd")
    private String extLetterFaxAreaCd;
    @JsonProperty("ExtLetterFaxNumber")
    private String extLetterFaxNumber;
    @JsonProperty("City")
    private String city;
    @JsonProperty("State")
    private String state;
    @JsonProperty("Zip")
    private String zip;
    @JsonProperty("ExtLetterEmail")
    private String extLetterEmail;
    @JsonProperty("ExtUserAmt")
    private Integer extUserAmt;
    @JsonProperty("ExtReasonGrantedCode")
    private String extReasonGrantedCode;
}
