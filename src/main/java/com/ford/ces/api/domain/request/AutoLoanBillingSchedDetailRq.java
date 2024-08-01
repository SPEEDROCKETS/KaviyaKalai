package com.ford.ces.api.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ford.ces.api.domain.response.BillingScheduleSkipMonths;
import lombok.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AutoLoanBillingSchedDetailRq {

    @JsonProperty("Ctl1")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String ctl1;

    @JsonProperty("Ctl2")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String ctl2;

    @JsonProperty("Ctl3")
    private String ctl3;

    @JsonProperty("Ctl4")
    private String ctl4;

    @JsonProperty("AcctId")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String acctId;

    @JsonProperty("RecsRequested")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String recsRequested;

    @JsonProperty("EffectiveDt")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String effectiveDt;

    @JsonProperty("BillingDefinitionId")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String billingDefinitionId;

    @JsonProperty("BillingScheduleKey")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String billingScheduleKey;

    @JsonProperty("PlanId")
	@Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String planId;

    @JsonProperty("BillingStopDt")
	@Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String billingStopDt;

    @JsonProperty("BillingStartDt")
	@Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String billingStartDt;

    @JsonProperty("BillingFirstDueDt")
    private String billingFirstDueDt;

    @JsonProperty("BillingFrequencyCd")
    private String billingFrequencyCd;

    @JsonProperty("BillingDay")
    private String billingDay;

    @JsonProperty("BillingIncrement")
    private String billingIncrement;

    @JsonProperty("BillingPaymentMethodCd")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String billingPaymentMethodCd;

    @JsonProperty("BillingNumPmts")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String billingNumPmts;

    @JsonProperty("BillingScheduleSkipMonths")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private List<BillingScheduleSkipMonths> billingScheduleSkipMonths;

    @JsonProperty("BillingFlatAmt")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String billingFlatAmt;

    @JsonProperty("BillingPseudoTerm")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String billingPseudoTerm;



}
