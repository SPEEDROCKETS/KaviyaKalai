package com.ford.ces.api.client;

import com.ford.ces.api.adapter.BillingScheduleAdapter;
import com.ford.ces.api.config.ErrorProperties;
import com.ford.ces.api.domain.constants.LaConstants;
import com.ford.ces.api.domain.request.LicenseNumber;
import com.ford.ces.api.domain.request.ReceivableAccountNumber;
import com.ford.ces.api.domain.response.LAFinalResponse;
import com.ford.ces.api.domain.response.LaAutoLoanAutoCollaterallResponse;
import com.ford.ces.api.domain.response.PinnacleResponse;
import com.ford.ces.api.exception.ErrorMessages;
import com.ford.ces.api.exception.LaAuthenticationFailedException;
import com.ford.ces.api.util.UtilProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static com.ford.ces.api.domain.constants.LaConstants.INQ_ACTION_CODE;
import static com.ford.ces.api.exception.ErrorMessages.ACCOUNT_NUMBER_NOT_FOUND;

@Component
public class BillingScheduleClient extends BaseClient{
    private static final String ACTION_CD ="/?ActionCd=";
    private static final Logger LOG = LoggerFactory.getLogger(BillingScheduleClient.class.getName());
    protected BillingScheduleAdapter billingScheduleAdapter;


    @Autowired
    public BillingScheduleClient(UtilProperties utilProperties, WebClient webClient, BillingScheduleAdapter billingScheduleAdapter, ErrorProperties errorProperties) {
        super(utilProperties,errorProperties,webClient);
        this.billingScheduleAdapter = billingScheduleAdapter;
        this.webClient=webClient;
    }

    public LAFinalResponse getBillingSchedulePayments(ReceivableAccountNumber receivableAccountNumber) {
        LOG.info("BillingScheduleClient getBillingSchedulePayments starts - ReceivableAccountNumber "+receivableAccountNumber.getReceivableAccountNumber());
        if(isClientValid(receivableAccountNumber.getClientId())) {
            var urlString = createRequestUrl(INQ_ACTION_CODE);
            var laAutoLoanBillingRequest = billingScheduleAdapter.buildLaAutoLoanBillingRequest(receivableAccountNumber);
            return getSchedulePaymentsFromLa(laAutoLoanBillingRequest, urlString, LAFinalResponse.class);
        }else {
            LOG.error("BillingSchedulePayments -"+ErrorMessages.CLIENT_ID_VALIDATION_FAILED);
            throw new LaAuthenticationFailedException(ErrorMessages.CLIENT_ID_VALIDATION_FAILED, ErrorMessages.CLIENT_ID_ERROR_CODE);
        }
    }

    public LaAutoLoanAutoCollaterallResponse updateLicensePlateNumber(LicenseNumber licenseNumber) {
        LOG.info("BillingScheduleClient updateLicensePlateNumber starts - ReceivableAccountNumber "+licenseNumber.getReceivableAccountNumber());
        if(isClientValid(licenseNumber.getClientId())) {
            var urlString = createRequestUrlForCollateral(LaConstants.UPDATE_ACTION_CODE);
            var laAutoLoanAutoCollateralRequest = billingScheduleAdapter.buildLaAutoLoanAutoCollateralRequest(licenseNumber);
            String updateLicense = "update License request body: " + laAutoLoanAutoCollateralRequest;
            LOG.info(updateLicense);
            return updateLicenseDetails(laAutoLoanAutoCollateralRequest, urlString, LaAutoLoanAutoCollaterallResponse.class);
        }else {
            LOG.error("UpdateLicensePlateNumber - "+ErrorMessages.CLIENT_ID_VALIDATION_FAILED);
            throw new LaAuthenticationFailedException(ErrorMessages.CLIENT_ID_VALIDATION_FAILED, ErrorMessages.CLIENT_ID_ERROR_CODE);
        }

    }

    public PinnacleResponse updateLicenseNumInPinnacle(LicenseNumber licenseNumber) {
        LOG.info("BillingScheduleClient updateLicenseNumInPinnacle starts - ReceivableAccountNumber "+licenseNumber.getReceivableAccountNumber());
        return updateDetailsToPinnacle(billingScheduleAdapter.buildLicenceNumberRequestBodyRequest(licenseNumber), createRequestUrlForLicenceUpdateInPinnacle(), HttpMethod.POST, PinnacleResponse.class);

    }

    public String createRequestUrlForLicenceUpdateInPinnacle() {
        return utilProperties.getUpdateLicenceInPinnacleUrl();
    }

    public String createRequestUrlForCollateral(String actionCode) {
        return utilProperties.getHost() + ":" +utilProperties.getPort()  +utilProperties.getAutoLoanAutoCollateral() + ACTION_CD + actionCode;
    }

    public String createRequestUrl(String actionCode) {
        return utilProperties.getHost() + ":" +utilProperties.getPort()  +utilProperties.getBillingScheduleUrl() + ACTION_CD + actionCode;
    }

    private boolean isClientValid(String clientID) {
        List<String> adfsClientIds=utilProperties.getAdfsClientIds();
        return adfsClientIds.contains(clientID);
    }
}
