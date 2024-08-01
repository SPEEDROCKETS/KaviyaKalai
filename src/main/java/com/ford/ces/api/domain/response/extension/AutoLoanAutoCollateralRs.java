package com.ford.ces.api.domain.response.extension;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AutoLoanAutoCollateralRs {

    @JsonProperty("AutoCollateralList")
    private List<AutoCollateralList> autoCollateralListList;
}
