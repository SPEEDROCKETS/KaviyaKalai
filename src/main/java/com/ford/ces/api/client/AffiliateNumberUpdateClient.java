package com.ford.ces.api.client;

import com.ford.ces.api.adapter.AffiliateNumberUpdateAdapter;
import com.ford.ces.api.config.ErrorProperties;
import com.ford.ces.api.domain.request.UpdateAffiliateDetailsRequest;
import com.ford.ces.api.domain.response.*;
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

import static com.ford.ces.api.domain.constants.LaConstants.*;
import static com.ford.ces.api.exception.ErrorMessages.ACCOUNT_NOT_FOUND;
import static com.ford.ces.api.exception.ErrorMessages.LA_UPDATED_SUCCESSFULLY_AND_PINNACLE_UPDATE_FAILED;

@Component
public class AffiliateNumberUpdateClient extends BaseClient{
    private static final String ACTION_CD ="/?ActionCd=";
    private static final Logger LOG = LoggerFactory.getLogger(AffiliateNumberUpdateClient.class.getName());
    protected AffiliateNumberUpdateAdapter affiliateNumberUpdateAdapter;

    @Autowired
    public AffiliateNumberUpdateClient(UtilProperties utilProperties, AffiliateNumberUpdateAdapter affiliateNumberUpdateAdapter, WebClient webClient, ErrorProperties errorProperties) {
        super(utilProperties,errorProperties,webClient);
        this.utilProperties = utilProperties;
        this.affiliateNumberUpdateAdapter = affiliateNumberUpdateAdapter;
        this.errorProperties=errorProperties;
    }

    public String updateAffiliateNumberInLA(UpdateAffiliateDetailsRequest updateAffiliateDetailsRequest) {
        LOG.info("AffiliateNumberUpdateClient updateAffiliateNumberInLA -"+updateAffiliateDetailsRequest.getReceivableAccountNumber());
        if(isClientValid(updateAffiliateDetailsRequest.getClientId())) {
            String updateSeqSuccess = AFFILIATE_SEQUENCE_UPDATE_FAILED;
            var urlString = createAutoLoanAffiliateRequestUrl(INQ_ACTION_CODE);
            var laAutoLoanAffiliateRequest = affiliateNumberUpdateAdapter.buildLaAutoLoanAffiliateRequest(updateAffiliateDetailsRequest);
            var laAutoLoanAffiliateResponse = enquireOrUpdateAffiliateNumber(laAutoLoanAffiliateRequest,urlString, LaAutoLoanAffiliateFinalResponse.class);
            if(PROCESS_COMPLETE.equalsIgnoreCase(laAutoLoanAffiliateResponse.getWiaraflOperationResponse().getTSRsHdr().getProcessMessage().trim())){
                updateSeqSuccess = updateAffiliateIfAccExists(updateAffiliateDetailsRequest,laAutoLoanAffiliateResponse,updateSeqSuccess);
            } else if(ACCOUNT_NOT_FOUND.equalsIgnoreCase(laAutoLoanAffiliateResponse.getWiaraflOperationResponse().getTSRsHdr().getTrnStatus().get(0).getMsgText())){
                updateSeqSuccess= ErrorMessages.ACCOUNT_NOT_FOUND;
            }
            return updateSeqSuccess;
        } else {
            LOG.error("UpdateAffiliateNumber -"+ErrorMessages.CLIENT_ID_VALIDATION_FAILED);
            throw new LaAuthenticationFailedException(ErrorMessages.CLIENT_ID_VALIDATION_FAILED, ErrorMessages.CLIENT_ID_ERROR_CODE);
        }

    }
    private String updateAffiliateIfAccExists(UpdateAffiliateDetailsRequest updateAffiliateDetailsRequest,LaAutoLoanAffiliateFinalResponse laAutoLoanAffiliateResponse,String updateSeqSuccess){
        var affiliateNumberSeqTwo = new LaAutoLoanAffiliateFinalResponse();
        var affiliateAccountsList = laAutoLoanAffiliateResponse.getWiaraflOperationResponse().getAutoLoanAffiliateRs().get(0).getAffiliateAccounts();
        // A user can have max 99 affiliate accounts. Taking only 1st two affiliate accounts (Seq-01 & Seq-02) from the list of affiliate accounts
        if (affiliateAccountsList.size() > 1) {
            affiliateAccountsList = affiliateAccountsList.subList(0, 2);
        } else {
            // Creating sequence-02 since only one sequence is available for the user
            affiliateNumberSeqTwo = createSecondSequence(affiliateAccountsList, updateAffiliateDetailsRequest);
            if(!PROCESS_COMPLETE.equalsIgnoreCase(affiliateNumberSeqTwo.getWiaraflOperationResponse().getTSRsHdr().getProcessMessage().trim())) {
                return updateSeqSuccess;
            }
        }
        AffiliateAccounts filteredAffiliateAccount = affiliateAccountsList.stream()
                .filter(laAutoLoanAffiliateAccount -> laAutoLoanAffiliateAccount.getAffId()
                        .equals(updateAffiliateDetailsRequest.getBankAccountNumber()))
                .filter(laAutoLoanAffiliateAccount -> laAutoLoanAffiliateAccount.getAbaNum()
                        .equals(updateAffiliateDetailsRequest.getBankCode()))
                .findFirst()
                .orElse(null);
        var autoDebitFlagUpdateStatus = "";
        if (filteredAffiliateAccount == null) {
            updateSeqSuccess = updateSequences(updateAffiliateDetailsRequest, updateSeqSuccess, affiliateNumberSeqTwo, affiliateAccountsList);
        } else {
            autoDebitFlagUpdateStatus = getAutoDebitUpdateStatus(updateAffiliateDetailsRequest);
            updateSeqSuccess = (!autoDebitFlagUpdateStatus.equalsIgnoreCase(AUTO_DEBIT_UPDATE_FAILED)) ?
                    AFFILIATE_ALREADY_EXIST + AND + autoDebitFlagUpdateStatus : autoDebitFlagUpdateStatus;
        }
        return updateSeqSuccess;
    }

