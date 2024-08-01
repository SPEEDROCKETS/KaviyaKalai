
package com.ford.ces.api.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CoBuyer{
    @JsonProperty("CoBuyerName")
    public String coBuyerName;
    @JsonProperty("NationalId")
    public String nationalId;
    @JsonProperty("NationalType")
    public String nationalType;
    @JsonProperty("CoBuyerValidDate")
    public String coBuyerValidDate;
    @JsonProperty("CoBuyerExpireDate")
    public String coBuyerExpireDate;
}