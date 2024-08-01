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
public class PinnacleUpdateLicenseRequest {
    @JsonProperty("lcnsPlateN")
    private String lcnsPlateN;

    @JsonProperty("rcvblAccNbr")
    private String rcvblAccNbr;
}
