package com.ford.ces.api.domain.response.extension;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AutoLoanAcctQuickViewRs {

    @JsonProperty("AcctId")
    private String acctId;

    @JsonProperty("ProductCd")
    private String productCd;

    @JsonProperty("DaysPastDue")
    private int daysPastDue;

    @JsonProperty("DaysPastDue30")
    private int daysPastDue30;

    @JsonProperty("DaysPastDue60")
    private int daysPastDue60;

    @JsonProperty("DaysPastDue90")
    private int daysPastDue90;

    @JsonProperty("DaysPastDue120")
    private int daysPastDue120;

    @JsonProperty("DaysPastDue150")
    private int daysPastDue150;

    @JsonProperty("DaysPastDue180")
    private int daysPastDue180;

    @JsonProperty("OriginalTerm")
    private int originalTerm;

    @JsonProperty("NumPmtsMade")
    private int numPmtsMade;

    @JsonProperty("NumPmtsRemaining")
    private int numPmtsRem;

    @JsonProperty("MonthsExtendedItd")
    private int monthsExtendedItd;

    @JsonProperty("CurrentTerm")
    private int currentTerm;

    @JsonProperty("MaturityDt")
    private String maturityDt;

    @JsonProperty("NextDueDt")
    private String nextDueDt;

    @JsonProperty("BillingFlatAmt")
    private BigDecimal billingFlatAmt;

    @JsonProperty("OldestDueDt")
    private String oldestDueDt;

    @JsonProperty("AcctStatusCd")
    private String acctStatusCd;

    @JsonProperty("ProcessStatusCd")
    private String processStatusCd;

    @JsonProperty("LastBalChangeDt")
    private String lastBalChangeDt;
}
