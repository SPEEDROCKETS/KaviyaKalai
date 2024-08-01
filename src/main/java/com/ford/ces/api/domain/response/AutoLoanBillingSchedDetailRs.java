package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AutoLoanBillingSchedDetailRs {

    @JsonProperty("BillingSchedDetail")
    private List<BillingSchedDetail> billingSchedDetail;

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

     @JsonProperty("ShortName")
     @NotNull
     @Pattern(regexp = "^[ A-Za-z\\u3000\\u3400-\\u4DBF\\u4E00-\\u9FFF  　　　]*$")
     private String shortName;

     @JsonProperty("RecsReturned")
    private String recsReturned;

    @JsonProperty("MoreInd")
    private String moreInd;

    @JsonProperty("MoreBillingDefinitionId")
    private String moreBillingDefinitionId;

    @JsonProperty("MoreBillingScheduleKey")
    private String moreBillingScheduleKey;

    @JsonProperty("MoreBillingStopDt")
    private String moreBillingStopDt;


}
