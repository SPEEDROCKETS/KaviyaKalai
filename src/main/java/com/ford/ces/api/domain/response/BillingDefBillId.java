package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingDefBillId {

    @JsonProperty("BilledType")
    public String billedType;

    @JsonProperty("BillCd")
    public int billCd;

    @JsonProperty("BillingScheduleKey")
    public String billingScheduleKey;

    @JsonProperty("FinancialIncludeCd")
    public String financialIncludeCd;
}
