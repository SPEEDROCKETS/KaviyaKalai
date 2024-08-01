package com.ford.ces.api.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Buyer{
    @JsonProperty("BuyerName")
    public String buyerName;
    @JsonProperty("NationalId")
    public String nationalId;
    @JsonProperty("NationalType")
    public String nationalType;
    @JsonProperty("BuyerValidDate")
    public String buyerValidDate;
    @JsonProperty("BuyerExpireDate")
    public String buyerExpireDate;
}