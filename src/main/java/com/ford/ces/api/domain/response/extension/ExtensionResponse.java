package com.ford.ces.api.domain.response.extension;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ExtensionResponse {
    private String receivableAccountNumber;

    private String productCd;

    private boolean relationshipFound;

    private String newUsedCd;

    private Integer daysPastDue;

    private Integer daysPastDue30;

    private Integer daysPastDue60;

    private Integer daysPastDue90;

    private Integer daysPastDue120;

    private Integer daysPastDue150;

    private Integer daysPastDue180;

    private Integer originalTerm;

    private Integer numPmtsRemaining;

    private Integer numPmtsMade;

    private Integer monthsExtendedItd;

    private Integer currentTerm;

    private String maturityDt;

    private String nextDueDt;

    private BigDecimal scheduledPmtAmt;

    private String pastDueDt;

    private String acctStatusCd;

    private String processStatusCd;

    private String lastBalChangeDt;

}
