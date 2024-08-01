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
public class CoBuyerDetails {

    private String coBuyerShorName;

    @Pattern(regexp="^([0-9]{8})$|^\\s*$", message = "Expected either null or numeric with 8 digits as input for CoBuyer Valid Date -{Pattern.coBuyerValidDate}")
    private String coBuyerValidDate;

    @Pattern(regexp="^([0-9]{8})$|^\\s*$", message = "Expected either null or numeric with 8 digits as input for CoBuyer Expiry Date-{Pattern.coBuyerExpDate}")
    private String coBuyerExpDate;
}
