
package com.ford.ces.api.service;


import com.ford.ces.api.client.ValidateNationalIdDatesClient;
import com.ford.ces.api.config.ErrorProperties;
import com.ford.ces.api.domain.constants.LaConstants;
import com.ford.ces.api.domain.request.*;
import com.ford.ces.api.domain.response.LaAutoLoanAcctResponse;
import com.ford.ces.api.domain.response.LAAutoLoanEndorserResponse;
import com.ford.ces.api.domain.response.NationalIdDatesResponse;
import com.ford.ces.api.exception.AccountNumerNotFoundException;
import com.ford.ces.api.exception.DatesAlreadyExistsException;
import com.ford.ces.api.exception.ErrorMessages;
import com.ford.ces.api.exception.NationalIdUpdateFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;

import static com.ford.ces.api.domain.constants.LaConstants.*;
import static com.ford.ces.api.exception.ErrorMessages.ACCOUNT_NOT_FOUND;
import static com.ford.ces.api.exception.ErrorMessages.UPDATE_DATE_LOGGER;
import static com.ford.ces.api.exception.ErrorMessages.UPDATE_DATE_SUCCESS_LOGGER;


@Service
public class ValidateNationalIdDatesService {

    private static final Logger LOG = LoggerFactory.getLogger(ValidateNationalIdDatesService.class.getName());

    private final ValidateNationalIdDatesClient validateNationalIdDatesClient;
    ErrorProperties errorProperties;

    @Autowired
    public ValidateNationalIdDatesService(ValidateNationalIdDatesClient validateNationalIdDatesClient, ErrorProperties errorProperties) {
        this.validateNationalIdDatesClient = validateNationalIdDatesClient;
        this.errorProperties = errorProperties;
    }

    public NationalIdDatesResponse validateNationalIdDates(ReceivableAccountNumber receivableAccountNumber) {
        LOG.info("retrieveDate starts - ReceivableAccountNumber "+receivableAccountNumber.getReceivableAccountNumber());
        ArrayList<Buyer> buyerList= new ArrayList<>();
        ArrayList<CoBuyer> coBuyerList= new ArrayList<>();
        ArrayList<AcctDetailsInfo> acctDetailsInfo= new ArrayList<>();
        var laAutoLoanAcctResponse= validateNationalIdDatesClient.validateNationalIdDates(receivableAccountNumber);
        var laAutoLoanEndorserResponse = validateNationalIdDatesClient.retrieveEndorserDetails(receivableAccountNumber);
        var acctDetails = AcctDetailsInfo.builder()
                .buyer(buyerValidation(laAutoLoanAcctResponse,buyerList,receivableAccountNumber))
                .coBuyer(coBuyerValidation(laAutoLoanEndorserResponse,coBuyerList,receivableAccountNumber))
                .build();
        acctDetailsInfo.add(acctDetails);
        LOG.info("RetrieveDate completed successfully");
        return NationalIdDatesResponse.builder()
                .acctDetailsInfo(acctDetailsInfo)
                .build();
     }

