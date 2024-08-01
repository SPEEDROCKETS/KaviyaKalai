
package com.ford.ces.api.client;

import com.ford.ces.api.adapter.ValidateNationalIdDatesAdapter;
import com.ford.ces.api.config.ErrorProperties;
import com.ford.ces.api.domain.request.ReceivableAccountNumber;
import com.ford.ces.api.domain.response.LaAutoLoanAcctResponse;
import com.ford.ces.api.domain.response.LAAutoLoanCustNameAddrFormat2Response;
import com.ford.ces.api.domain.response.LAAutoLoanEndorserResponse;
import com.ford.ces.api.exception.ErrorMessages;
import com.ford.ces.api.exception.LaAuthenticationFailedException;
import com.ford.ces.api.util.UtilProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static com.ford.ces.api.domain.constants.LaConstants.INQ_ACTION_CODE;
import static com.ford.ces.api.domain.constants.LaConstants.UPDATE_ACTION_CODE;

@Component
public class ValidateNationalIdDatesClient extends BaseClient{
    private static final String ACTION_CD ="/?ActionCd=";
    private static final Logger LOG = LoggerFactory.getLogger(ValidateNationalIdDatesClient.class.getName());
    protected ValidateNationalIdDatesAdapter validateNationalIdDatesAdapter;

    @Autowired
    public ValidateNationalIdDatesClient(UtilProperties utilProperties, WebClient webClient, ValidateNationalIdDatesAdapter validateNationalIdDatesAdapter,  ErrorProperties errorProperties) {
        super(utilProperties,errorProperties,webClient);
        this.utilProperties = utilProperties;
        this.validateNationalIdDatesAdapter = validateNationalIdDatesAdapter;
        this.errorProperties=errorProperties;
        this.webClient=webClient;
    }

    public LaAutoLoanAcctResponse validateNationalIdDates(ReceivableAccountNumber receivableAccountNumber) {
        LOG.info("retrieveDate  starts - ReceivableAccountNumber "+receivableAccountNumber.getReceivableAccountNumber());
       if(isClientValid(receivableAccountNumber.getClientId())) {
            var urlString = createAutoLoanAcctRequestUrl(INQ_ACTION_CODE);
            var laAutoLoanAcctRequest = validateNationalIdDatesAdapter.buildAutoLoanAcctRequest(receivableAccountNumber);
            return getDataFromLaForAutoLoanAcct(laAutoLoanAcctRequest, urlString, LaAutoLoanAcctResponse.class);
        }else {
            throw new LaAuthenticationFailedException(ErrorMessages.CLIENT_ID_VALIDATION_FAILED, ErrorMessages.CLIENT_ID_ERROR_CODE);
        }
    }

    public LAAutoLoanEndorserResponse retrieveEndorserDetails(ReceivableAccountNumber receivableAccountNumber) {
        LOG.info("retrieveEndorserDetails  starts - ReceivableAccountNumber "+receivableAccountNumber.getReceivableAccountNumber());
        if(isClientValid(receivableAccountNumber.getClientId())) {
            var urlString = createAutoLoanEndorserRequestUrl(INQ_ACTION_CODE);
            var laAutoLoanEndorserRequest = validateNationalIdDatesAdapter.buildAutoLoanEndorserRequest(receivableAccountNumber);
            return getDataFromLaForAutoLoanEndorser(laAutoLoanEndorserRequest, urlString, LAAutoLoanEndorserResponse.class);
        }else {
            throw new LaAuthenticationFailedException(ErrorMessages.CLIENT_ID_VALIDATION_FAILED, ErrorMessages.CLIENT_ID_ERROR_CODE);
        }
    }

    public LAAutoLoanCustNameAddrFormat2Response retrieveCustomerAddressDetails(String buyerCustomerId,ReceivableAccountNumber receivableAccountNumber) {
        LOG.info("retrieveCustomerAddressDetails  starts - Address Format "+receivableAccountNumber.getReceivableAccountNumber());
        if(isClientValid(receivableAccountNumber.getClientId())) {
            var urlString = createCustAddrFormat2RequestUrl(INQ_ACTION_CODE);
            var loanCustNameAddrFormat2Request = validateNationalIdDatesAdapter.buildAutoLoanCustAddrFormat2Request(buyerCustomerId,receivableAccountNumber);
            return getDataFromLaForAutoLoanCustNameAddrFormat2(loanCustNameAddrFormat2Request, urlString, LAAutoLoanCustNameAddrFormat2Response.class);
        }else {
            throw new LaAuthenticationFailedException(ErrorMessages.CLIENT_ID_VALIDATION_FAILED, ErrorMessages.CLIENT_ID_ERROR_CODE);
        }
    }

    public LAAutoLoanCustNameAddrFormat2Response updateCustomerAddressDetails(String customerId, ReceivableAccountNumber receivableAccountNumber,String validDate,String expDate) {
        LOG.info("retrieveCustomerAddressDetails1  starts - Address Format "+receivableAccountNumber);
        if(isClientValid(receivableAccountNumber.getClientId())) {
            var urlString = createCustAddrFormat2RequestUrl(UPDATE_ACTION_CODE);
            var loanCustNameAddrFormat2Request = validateNationalIdDatesAdapter.buildAutoLoanCustAddrFormat2RequestForUpdate(customerId,receivableAccountNumber,validDate,expDate);
            return getDataFromLaForAutoLoanCustNameAddrFormat2(loanCustNameAddrFormat2Request, urlString, LAAutoLoanCustNameAddrFormat2Response.class);
        }else {
            throw new LaAuthenticationFailedException(ErrorMessages.CLIENT_ID_VALIDATION_FAILED, ErrorMessages.CLIENT_ID_ERROR_CODE);
        }
    }

    public String createAutoLoanAcctRequestUrl(String actionCode) {
        return utilProperties.getHost() + ":" +utilProperties.getPort()  +utilProperties.getAutoLoanAcctUrl() + ACTION_CD + actionCode;
    }

    public String createAutoLoanEndorserRequestUrl(String actionCode) {
        return utilProperties.getHost() + ":" +utilProperties.getPort()  +utilProperties.getAutoLoanEndorserUrl() + ACTION_CD + actionCode;
    }

    public String createCustAddrFormat2RequestUrl(String actionCode) {
        return utilProperties.getHost() + ":" +utilProperties.getPort()  +utilProperties.getAutoLoanCustAddrFormat2Url() + ACTION_CD + actionCode;
    }

    public boolean isClientValid(String clientID) {
        List<String> adfsClientIds=utilProperties.getAdfsClientIds();
       return adfsClientIds.contains(clientID);
    }
}

