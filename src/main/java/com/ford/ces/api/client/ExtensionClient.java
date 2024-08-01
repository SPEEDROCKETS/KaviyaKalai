package com.ford.ces.api.client;

import com.ford.ces.api.adapter.ExtensionAdapter;
import com.ford.ces.api.adapter.ValidateNationalIdDatesAdapter;
import com.ford.ces.api.config.ErrorProperties;
import com.ford.ces.api.domain.constants.LaConstants;
import com.ford.ces.api.domain.request.ExtensionRequest;
import com.ford.ces.api.domain.request.ProcessExtensionRequest;
import com.ford.ces.api.domain.request.ReceivableAccountNumber;
import com.ford.ces.api.domain.response.LAAutoLoanEndorserResponse;
import com.ford.ces.api.domain.response.extension.*;
import com.ford.ces.api.exception.AccountNumerNotFoundException;
import com.ford.ces.api.exception.ErrorMessages;
import com.ford.ces.api.exception.LAServiceException;
import com.ford.ces.api.exception.LaAuthenticationFailedException;
import com.ford.ces.api.util.UtilProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

import static com.ford.ces.api.domain.constants.LaConstants.INQ_ACTION_CODE;
import static com.ford.ces.api.exception.ErrorMessages.ACCOUNT_NUMBER_NOT_FOUND;

@Component
public class ExtensionClient extends BaseClient {


    private static final String ACTION_CD = "/?ActionCd=";
    private static final Logger LOG = LoggerFactory.getLogger(ExtensionClient.class.getName());
    protected ExtensionAdapter extensionAdapter;
    protected ValidateNationalIdDatesAdapter validateNationalIdDatesAdapter;


    @Autowired
    public ExtensionClient(UtilProperties utilProperties, ExtensionAdapter extensionAdapter, ErrorProperties errorProperties
            , WebClient webClient, ValidateNationalIdDatesAdapter validateNationalIdDatesAdapter) {
        super(utilProperties, errorProperties, webClient);
        this.extensionAdapter = extensionAdapter;
        this.validateNationalIdDatesAdapter = validateNationalIdDatesAdapter;
    }

    public LaAutoLoanAutoCollateralResponseINQ getAutoLoanAutoCollateral(ReceivableAccountNumber receivableAccountNumber) {
        LOG.info(" Get AutoLoanAutoCollateral Details starts - ReceivableAccountNumber " + receivableAccountNumber.getReceivableAccountNumber());
        if (isClientValid(receivableAccountNumber.getClientId())) {
            var urlString = createRequestUrlForAutoLoanAutoCollateral(LaConstants.INQ_ACTION_CODE);
            var laExtAutoLoanAutoCollateralRequest = extensionAdapter.buildExtensionLaAutoLoanAutoCollateralRequest(receivableAccountNumber);
            LOG.info("Extension AutoLoanAutoCollateral request body: {}", laExtAutoLoanAutoCollateralRequest);
            return getDataFromLA(laExtAutoLoanAutoCollateralRequest, urlString, LaAutoLoanAutoCollateralResponseINQ.class);
        } else {
            throw new LaAuthenticationFailedException(ErrorMessages.CLIENT_ID_VALIDATION_FAILED, ErrorMessages.CLIENT_ID_ERROR_CODE);
        }

    }

    public LAAutoLoanEndorserResponse getAutoLoanEndorser(ReceivableAccountNumber receivableAccountNumber) {
        LOG.info("Get AutoLoanEndorser Details  starts - ReceivableAccountNumber " + receivableAccountNumber.getReceivableAccountNumber());
        if (isClientValid(receivableAccountNumber.getClientId())) {
            var urlString = createAutoLoanEndorserRequestUrl(INQ_ACTION_CODE);
            var laExtAutoLoanEndorserRequest = validateNationalIdDatesAdapter.buildAutoLoanEndorserRequest(receivableAccountNumber);
            LOG.info("Extension AutoLoanEndorser request body: {}", laExtAutoLoanEndorserRequest);
            return getDataFromLaForAutoLoanEndorser(laExtAutoLoanEndorserRequest, urlString, LAAutoLoanEndorserResponse.class);
        } else {
            throw new LaAuthenticationFailedException(ErrorMessages.CLIENT_ID_VALIDATION_FAILED, ErrorMessages.CLIENT_ID_ERROR_CODE);
        }
    }

