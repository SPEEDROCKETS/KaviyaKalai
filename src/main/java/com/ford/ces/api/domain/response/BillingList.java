package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingList {

    @JsonProperty("BillingDefinitionId")
    public String billingDefinitionId;
    @JsonProperty("PlanId")
    public String planId;
    @JsonProperty("BillingDefMethod")
    public String billingDefMethod;
    @JsonProperty("BillingPmtSource")
    public String billingPmtSource;
    @JsonProperty("BillingDefBillType")
    public String billingDefBillType;
    @JsonProperty("BillingNextDueDt")
    public String billingNextDueDt;
    @JsonProperty("BillingPreviousDueDt")
    public String billingPreviousDueDt;
    @JsonProperty("OldestDueDt")
    public String oldestDueDt;
    @JsonProperty("BillingCbgAfDfltInd")
    public String billingCbgAfDfltInd;
    @JsonProperty("RepaymentModifierInd")
    public String repaymentModifierInd;
    @JsonProperty("NextRepaymentDt")
    public String nextRepaymentDt;
    @JsonProperty("BillingProjectedIntInd")
    public String billingProjectedIntInd;
    @JsonProperty("BillingDefBillMsgCd")
    public String billingDefBillMsgCd;
    @JsonProperty("BillingGroupId")
    public String billingGroupId;
    @JsonProperty("BillingDefZeroBillAmt")
    public double billingDefZeroBillAmt;
    @JsonProperty("BillingDefZeroBillCd")
    public String billingDefZeroBillCd;
    @JsonProperty("BillingZeroBillWaiveInd")
    public String billingZeroBillWaiveInd;
    @JsonProperty("BillingDefZeroDueAmt")
    public double billingDefZeroDueAmt;
    @JsonProperty("BillingDefAltPmtStmtCd")
    public String billingDefAltPmtStmtCd;
    @JsonProperty("BillingDefOrigBillType")
    public String billingDefOrigBillType;
    @JsonProperty("BillingPrevAutoDebitAmt")
    public double billingPrevAutoDebitAmt;
    @JsonProperty("BillingNextBillDt")
    public String billingNextBillDt;
    @JsonProperty("BillingPreviousBillDt")
    public String billingPreviousBillDt;
    @JsonProperty("BillingLeadDays")
    public int billingLeadDays;
    @JsonProperty("BillingDefNumberOfCoupons")
    public int billingDefNumberOfCoupons;
    @JsonProperty("BillingCouponThruDt")
    public String billingCouponThruDt;
    @JsonProperty("BillingCouponCreateDt")
    public String billingCouponCreateDt;
    @JsonProperty("BillingDefCouponLeadDays")
    public int billingDefCouponLeadDays;
    @JsonProperty("BillingCouponStartDt")
    public String billingCouponStartDt;
    @JsonProperty("BillingNextCouponStartDt")
    public String billingNextCouponStartDt;
    @JsonProperty("BillingNextCouponThruDt")
    public String billingNextCouponThruDt;
    @JsonProperty("AffiliateSeq")
    public int affiliateSeq;
    @JsonProperty("BillingAutoDebitMethodCd")
    public String billingAutoDebitMethodCd;
    @JsonProperty("BillingDefAutoDays")
    public int billingDefAutoDays;
    @JsonProperty("NextAutoDebitDt")
    public String nextAutoDebitDt;
    @JsonProperty("BillingAutoDebitStatusCd")
    public String billingAutoDebitStatusCd;
    @JsonProperty("BillingAutoDebitStatChgDt")
    public String billingAutoDebitStatChgDt;
    @JsonProperty("BillingEbillStatus")
    public String billingEbillStatus;
    @JsonProperty("BillingEbillSource")
    public String billingEbillSource;
    @JsonProperty("BillingDefBillId")
    public List<BillingDefBillId> billingDefBillId;
}