    private ArrayList<Buyer> buyerValidation(LaAutoLoanAcctResponse laAutoLoanAcctResponse, ArrayList<Buyer> buyerList, ReceivableAccountNumber receivableAccountNumber) {
        String responseProcessMessage=laAutoLoanAcctResponse.getWiaractOperationResponse().getTSRsHdr().getProcessMessage();
        var messageTxt = "";
        if (LaConstants.PROCESS_COMPLETE.equalsIgnoreCase(responseProcessMessage.trim())){
            String buyerCustomerId = laAutoLoanAcctResponse.getWiaractOperationResponse().getAutoLoanAcctRs().get(0).custId;
            var laAutoLoanCustNameAddrFormat2Response = validateNationalIdDatesClient.retrieveCustomerAddressDetails(buyerCustomerId,receivableAccountNumber);
            Buyer buyer = Buyer.builder()
                    .buyerName(laAutoLoanCustNameAddrFormat2Response.getWiarcn2OperationResponse().getAutoLoanCustNameAddrFormat2Rs().get(0).surnames.get(0).getSurname())
                    .nationalId(laAutoLoanCustNameAddrFormat2Response.getWiarcn2OperationResponse().getAutoLoanCustNameAddrFormat2Rs().get(0).nationalId)
                    .nationalType(laAutoLoanCustNameAddrFormat2Response.getWiarcn2OperationResponse().getAutoLoanCustNameAddrFormat2Rs().get(0).nationalType)
                    .buyerValidDate(laAutoLoanCustNameAddrFormat2Response.getWiarcn2OperationResponse().getAutoLoanCustNameAddrFormat2Rs().get(0).nationalIdValidDt)
                    .buyerExpireDate(laAutoLoanCustNameAddrFormat2Response.getWiarcn2OperationResponse().getAutoLoanCustNameAddrFormat2Rs().get(0).nationalIdExpireDt)
                    .build();
            buyerList.add(buyer);
        }else {
            messageTxt = laAutoLoanAcctResponse.getWiaractOperationResponse().getTSRsHdr().getTrnStatus().get(0).getMsgText();
        }

        if(messageTxt.equalsIgnoreCase(ACCOUNT_NOT_FOUND)){
            throw new AccountNumerNotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND, errorProperties.getAccountNumberNotFoundErrorCode());
        }
       return buyerList;
    }


    private ArrayList<CoBuyer> coBuyerValidation(LAAutoLoanEndorserResponse laAutoLoanEndorserResponse, ArrayList<CoBuyer> coBuyerList, ReceivableAccountNumber receivableAccountNumber) {
        String responseForEndorserProcessMessage=laAutoLoanEndorserResponse.getWIAREDROperationResponse().getTSRsHdr().getProcessMessage();
        var messageTxt="";
        if (LaConstants.PROCESS_COMPLETE.equalsIgnoreCase(responseForEndorserProcessMessage.trim())){
            Long coBuyerCustomerId = laAutoLoanEndorserResponse.getWIAREDROperationResponse().getAutoLoanEndorserRs().get(0).endorserList.get(0).custId;
            var laAutoLoanCustNameAddrFormat2ResponseForCoBuyer = validateNationalIdDatesClient.retrieveCustomerAddressDetails(String.valueOf(coBuyerCustomerId),receivableAccountNumber);
            CoBuyer coBuyer = CoBuyer.builder()
                    .coBuyerName(laAutoLoanCustNameAddrFormat2ResponseForCoBuyer.getWiarcn2OperationResponse().getAutoLoanCustNameAddrFormat2Rs().get(0).surnames.get(0).getSurname())
                    .nationalId(laAutoLoanCustNameAddrFormat2ResponseForCoBuyer.getWiarcn2OperationResponse().getAutoLoanCustNameAddrFormat2Rs().get(0).nationalId)
                    .nationalType(laAutoLoanCustNameAddrFormat2ResponseForCoBuyer.getWiarcn2OperationResponse().getAutoLoanCustNameAddrFormat2Rs().get(0).nationalType)
                    .coBuyerExpireDate(laAutoLoanCustNameAddrFormat2ResponseForCoBuyer.getWiarcn2OperationResponse().getAutoLoanCustNameAddrFormat2Rs().get(0).nationalIdExpireDt)
                    .coBuyerValidDate(laAutoLoanCustNameAddrFormat2ResponseForCoBuyer.getWiarcn2OperationResponse().getAutoLoanCustNameAddrFormat2Rs().get(0).nationalIdValidDt)
                    .build();
            coBuyerList.add(coBuyer);
        } else {
            messageTxt = laAutoLoanEndorserResponse.getWIAREDROperationResponse().getTSRsHdr().getTrnStatus().get(0).getMsgText();
        }

        if(messageTxt.equalsIgnoreCase(ACCOUNT_NOT_FOUND) || messageTxt.equalsIgnoreCase(CUSTOMER_RELATIONSHIP_NOT_FOUND)){
            coBuyerList.add(coBuyerForRelationshipNotFound());
        }
        return coBuyerList;
      }

    private CoBuyer coBuyerForRelationshipNotFound(){
        return  CoBuyer.builder()
                .coBuyerName(null)
                .nationalId("")
                .nationalType("")
                .coBuyerExpireDate("")
                .coBuyerValidDate("")
                .build();
    }
    public String updateNationalIdDates(NationalIdUpdateRequest nationalIdUpdateRequest) {
        LOG.info("updateNationalIdDates starts - ReceivableAccountNumber " + nationalIdUpdateRequest.getReceivableAccountNumber());
        var messageTxt = "";
        if((!ObjectUtils.isEmpty(nationalIdUpdateRequest.getBuyerDetails().getBuyerValidDate())
                && !ObjectUtils.isEmpty(nationalIdUpdateRequest.getBuyerDetails().getBuyerExpDate()))
                && (!ObjectUtils.isEmpty(nationalIdUpdateRequest.getCoBuyerDetails().getCoBuyerValidDate())
                && !ObjectUtils.isEmpty(nationalIdUpdateRequest.getCoBuyerDetails().getCoBuyerExpDate()))){
            var laAutoLoanAcctResponseForBuyer = validateNationalIdDatesClient.validateNationalIdDates(nationalIdUpdateRequest.getReceivableAccountNumber());
            var laAutoLoanAcctResponseForCoBuyer = validateNationalIdDatesClient.retrieveEndorserDetails(nationalIdUpdateRequest.getReceivableAccountNumber());
            messageTxt = buyerAndCoBuyerUpdate(laAutoLoanAcctResponseForBuyer,laAutoLoanAcctResponseForCoBuyer,nationalIdUpdateRequest);
        } else if (!ObjectUtils.isEmpty(nationalIdUpdateRequest.getBuyerDetails().getBuyerValidDate()) && !ObjectUtils.isEmpty(nationalIdUpdateRequest.getBuyerDetails().getBuyerExpDate())) {
            var laAutoLoanAcctResponseForBuyer = validateNationalIdDatesClient.validateNationalIdDates(nationalIdUpdateRequest.getReceivableAccountNumber());
            messageTxt = buyerUpdate(laAutoLoanAcctResponseForBuyer,nationalIdUpdateRequest);
        } else if (!ObjectUtils.isEmpty(nationalIdUpdateRequest.getCoBuyerDetails().getCoBuyerExpDate()) && !ObjectUtils.isEmpty(nationalIdUpdateRequest.getCoBuyerDetails().getCoBuyerValidDate())) {
            var laAutoLoanAcctResponseForCoBuyer = validateNationalIdDatesClient.retrieveEndorserDetails(nationalIdUpdateRequest.getReceivableAccountNumber());
            messageTxt = coBuyerUpdate(laAutoLoanAcctResponseForCoBuyer,nationalIdUpdateRequest);
        }

        switch (messageTxt){
            case ACCOUNT_NOT_FOUND:
                LOG.error(UPDATE_DATE_LOGGER+ACCOUNT_NOT_FOUND);
                throw new AccountNumerNotFoundException(ACCOUNT_NOT_FOUND, errorProperties.getAccountNumberNotFoundErrorCode());
            case CUSTOMER_RELATIONSHIP_NOT_FOUND:
                LOG.error(UPDATE_DATE_LOGGER+ErrorMessages.CUSTOMER_RELATIONSHIP_NOT_FOUND);
                throw new AccountNumerNotFoundException(ErrorMessages.CUSTOMER_RELATIONSHIP_NOT_FOUND, errorProperties.getCustomerRelationShipNotFound());
            case UPDATE_FUNCTION_SELECTED_BUT_NO_INPUT_ENTERED:
                LOG.error(UPDATE_DATE_LOGGER+ErrorMessages.DATES_ALREADY_EXITS_FOR_BUYER_COBUYER);
                throw new DatesAlreadyExistsException(ErrorMessages.DATES_ALREADY_EXITS_FOR_BUYER_COBUYER, errorProperties.getDatesAlreadyExists());
            case BUYER_SUCCESS:
                LOG.info(UPDATE_DATE_SUCCESS_LOGGER);
                return BUYER_SUCCESS;
            case COBUYER_SUCCESS:
                LOG.info(UPDATE_DATE_SUCCESS_LOGGER);
                return COBUYER_SUCCESS;
            case BUYER_COBUYER_SUCCESS:
                LOG.info(UPDATE_DATE_SUCCESS_LOGGER);
                return BUYER_COBUYER_SUCCESS;
            default:
                LOG.error(UPDATE_DATE_LOGGER+ErrorMessages.BUYER_COBUYER_FAILED);
                throw new NationalIdUpdateFailedException(ErrorMessages.BUYER_COBUYER_FAILED, errorProperties.getNationalIdUpdateFailed());
        }
    }

    private String buyerAndCoBuyerUpdate(LaAutoLoanAcctResponse laAutoLoanAcctResponseForBuyer,LAAutoLoanEndorserResponse laAutoLoanAcctResponseForCoBuyer,NationalIdUpdateRequest nationalIdUpdateRequest){
        LOG.info("buyerAndCoBuyerUpdate starts - ReceivableAccountNumber " + nationalIdUpdateRequest.getReceivableAccountNumber());
        var messageTxt ="";
        if(PROCESS_COMPLETE.equalsIgnoreCase(laAutoLoanAcctResponseForBuyer.getWiaractOperationResponse().getTSRsHdr().getProcessMessage().trim())){
             var buyerMessageTxt = updateBuyerCoBuyerAddrDetails(laAutoLoanAcctResponseForBuyer.getWiaractOperationResponse().getAutoLoanAcctRs().get(0).custId,nationalIdUpdateRequest.getReceivableAccountNumber(),nationalIdUpdateRequest.getBuyerDetails().getBuyerValidDate(),nationalIdUpdateRequest.getBuyerDetails().getBuyerExpDate(),BUYER);
            if(PROCESS_COMPLETE.equalsIgnoreCase(laAutoLoanAcctResponseForCoBuyer.getWIAREDROperationResponse().getTSRsHdr().getProcessMessage().trim())){
                var coBuyerMessageTxt = updateBuyerCoBuyerAddrDetails(String.valueOf(laAutoLoanAcctResponseForCoBuyer.getWIAREDROperationResponse().getAutoLoanEndorserRs().get(0).endorserList.get(0).custId),nationalIdUpdateRequest.getReceivableAccountNumber(),nationalIdUpdateRequest.getCoBuyerDetails().getCoBuyerValidDate(),nationalIdUpdateRequest.getCoBuyerDetails().getCoBuyerExpDate(),BUYER_COBUYER);
                messageTxt = buyerMessageTxt.equalsIgnoreCase(BUYER_SUCCESS) ? coBuyerMessageTxt : buyerMessageTxt;
            }else {
                messageTxt = laAutoLoanAcctResponseForCoBuyer.getWIAREDROperationResponse().getTSRsHdr().getTrnStatus().get(0).getMsgText().trim();
            }
        }else{
            messageTxt = laAutoLoanAcctResponseForBuyer.getWiaractOperationResponse().getTSRsHdr().getTrnStatus().get(0).getMsgText();
        }
        return messageTxt;
    }

    private String buyerUpdate(LaAutoLoanAcctResponse laAutoLoanAcctResponseForBuyer,NationalIdUpdateRequest nationalIdUpdateRequest){
        LOG.info("buyerUpdate starts - ReceivableAccountNumber " + nationalIdUpdateRequest.getReceivableAccountNumber());
        var messageTxt="";
        if(PROCESS_COMPLETE.equalsIgnoreCase(laAutoLoanAcctResponseForBuyer.getWiaractOperationResponse().getTSRsHdr().getProcessMessage().trim())){
            messageTxt =  updateBuyerCoBuyerAddrDetails(laAutoLoanAcctResponseForBuyer.getWiaractOperationResponse().getAutoLoanAcctRs().get(0).custId,nationalIdUpdateRequest.getReceivableAccountNumber(),nationalIdUpdateRequest.getBuyerDetails().getBuyerValidDate(),nationalIdUpdateRequest.getBuyerDetails().getBuyerExpDate(),"Buyer");
        }else{
            messageTxt = laAutoLoanAcctResponseForBuyer.getWiaractOperationResponse().getTSRsHdr().getTrnStatus().get(0).getMsgText();
        }
        return messageTxt;
    }

    private String coBuyerUpdate(LAAutoLoanEndorserResponse laAutoLoanAcctResponseForCoBuyer,NationalIdUpdateRequest nationalIdUpdateRequest){
        LOG.info("coBuyerUpdate starts - ReceivableAccountNumber " + nationalIdUpdateRequest.getReceivableAccountNumber());
        var messageTxt="";
        if(PROCESS_COMPLETE.equalsIgnoreCase(laAutoLoanAcctResponseForCoBuyer.getWIAREDROperationResponse().getTSRsHdr().getProcessMessage().trim())){
            messageTxt =updateBuyerCoBuyerAddrDetails(String.valueOf(laAutoLoanAcctResponseForCoBuyer.getWIAREDROperationResponse().getAutoLoanEndorserRs().get(0).endorserList.get(0).custId),nationalIdUpdateRequest.getReceivableAccountNumber(),nationalIdUpdateRequest.getCoBuyerDetails().getCoBuyerValidDate(),nationalIdUpdateRequest.getCoBuyerDetails().getCoBuyerExpDate(),"CoBuyer");
        }else {
            messageTxt = laAutoLoanAcctResponseForCoBuyer.getWIAREDROperationResponse().getTSRsHdr().getTrnStatus().get(0).getMsgText();
        }
        return messageTxt;
    }

    private String updateBuyerCoBuyerAddrDetails(String customerId, ReceivableAccountNumber receivableAccountNumber, String validDate, String expiryDate,String statusMessage) {
        LOG.info("updateBuyerCoBuyerAddrDetails starts - ReceivableAccountNumber " + receivableAccountNumber.getReceivableAccountNumber() + "For " +statusMessage);
        String messageTxt;
        var laAutoLoanCustNameAddrFormat2Response = validateNationalIdDatesClient.updateCustomerAddressDetails(customerId, receivableAccountNumber, validDate, expiryDate);
        if (LaConstants.PROCESS_COMPLETE.equalsIgnoreCase(laAutoLoanCustNameAddrFormat2Response.getWiarcn2OperationResponse().getTSRsHdr().getProcessMessage().trim())) {
            messageTxt = statusMessage;
            switch (messageTxt){
                case BUYER:
                    return BUYER_SUCCESS;
                case COBUYER:
                    return COBUYER_SUCCESS;
                default:
                    return BUYER_COBUYER_SUCCESS;
            }
        } else {
            messageTxt = laAutoLoanCustNameAddrFormat2Response.getWiarcn2OperationResponse().getTSRsHdr().getTrnStatus().get(0).getMsgText().trim();
        }
        return messageTxt;
    }
}