    public LaAutoLoanAcctQuickViewRs getLaAutoLoanAcctQuickViewRs(ReceivableAccountNumber receivableAccountNumber) {
        LOG.info("LaAutoLoanAcctQuickView client started for Receivable Account Number" + receivableAccountNumber.getReceivableAccountNumber());
        if (isClientValid(receivableAccountNumber.getClientId())) {
            var urlString = createReqUrlForAutoLoanAcctQuickView(LaConstants.INQ_ACTION_CODE);
            var laAutoLoanAcctQuickViewRequest = extensionAdapter.buildLaAutoLoanAcctQuickViewRequest(receivableAccountNumber);
            LOG.info("LaAutoLoanAcctQuickView request body: {}", laAutoLoanAcctQuickViewRequest);
            return getDataFromLA(laAutoLoanAcctQuickViewRequest, urlString, LaAutoLoanAcctQuickViewRs.class);
        } else {
            throw new LaAuthenticationFailedException(ErrorMessages.CLIENT_ID_VALIDATION_FAILED, ErrorMessages.CLIENT_ID_ERROR_CODE);
        }
    }

    public ExtensionDetailsResponse getExtensionDetailsClient(ReceivableAccountNumber receivableAccountNumber) {

        LOG.info("Retrieve extension client started for account number {}", receivableAccountNumber);
        var extAutoLoanAutoCollateralResponse = getAutoLoanAutoCollateral(receivableAccountNumber);

        isObjectExist(extAutoLoanAutoCollateralResponse.getExtensionWIARACOOperationResponse().getAutoLoanAutoCollateralRs());
        isObjectExist(extAutoLoanAutoCollateralResponse.getExtensionWIARACOOperationResponse().getAutoLoanAutoCollateralRs().get(0).getAutoCollateralListList());
        LOG.info(" Extension LAAutoLoanAutoCollateral response :{}", extAutoLoanAutoCollateralResponse);
        var extAutoLoanEndorserResponse = getAutoLoanEndorser(receivableAccountNumber);

        LOG.info(" Extension LAAutoLoanEndorser response :{}", extAutoLoanEndorserResponse);
        var autoLoanAcctQuickViewRS = getLaAutoLoanAcctQuickViewRs(receivableAccountNumber);

        isObjectExist(autoLoanAcctQuickViewRS.getWiaraqvOperationResponse().getAutoLoanAcctQuickViewRs());
        LOG.info(" Extension LAAutoLoanQuickView response :{}", autoLoanAcctQuickViewRS);

        return extensionAdapter.buildExtensionResponse(extAutoLoanAutoCollateralResponse, extAutoLoanEndorserResponse, autoLoanAcctQuickViewRS);
    }

    public ProcessExtensionResponse processExtensionClient(ProcessExtensionRequest processExtensionRequest) {

        LOG.info("Process Extension client starts - ReceivableAccountNumber " + processExtensionRequest.getReceivableAccountNumber());
        if (isClientValid(processExtensionRequest.getClientId())) {
            var urlString = createRequestUrlForAutoLoanProcessExt(LaConstants.ADD_ACTION_CODE);
            var laAutoLoanProcessExtReq = extensionAdapter.buildLaAutoLoanProcessExtRequest(processExtensionRequest, processExtensionRequest.getProcessExtensionReq().getNumberOfMonths());
            LOG.info("LaAutoLoanProcessExtension request body: {}", laAutoLoanProcessExtReq);
            LaAutoLoanProcessExtResponse laAutoLoanProcessExtResponse = getDataFromLA(laAutoLoanProcessExtReq, urlString, LaAutoLoanProcessExtResponse.class);

            if (isLAResponseValid(laAutoLoanProcessExtResponse)) {
                return extensionAdapter.buildProcessExtensionResponse(laAutoLoanProcessExtResponse);
            }
            throw new LAServiceException(laAutoLoanProcessExtResponse.getWIAR2EXOperationResponse().getTsRsHdr().getTrnStatus().get(0).getMsgText(), errorProperties.getExtensionServiceFailed());

        } else {
            throw new LaAuthenticationFailedException(ErrorMessages.CLIENT_ID_VALIDATION_FAILED, ErrorMessages.CLIENT_ID_ERROR_CODE);
        }
    }

