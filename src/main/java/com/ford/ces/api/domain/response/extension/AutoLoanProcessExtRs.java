package com.ford.ces.api.domain.response.extension;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class AutoLoanProcessExtRs {
    @JsonProperty("AcctId")
    private String acctId;
    @JsonProperty("PaymentsToExtend")
    private int paymentsToExtend;
    @JsonProperty("FirstDueAfterExtensionDt")
    private String firstDueAfterExtensionDt;
    @JsonProperty("MaturityDtAfterExtension")
    private String maturityDtAfterExtension;
    @JsonProperty("MaxExtensionFeeAmt")
    private BigDecimal maxExtensionFeeAmt;
}