    private String updateSequences(UpdateAffiliateDetailsRequest updateAffiliateDetailsRequest, String updateSeqSuccess, LaAutoLoanAffiliateFinalResponse affiliateNumberSeqTwo, List<AffiliateAccounts> affiliateAccountsList) {
        String autoDebitFlagUpdateStatus;
        var urlStringForUpdate = createAutoLoanAffiliateRequestUrl(UPDATE_ACTION_CODE);
        if(affiliateAccountsList.size() > 1) {
            var affiliateNumberSeqTwoUpdateRequest = affiliateNumberUpdateAdapter.buildLaAutoLoanAffiliateUpdateOrAddRequest(affiliateAccountsList.get(1), updateAffiliateDetailsRequest, SEQ_NUM_TWO);
            affiliateNumberSeqTwo = enquireOrUpdateAffiliateNumber(affiliateNumberSeqTwoUpdateRequest, urlStringForUpdate, LaAutoLoanAffiliateFinalResponse.class);
        }
        var affiliateNumberSeqOneUpdateRequest = affiliateNumberUpdateAdapter.buildLaAutoLoanAffiliateUpdateOrAddRequest(affiliateAccountsList.get(0), updateAffiliateDetailsRequest, SEQ_NUM_ONE);
        var affiliateNumberSeqOne = enquireOrUpdateAffiliateNumber(affiliateNumberSeqOneUpdateRequest, urlStringForUpdate, LaAutoLoanAffiliateFinalResponse.class);

        if (PROCESS_COMPLETE.equalsIgnoreCase(affiliateNumberSeqTwo.getWiaraflOperationResponse().getTSRsHdr().getProcessMessage().trim())
                && PROCESS_COMPLETE.equalsIgnoreCase(affiliateNumberSeqOne.getWiaraflOperationResponse().getTSRsHdr().getProcessMessage().trim())) {
            autoDebitFlagUpdateStatus = getAutoDebitUpdateStatus(updateAffiliateDetailsRequest);
            updateSeqSuccess = (!autoDebitFlagUpdateStatus.equalsIgnoreCase(AUTO_DEBIT_UPDATE_FAILED)) ?
                    SEQUENCE_UPDATED_SUCCESSFULLY + AND + autoDebitFlagUpdateStatus : autoDebitFlagUpdateStatus;
        }
        return updateSeqSuccess;
    }

    private LaAutoLoanAffiliateFinalResponse createSecondSequence(List<AffiliateAccounts> laAutoLoanAffiliateAccounts, UpdateAffiliateDetailsRequest updateAffiliateDetailsRequest) {
        return enquireOrUpdateAffiliateNumber(affiliateNumberUpdateAdapter.buildLaAutoLoanAffiliateUpdateOrAddRequest(laAutoLoanAffiliateAccounts.get(0), updateAffiliateDetailsRequest, SEQ_NUM_TWO), createAutoLoanAffiliateRequestUrl(ADD_ACTION_CODE), LaAutoLoanAffiliateFinalResponse.class);
    }

