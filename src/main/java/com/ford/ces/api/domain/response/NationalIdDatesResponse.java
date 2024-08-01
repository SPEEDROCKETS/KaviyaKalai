package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ford.ces.api.domain.request.AcctDetailsInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NationalIdDatesResponse {
    @JsonProperty("AcctDetailsInfo")
    public ArrayList<AcctDetailsInfo> acctDetailsInfo;
}
