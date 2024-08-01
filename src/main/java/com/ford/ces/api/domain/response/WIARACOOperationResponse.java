package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class WIARACOOperationResponse {

    @JsonProperty("TSRsHdr")
    private TSRsHdr tSRsHdr;
}
