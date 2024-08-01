package com.ford.ces.api.service;

import com.ford.ces.api.client.BillingScheduleClient;
import com.ford.ces.api.config.ErrorProperties;
import com.ford.ces.api.domain.constants.LaConstants;
import com.ford.ces.api.domain.request.LicenseNumber;
import com.ford.ces.api.domain.request.ReceivableAccountNumber;
import com.ford.ces.api.domain.response.LAFinalResponse;
import com.ford.ces.api.domain.response.LaAutoLoanAutoCollaterallResponse;
import com.ford.ces.api.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import static com.ford.ces.api.exception.ErrorMessages.*;


@Service
public class BillingScheduleService {

    private static final Logger LOG = LoggerFactory.getLogger(BillingScheduleService.class.getName());

    private final BillingScheduleClient billingScheduleClient;
    ErrorProperties errorProperties;

    @Autowired
    public BillingScheduleService(BillingScheduleClient billingScheduleClient, ErrorProperties errorProperties) {
        this.billingScheduleClient = billingScheduleClient;
        this.errorProperties = errorProperties;
    }

    public LAFinalResponse getBillingSchedulePayments(ReceivableAccountNumber receivableAccountNumber) {
        LOG.info("BillingScheduleService getBillingSchedulePayments starts - ReceivableAccountNumber "+receivableAccountNumber.getReceivableAccountNumber());
        var laFinalResponse = billingScheduleClient.getBillingSchedulePayments(receivableAccountNumber);
        if(ObjectUtils.isEmpty(laFinalResponse.getWIARBSDOperationResponse().getAutoLoanBillingSchedDetailRs())){
            LOG.error("BillingSchedulePayments -"+ACCOUNT_NUMBER_NOT_FOUND);
            throw new AccountNumerNotFoundException(ACCOUNT_NUMBER_NOT_FOUND, errorProperties.getAccountNumberNotFoundErrorCode());
            }else {
                LOG.info("BillingSchedulePayments completed Successfully");
                return laFinalResponse;
             }
        }

    public LaAutoLoanAutoCollaterallResponse updateLicensePlateNumber(LicenseNumber licenseNumber) {
        LOG.info("BillingScheduleService- updateLicensePlateNumber starts "+licenseNumber.getReceivableAccountNumber());
        var laAutoLoanAutoCollaterallResponse =  billingScheduleClient.updateLicensePlateNumber(licenseNumber);
        LOG.info("LA license update response: {}",laAutoLoanAutoCollaterallResponse);
        String responseMsgText = LaConstants.PROCESS_COMPLETE;
        String responseProcessMessage=laAutoLoanAutoCollaterallResponse.getWIARACOOperationResponse().getTSRsHdr().getProcessMessage();
        if(responseMsgText.equalsIgnoreCase(responseProcessMessage.trim())) {
            var pinnacleResponse = billingScheduleClient.updateLicenseNumInPinnacle(licenseNumber);
            if (!LaConstants.SUCCESS.equalsIgnoreCase(pinnacleResponse.getResult().getResponse().getStatus())) {
                responseMsgText = LA_UPDATED_SUCCESSFULLY_AND_PINNACLE_UPDATE_FAILED;
            }
        }
        else
            responseMsgText=laAutoLoanAutoCollaterallResponse.getWIARACOOperationResponse().getTSRsHdr().getTrnStatus().get(0).getMsgText();

        switch (responseMsgText){
            case ACCOUNT_NOT_FOUND:
                LOG.error(UPDATE_LICENSE_LOGGER+INVALID_ACCOUNT);
                throw new AccountNumerNotFoundException(INVALID_ACCOUNT, errorProperties.getAccountNumberNotFoundErrorCode());
            case INVALID_LIC_REG_NUMBER_FOUND_DURING_USER_EDITS:
                LOG.error(UPDATE_LICENSE_LOGGER+INVALID_LICENSE);
                throw new LicenseNumberFormatException(INVALID_LICENSE,errorProperties.getLicenseErrorCode());
            case UPDATE_FUNCTION_SELECTED_BUT_NO_INPUT_ENTERED:
                LOG.error(UPDATE_LICENSE_LOGGER+LICENSE_NUMBER_EXIST);
                throw new LicenseNumberConflictException(LICENSE_NUMBER_EXIST,errorProperties.getLicenseNumberExists());
            case LA_UPDATED_SUCCESSFULLY_AND_PINNACLE_UPDATE_FAILED:
                LOG.info(UPDATE_LICENSE_LOGGER+LA_UPDATED_SUCCESSFULLY_AND_PINNACLE_UPDATE_FAILED);
                throw new PinnacleUpdateFailedException(LA_UPDATED_SUCCESSFULLY_AND_PINNACLE_UPDATE_FAILED, errorProperties.getPinnacleUpdateFailed());
            default:
                LOG.info("UpdateLicensePlateNumber completed Successfully");
                return laAutoLoanAutoCollaterallResponse;
        }
    }

    }





