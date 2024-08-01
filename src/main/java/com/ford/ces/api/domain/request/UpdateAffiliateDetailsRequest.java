package com.ford.ces.api.domain.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateAffiliateDetailsRequest implements Serializable {

    @NotBlank(message = "Receivable Account Number should not be blank -{NotBlank.accountNumber}")
    @Size(min =8 ,max = 15,message = "Receivable Account Number size must be between 8 and 15 -{Size.accountNumber}")
    @Pattern(regexp="^(0|[1-9][0-9]*)$|^\\s*$", message = "Expect only Numeric values as input -{Pattern.accountNumber}")
    private String receivableAccountNumber;

    @NotBlank(message = "Client Id should not be blank -{NotBlank.clientId}")
    @Pattern(regexp="^[a-zA-Z0-9-]*$", message = "Accept Only Alpha Numeric values as input for Client Id")
    @Size(min =8 ,max = 36,message = "Client Id size must be between 8 and 36 -{Size.clientID}")
    private String clientId;

    @NotBlank(message = "Bank account number should not be blank -{NotBlank.bankAccountNumber}")
    @Size(min =1 ,max = 19,message = "Bank account number size must be between 0 and 19-{Size.bankAccountNumber}")
    @Pattern(regexp="^(0|[1-9][0-9]*)$|^\\s*$", message = "Expect only Numeric values as input -{Pattern.accountNumber}")
    private String bankAccountNumber;

    @NotBlank(message = "Bank Code should not be blank -{NotBlank.bankCode}")
    @Size(min =1 ,max = 19,message = "Bank code size must be between 0 and 19-{Size.bankCode}")
    @Pattern(regexp="^(0|[1-9][0-9]*)$|^\\s*$", message = "Expect only Numeric values as input -{Pattern.bankCode}")
    private String bankCode;

}

