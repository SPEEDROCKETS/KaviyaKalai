package com.ford.ces.api.service;

import com.ford.ces.api.client.AffiliateNumberUpdateClient;
import com.ford.ces.api.config.ErrorProperties;
import com.ford.ces.api.domain.request.UpdateAffiliateDetailsRequest;
import com.ford.ces.api.exception.*;
import com.ford.ces.api.util.AffiliateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.ford.ces.api.domain.constants.LaConstants.*;
import static com.ford.ces.api.exception.ErrorMessages.ACCOUNT_NOT_FOUND;
import static com.ford.ces.api.exception.ErrorMessages.LA_UPDATED_SUCCESSFULLY_AND_PINNACLE_UPDATE_FAILED;
import static com.ford.ces.api.exception.ErrorMessages.UPDATE_AFFILIATE_LOGGER;

@Service
public class AffiliateNumberUpdateService {

    private static final Logger LOG = LoggerFactory.getLogger(AffiliateNumberUpdateService.class.getName());

    private final AffiliateNumberUpdateClient affiliateNumberUpdateClient;

    private ErrorProperties errorProperties;

    AffiliateUtil affiliateUtil;

    @Autowired
    public AffiliateNumberUpdateService(AffiliateNumberUpdateClient affiliateNumberUpdateClient, ErrorProperties errorProperties, AffiliateUtil affiliateUtil) {
        this.affiliateNumberUpdateClient = affiliateNumberUpdateClient;
        this.errorProperties = errorProperties;
        this.affiliateUtil= affiliateUtil;
    }
    public String updateAffiliateNumber(UpdateAffiliateDetailsRequest updateAffiliateDetailsRequest) {
        LOG.info("AffiliateNumberUpdateService updateAffiliateNumber starts - "+updateAffiliateDetailsRequest.getReceivableAccountNumber());
        List<String> bankCode = Arrays.asList(affiliateUtil.getListOfBankCodes()).stream()
                .filter(x-> x.contains(updateAffiliateDetailsRequest.getBankCode()))
                .collect(Collectors.toList());
        if (bankCode.isEmpty()){
            LOG.error("UpdateAffiliateNumber -"+ErrorMessages.INVALID_BANK_CODE);
            throw new InvalidBankCodeException(ErrorMessages.INVALID_BANK_CODE, errorProperties.getInvalidBankCode());
        } else {
            //LA UPDATE
            String messageTxt= affiliateNumberUpdateClient.updateAffiliateNumberInLA(updateAffiliateDetailsRequest);
            //PINNACLE UPDATE
            var autoDebitValueForPinnacle = "1";
            if (messageTxt.contains(SEQUENCE_UPDATED_SUCCESSFULLY)) {
                if(messageTxt.contains(AUTO_DEBIT_FLAG_IS_SUSPENDED)){
                    autoDebitValueForPinnacle = "2";
                }
                messageTxt = affiliateNumberUpdateClient.updateAffiliateNumberInPinnacle(updateAffiliateDetailsRequest, autoDebitValueForPinnacle, SEQUENCE_UPDATED_SUCCESSFULLY);
            } else if (messageTxt.contains(AFFILIATE_ALREADY_EXIST)) {
                if (messageTxt.contains(AUTO_DEBIT_FLAG_UPDATED_TO_ACTIVE)) {
                    messageTxt = affiliateNumberUpdateClient.updateAffiliateNumberInPinnacle(updateAffiliateDetailsRequest, autoDebitValueForPinnacle, AFFILIATE_ALREADY_EXIST);
                } else {
                    messageTxt = AFFILIATE_ALREADY_EXIST;
                }
            }

            switch (messageTxt){
                case AUTO_DEBIT_UPDATE_FAILED:
                    LOG.error(UPDATE_AFFILIATE_LOGGER+ErrorMessages.LA_AUTO_DEBIT_UPDATE_FAILED);
                    throw new LaAutodebitFlagUpdateFailedException(ErrorMessages.LA_AUTO_DEBIT_UPDATE_FAILED,errorProperties.getLaAutodebitUpdateFailed());
                case AFFILIATE_SEQUENCE_UPDATE_FAILED:
                    LOG.error(UPDATE_AFFILIATE_LOGGER+ErrorMessages.LA_SEQUENCE_UPDATE_FAILED);
                    throw new LaSequenceUpdateFailedException(ErrorMessages.LA_SEQUENCE_UPDATE_FAILED,errorProperties.getLaSequenceUpdateFailed());
                case AFFILIATE_ALREADY_EXIST:
                    LOG.error(UPDATE_AFFILIATE_LOGGER+ErrorMessages.AFFILIATE_INFORMATION_EXIST);
                    throw new AffiliateAlreadyExistException(ErrorMessages.AFFILIATE_INFORMATION_EXIST,errorProperties.getAffiliateInformationExists());
                case ACCOUNT_NOT_FOUND:
                case NO_DATA_FOUND_FOR_THE_GIVEN_ACCOUNT_NUMBER:
                    LOG.error(UPDATE_AFFILIATE_LOGGER+ErrorMessages.ACCOUNT_NOT_FOUND);
                    throw new AccountNumerNotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND,errorProperties.getAccountNumberNotFoundErrorCode());
                case LA_UPDATED_SUCCESSFULLY_AND_PINNACLE_UPDATE_FAILED:
                    LOG.error(UPDATE_AFFILIATE_LOGGER+ErrorMessages.LA_UPDATED_SUCCESSFULLY_AND_PINNACLE_UPDATE_FAILED);
                    throw new PinnacleUpdateFailedException(LA_UPDATED_SUCCESSFULLY_AND_PINNACLE_UPDATE_FAILED, errorProperties.getPinnacleUpdateFailed());
                default:
                    LOG.info("UpdateAffiliateNumber completed successfully");
                    return SEQUENCE_UPDATED_SUCCESSFULLY;
            }
        }
    }
 }
