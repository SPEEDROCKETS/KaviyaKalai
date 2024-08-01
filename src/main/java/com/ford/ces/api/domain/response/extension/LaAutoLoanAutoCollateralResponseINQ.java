package com.ford.ces.api.domain.response.extension;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LaAutoLoanAutoCollateralResponseINQ {

    @JsonProperty("WIARACOOperationResponse")
    private ExtensionWIARACOOperationResponse extensionWIARACOOperationResponse;
}
