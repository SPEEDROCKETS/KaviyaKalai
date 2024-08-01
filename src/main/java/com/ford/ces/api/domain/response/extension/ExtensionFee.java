package com.ford.ces.api.domain.response.extension;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExtensionFee {

    private int numberOfMonthsExtended;
    private BigDecimal feeAmt;
    private String nextPaymentStartDate;
    private String newMaturityDate;
}
