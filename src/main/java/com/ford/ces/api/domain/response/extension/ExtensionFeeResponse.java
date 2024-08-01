package com.ford.ces.api.domain.response.extension;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExtensionFeeResponse {
    @JsonProperty(value = "receivableAccountNumber")
    private String receivableAccountNumber;
    @JsonProperty(value = "extensionDetails")
    private List<ExtensionFee> extensionFeeList;
}
