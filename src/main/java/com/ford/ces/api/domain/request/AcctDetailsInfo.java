
package com.ford.ces.api.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AcctDetailsInfo {
    @JsonProperty("Buyer")
    public ArrayList<Buyer> buyer;
    @JsonProperty("CoBuyer")
    public ArrayList<CoBuyer> coBuyer;
}