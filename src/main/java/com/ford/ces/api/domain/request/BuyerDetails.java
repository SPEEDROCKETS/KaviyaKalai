package com.ford.ces.api.domain.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.Pattern;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BuyerDetails {

    private String buyerShorName;

    @Pattern(regexp="^([0-9]{8})$|^\\s*$", message = "Expected either null or numeric with 8 digits as input for Buyer Valid Date -{Pattern.buyerValidDate}")
    private String buyerValidDate;

    @Pattern(regexp="^([0-9]{8})$|^\\s*$", message = "Expected either null or numeric with 8 digits as input for Buyer Expiry Date -{Pattern.buyerExpDate}")
    private String buyerExpDate;
}
