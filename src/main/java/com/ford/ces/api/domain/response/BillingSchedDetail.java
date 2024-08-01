package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingSchedDetail {

    @JsonProperty("BillingDefinitionId")
    private String billingDefinitionId;

    @JsonProperty("BillingScheduleKey")
    private String billingScheduleKey;

    @JsonProperty("PlanId")
    private String planId;

    @JsonProperty("DetailNum")
    private int detailNum;

    @JsonProperty("BillingStopDt")
    private String billingStopDt;

    @JsonProperty("BillingStartDt")
    private String billingStartDt;

    @JsonProperty("BillingFirstDueDt")
    private String billingFirstDueDt;

    @JsonProperty("BillingFrequencyCd")
    private String billingFrequencyCd;

    @JsonProperty("BillingDay")
    private String billingDay;

    @JsonProperty("BillingIncrement")
    private int billingIncrement;

    @JsonProperty("BillingPaymentMethodCd")
    private String billingPaymentMethodCd;

    @JsonProperty("BillingNumPmts")
    private int billingNumPmts;

    @JsonProperty("BillingScheduleSkipMonths")
    List<BillingScheduleSkipMonths> billingScheduleSkipMonths;

    @JsonProperty("BillingFlatAmt")
    private double billingFlatAmt;

    @JsonProperty("CalcCd")
    private String calcCd;

    @JsonProperty("BalCd")
    private String balCd;

    @JsonProperty("BillingBalPercentage")
    private int billingBalPercentage;

    @JsonProperty("BillingPseudoTerm")
    private int billingPseudoTerm;


}
