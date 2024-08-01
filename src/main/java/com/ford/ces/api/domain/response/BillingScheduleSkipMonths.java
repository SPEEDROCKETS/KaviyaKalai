package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BillingScheduleSkipMonths {

    @JsonProperty("BillingSkipPmtMonths")
    private int billingSkipPmtMonths;

}
