package com.ford.ces.api.domain.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.Valid;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NationalIdUpdateRequest {

    @Valid
    private ReceivableAccountNumber receivableAccountNumber;
    @Valid
    private BuyerDetails buyerDetails;
    @Valid
    private CoBuyerDetails coBuyerDetails;
}