    public ExtensionFeeResponse extensionFeeClient(ExtensionRequest extensionRequest) {
        LOG.info("Extension fee client starts - ReceivableAccountNumber " + extensionRequest.getReceivableAccountNumber());
        if (isClientValid(extensionRequest.getClientId())) {
            var urlString = createRequestUrlForAutoLoanProcessExt(INQ_ACTION_CODE);
            LOG.info("ExtensionFee Url : {}", urlString);
            List<LaAutoLoanProcessExtResponse> laExtensionFeeResponseList = new ArrayList<>();
            for (int months = 1; months <= extensionRequest.getExtensionReq().getNumberOfMonths(); months++) {
                var laAutoLoanProcessExtReq = extensionAdapter.buildLaAutoLoanProcessExtReqExtensionFee(extensionRequest, months);
                LOG.info("LaAutoLoanProcessExtension request body: {}", laAutoLoanProcessExtReq);
                LaAutoLoanProcessExtResponse laAutoLoanProcessExtResponse= getDataFromLA(laAutoLoanProcessExtReq, urlString, LaAutoLoanProcessExtResponse.class);
                if (isLAResponseValid(laAutoLoanProcessExtResponse)) {
                    isObjectExist(laAutoLoanProcessExtResponse.getWIAR2EXOperationResponse().getAutoLoanProcessExtRs());
                    laExtensionFeeResponseList.add(laAutoLoanProcessExtResponse);
                }
                else {
                    throw new LAServiceException(laAutoLoanProcessExtResponse.getWIAR2EXOperationResponse().getTsRsHdr().getTrnStatus().get(0).getMsgText(), errorProperties.getExtensionServiceFailed());
                }
            }
            return extensionAdapter.buildExtensionFeeResponse(laExtensionFeeResponseList);
        } else {
            throw new LaAuthenticationFailedException(ErrorMessages.CLIENT_ID_VALIDATION_FAILED, ErrorMessages.CLIENT_ID_ERROR_CODE);
        }
    }

    public void isObjectExist(Object obj) {
        if (ObjectUtils.isEmpty(obj)) {
            throw new AccountNumerNotFoundException(ACCOUNT_NUMBER_NOT_FOUND, errorProperties.getAccountNumberNotFoundErrorCode());
        }
    }

    public boolean isClientValid(String clientID) {
        List<String> adfsClientIds = utilProperties.getAdfsClientIds();
        return adfsClientIds.contains(clientID);
    }

    public String createRequestUrlForAutoLoanAutoCollateral(String actionCode) {
        return utilProperties.getHost() + ":" + utilProperties.getPort() + utilProperties.getAutoLoanAutoCollateral() + ACTION_CD + actionCode;
    }

    public String createReqUrlForAutoLoanAcctQuickView(String actionCode) {
        return utilProperties.getHost() + ":" + utilProperties.getPort() + utilProperties.getAutoLoanAcctQuickViewUrl() + ACTION_CD + actionCode;
    }

    public String createAutoLoanEndorserRequestUrl(String actionCode) {
        return utilProperties.getHost() + ":" + utilProperties.getPort() + utilProperties.getAutoLoanEndorserUrl() + ACTION_CD + actionCode;
    }

    private String createRequestUrlForAutoLoanProcessExt(String actionCode) {
        return utilProperties.getHost() + ":" + utilProperties.getPort() + utilProperties.getAutoLoanProcessExt() + ACTION_CD + actionCode;
    }


    public Boolean isLAResponseValid(LaAutoLoanProcessExtResponse laAutoLoanProcessExtResponse) {
        if (laAutoLoanProcessExtResponse.getWIAR2EXOperationResponse().getTsRsHdr().getMaxSeverity().equals(LaConstants.MAX_SEVERITY)) {
            isExceptionAccountNotFound(laAutoLoanProcessExtResponse);
            return false;
        }
        return true;
    }

    public void isExceptionAccountNotFound(LaAutoLoanProcessExtResponse laAutoLoanProcessExtResponse) {
        if (laAutoLoanProcessExtResponse.getWIAR2EXOperationResponse().getTsRsHdr().getTrnStatus().get(0).getMsgText().equals(LaConstants.ACCOUNT_NOT_FOUND)) {
            throw new AccountNumerNotFoundException(laAutoLoanProcessExtResponse.getWIAR2EXOperationResponse().getTsRsHdr().getTrnStatus().get(0).getMsgText(), errorProperties.getAccountNumberNotFoundErrorCode());
        }
    }

}
