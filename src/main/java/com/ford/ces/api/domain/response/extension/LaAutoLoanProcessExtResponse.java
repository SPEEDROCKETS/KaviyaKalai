package com.ford.ces.api.domain.response.extension;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaAutoLoanProcessExtResponse {

    @JsonProperty("WIAR2EXOperationResponse")
    private WIAR2EXOperationResponse wIAR2EXOperationResponse;
}
