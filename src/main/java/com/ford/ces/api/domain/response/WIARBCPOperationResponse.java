package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class WIARBCPOperationResponse {
    @JsonProperty("TSRsHdr")
    public TSRsHdr tSRsHdr;

}
