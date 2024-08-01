package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AffiliateAccounts {

    @JsonProperty("SeqNum")
    private Integer seqNum;

    @JsonProperty("AffCtls")
    private String affCtls;

    @JsonProperty("AffName")
    private String affName;

    @JsonProperty("AffId")
    private String affId;

    @JsonProperty("SysCd")
    private String sysCd;

    @JsonProperty("AffTaxId")
    private String affTaxId;

    @JsonProperty("AutoDebitAmt")
    private Double autoDebitAmt;

    @JsonProperty("AbaNum")
    private String abaNum;

    @JsonProperty("AbaName")
   // @NotNull
    @Pattern(regexp = "^[ A-Za-z\\u3000\\u3400-\\u4DBF\\u4E00-\\u9FFF  　　　]*$")
    private String abaName;

    @JsonProperty("EmpLastName")
    private String empLastName;

    @JsonProperty("EmpFirstName")
    private String empFirstName;

    @JsonProperty("EmpMidInitial")
    private String empMidInitial;

    @JsonProperty("EmpTaxId")
    private String empTaxId;

    @JsonProperty("EmpPayrollType")
    private String empPayrollType;

    @JsonProperty("EmpPayrollLoc")
    private String empPayrollLoc;

    @JsonProperty("PayDeductAmt")
    private Double payDeductAmt;

    @JsonProperty("PayDeductStatus")
    private String payDeductStatus;

    @JsonProperty("AutoDebitInd")
    private String autoDebitInd;

    @JsonProperty("AutoDebitTxt")
    private String autoDebitTxt;

    @JsonProperty("AutoCreditCd")
    private String autoCreditCd;

    @JsonProperty("CompensateBalCd")
    private String compensateBalCd;

    @JsonProperty("MaturityBillInd")
    private String maturityBillInd;

    @JsonProperty("UserInd1")
    private String userInd1;

    @JsonProperty("UserInd2")
    private String userInd2;

    @JsonProperty("UserInd3")
    private String userInd3;

    @JsonProperty("AutoDebitFirstDt")
    private Integer autoDebitFirstDt;

    @JsonProperty("AutoDebitStopDt")
    private Integer autoDebitStopDt;

    @JsonProperty("AutoDebitNextDt")
    private Integer autoDebitNextDt;

    @JsonProperty("AutoDebitMethod")
    private String autoDebitMethod;

    @JsonProperty("AutoDebitFreq")
    private String autoDebitFreq;

    @JsonProperty("AutoDebitIncrement")
    private Integer autoDebitIncrement;

    @JsonProperty("AutoDebitFeeCd")
    private String autoDebitFeeCd;

    @JsonProperty("AutoDebitLeadDays")
    private Integer autoDebitLeadDays;

    @JsonProperty("AutoDebitDay")
    private String autoDebitDay;

    @JsonProperty("AutoDebitGenDate")
    private Integer autoDebitGenDate;

    @JsonProperty("AutoDebitPrevSchDt")
    private Integer autoDebitPrevSchDt;
}
