package com.ford.ces.api.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("classpath:/ValidationMessages.properties")
public class ErrorProperties {

    @Value("${NotFound.accountNumber}")
    String accountNumberNotFoundErrorCode;

    @Value("${RACFAuth.InvalidCredentials}")
    String racfAAuthenticationFailed;

    @Value("${Invalid.licenceNumber}")
    String licenseErrorCode;

    @Value("EC2009")
    String licenseNumberExists;

    @Value("${PinnacleUpdate.failed}")
    String pinnacleUpdateFailed;

    @Value("${PinnacleService.failed}")
    String pinnacleServiceFailed;

    @Value("${NotBlank.licenceNumber}")
    String licenseNumberNotNull;

    @Value("${Affiliate.AlreadyExist}")
    String affiliateInformationExists;

    @Value("${LaSequence.Failed}")
    String laSequenceUpdateFailed;

    @Value("${LaAutoDebit.Failed}")
    String laAutodebitUpdateFailed;

    @Value("${LaAffiliateEnquiry.Failed}")
    String laAffiliateEnquiryFailed;

    @Value("${listOfBankCodes}")
    String[] listOfBankCodes;

    @Value("${Invalid.BankCode}")
    String invalidBankCode;

    @Value("EC2026")
    String datesAlreadyExists;

    @Value("EC2027")
    String customerRelationShipNotFound;

    @Value("EC2028")
    String nationalIdUpdateFailed;

    @Value("${LaExtensionService.Failed}")
    String extensionServiceFailed;

}