    private String getAutoDebitUpdateStatus(UpdateAffiliateDetailsRequest updateAffiliateDetailsRequest) {
        LOG.info("AffiliateNumberUpdateClient getAutoDebitUpdateStatus -"+updateAffiliateDetailsRequest.getReceivableAccountNumber());
        String autoDebitFlagUpdateStatus;
        autoDebitFlagUpdateStatus = updateAutoDebit(updateAffiliateDetailsRequest);
        if(ACTIVE.equalsIgnoreCase(autoDebitFlagUpdateStatus)){
            autoDebitFlagUpdateStatus = AUTO_DEBIT_FLAG_IS_ALREADY_ACTIVE;
        } else if (SUSPENDED.equalsIgnoreCase(autoDebitFlagUpdateStatus)) {
            autoDebitFlagUpdateStatus = AUTO_DEBIT_FLAG_IS_SUSPENDED;
        }
        return autoDebitFlagUpdateStatus;
    }

    private String updateAutoDebit(UpdateAffiliateDetailsRequest updateAffiliateDetailsRequest) {
        LOG.info("AffiliateNumberUpdateClient updateAutoDebit -"+updateAffiliateDetailsRequest.getReceivableAccountNumber());
        var laAutoDebitResponse = getAutoDebitDataFromLa(affiliateNumberUpdateAdapter.buildLaAutodebitRequest(updateAffiliateDetailsRequest), createRequestUrlForAutoDebit(INQ_ACTION_CODE) , LAAutodebitResponse.class);
        var autoDebitStatus = laAutoDebitResponse.getWiarbilOperationResponse().autoLoanBillingDefRs.get(0).getBillingList().get(0).billingAutoDebitStatusCd;
        if(!ACTIVE.equalsIgnoreCase(autoDebitStatus) && !SUSPENDED.equalsIgnoreCase(autoDebitStatus))
        {
            var updatedLAAutoDebitResponse = updateAutoChangeProcessFlag(affiliateNumberUpdateAdapter.buildLaAutoLoanBillingChangeProcessRequest(updateAffiliateDetailsRequest), createUrlStringForAutoChangeProcessFlag(UPDATE_ACTION_CODE), LaAutoChangeProcessResponse.class);
            if(PROCESS_COMPLETE.equalsIgnoreCase(updatedLAAutoDebitResponse.getWiarbcpOperationResponse().getTSRsHdr().getProcessMessage().trim())){
                autoDebitStatus= AUTO_DEBIT_FLAG_UPDATED_TO_ACTIVE;
            } else{
                autoDebitStatus = AUTO_DEBIT_UPDATE_FAILED;
            }
        }
        return autoDebitStatus;
    }

    public String updateAffiliateNumberInPinnacle(UpdateAffiliateDetailsRequest updateAffiliateDetailsRequest, String autoDebitValueForPinnacle, String updateScenario) {
        LOG.info("AffiliateNumberUpdateClient updateAffiliateNumberInPinnacle -"+updateAffiliateDetailsRequest.getReceivableAccountNumber());
        var pinnacleResponse = updateDetailsToPinnacle(affiliateNumberUpdateAdapter.buildPinnacleAffiliateUpdateRequest(updateAffiliateDetailsRequest, autoDebitValueForPinnacle), createRequestUrlForAffiliateUpdateInPinnacle(), HttpMethod.PUT, PinnacleResponse.class);
        return SUCCESS.equalsIgnoreCase(pinnacleResponse.getResult().getResponse().getStatus())
                ? updateScenario : LA_UPDATED_SUCCESSFULLY_AND_PINNACLE_UPDATE_FAILED;
    }

    public String createAutoLoanAffiliateRequestUrl(String actionCode) {
        return utilProperties.getHost() + ":" +utilProperties.getPort()  +utilProperties.getAutoLoanAffiliateUrl() + ACTION_CD + actionCode;
    }

    public boolean isClientValid(String clientID) {
        List<String> adfsClientIds=utilProperties.getAdfsClientIds();
        return adfsClientIds.contains(clientID);
    }

    public String createRequestUrlForAutoDebit(String actionCode) {
        return utilProperties.getHost() + ":" +utilProperties.getPort()  +utilProperties.getAutoLoanBillingDef() + ACTION_CD + actionCode;
    }
    public String createUrlStringForAutoChangeProcessFlag(String actionCode) {
        return utilProperties.getHost() + ":" +utilProperties.getPort()  +utilProperties.getAutoLoanChangeProcess() + ACTION_CD + actionCode;
    }

    public String createRequestUrlForAffiliateUpdateInPinnacle() {
        return utilProperties.getPinnacleUrlForUpdateAffiliateNumber();
    }
}
