package com.ford.ces.api.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PinnacleUpdateAffiliateRequest {

    @JsonProperty("rcvblAccNbr")
    private String rcvblAccNbr;

    @JsonProperty("bankAcctNumber")
    private String bankAcctNumber;

    @JsonProperty("bankCode")
    private String bankCode;

    @JsonProperty("autoDebtStatusCd")
    private String autoDebtStatusCd;

}
