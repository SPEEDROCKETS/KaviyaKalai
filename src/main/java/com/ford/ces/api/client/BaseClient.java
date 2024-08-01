package com.ford.ces.api.client;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ford.ces.api.config.ErrorProperties;
import com.ford.ces.api.domain.constants.LaConstants;
import com.ford.ces.api.domain.response.*;
import com.ford.ces.api.exception.AffiliateException;
import com.ford.ces.api.exception.ErrorMessages;
import com.ford.ces.api.exception.LaSystemException;
import com.ford.ces.api.exception.PinnacleServiceException;
import com.ford.ces.api.util.UtilProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

import static com.ford.ces.api.constant.CESConstant.URL_REQUEST_OBJ_LOG;
import static com.ford.ces.api.domain.constants.LaConstants.*;
import static com.ford.ces.api.exception.ErrorMessages.LA_UPDATED_SUCCESSFULLY_AND_PINNACLE_UPDATE_FAILED;

@Component
public abstract class BaseClient {


    private static final Logger LOG = LoggerFactory.getLogger(BaseClient.class);

    protected UtilProperties utilProperties;
    ErrorProperties errorProperties;
    WebClient webClient;

    @Autowired
    protected BaseClient(UtilProperties utilProperties, ErrorProperties errorProperties, WebClient webClient) {
        this.utilProperties = utilProperties;
        this.webClient = webClient;
        this.errorProperties = errorProperties;
    }

    public <T, R extends LAFinalResponse> R getSchedulePaymentsFromLa(T requestObject, String urlString, Class<R> responseClass) {
        LOG.info("BaseClient getDataFromLa starts");
        LOG.info(URL_REQUEST_OBJ_LOG,urlString,requestObject);
        var response = this.webClient
                .post()
                .uri(urlString)
                .headers(httpHeaders -> {
                    httpHeaders.setBasicAuth(utilProperties.getRacfId(), utilProperties.getRacfpwd());
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .bodyValue(requestObject)
                .retrieve();

        //Print raw LA rsponse
        response.bodyToMono(String.class)
                .subscribe(flatResponse -> LOG.info("Schedule Endpoint - Flat Response body: {}", flatResponse));

        //Actual flow continues
        var finalResponse = response.bodyToMono(responseClass)
                .doOnError(err -> {
                    LOG.error(WEBCLIENT_ERROR);
                    LOG.error("Error retrieving information from La. Request: {}, Error: {} ", requestObject, err.getMessage());
                    throw new LaSystemException(err.getMessage());
                }).block();
        LOG.info("BillingSchedule Response Object: {}", finalResponse);

        return finalResponse;
    }

    public <T, R extends LaAutoLoanAutoCollaterallResponse> R updateLicenseDetails(T requestObject, String urlString,
                                                                                   Class<R> responseClass) {
        LOG.info("BaseClient getDataFromLaForUpdateLicense starts");
        LOG.info(URL_REQUEST_OBJ_LOG,urlString,requestObject);
        var response = this.webClient
                .post()
                .uri(urlString)
                .headers(httpHeaders -> {
                    httpHeaders.add(CONTENT_TYPE, JSON_TYPE);
                    httpHeaders.setBasicAuth(utilProperties.getRacfId(), utilProperties.getRacfpwd());
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .bodyValue(requestObject)
                .retrieve()
                .bodyToMono(responseClass)
                .doOnError(err -> {
                    LOG.error(WEBCLIENT_ERROR);
                    LOG.error(ERROR_INFO, requestObject, err.getMessage());
                    throw new LaSystemException(err.getMessage());
                }).block();
        LOG.info("LicenseUpdate Response Object: {}", response);
        return response;

    }

    public <T, R extends PinnacleResponse> R updateDetailsToPinnacle(T requestObject, String urlString,
                                                                     HttpMethod httpMethod, Class<R> responseClass) {
        LOG.info("BaseClient updatePinacle starts");
        LOG.info(URL_REQUEST_OBJ_LOG,urlString,requestObject);
        var bearerToken = "";
        try {
            bearerToken = getAccessToken();
        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage());
            LOG.error("Error retrieving Authentication information from Pinnacle ADFS. Request: {}, Error: {} ", requestObject, e.getMessage());
            throw new PinnacleServiceException(LA_UPDATED_SUCCESSFULLY_AND_PINNACLE_UPDATE_FAILED, errorProperties.getPinnacleServiceFailed());
        }
        String finalBearerToken = BEARER_SPACE + bearerToken;
        R response = this.webClient
                .method(httpMethod)
                .uri(urlString)
                .header(HttpHeaders.AUTHORIZATION, finalBearerToken)
                .header(CONTENT_TYPE, JSON_TYPE)
                .bodyValue(requestObject)
                .retrieve()
                .bodyToMono(responseClass)
                .doOnError(err -> {
                    LOG.error(WEBCLIENT_ERROR);
                    LOG.error("Error retrieving information from Pinnacle. Request: {}, Error: {} ", requestObject, err.getMessage());
                    throw new PinnacleServiceException(LA_UPDATED_SUCCESSFULLY_AND_PINNACLE_UPDATE_FAILED, errorProperties.getPinnacleServiceFailed());
                }).block();
        LOG.info("Pinnacle Service Response Object: {}", response);
        return response;
    }


    public <T, R extends LaAutoLoanAffiliateFinalResponse> R enquireOrUpdateAffiliateNumber(T requestObject, String urlString,
                                                                                            Class<R> responseClass) {
        LOG.info("BaseClient enquireOrUpdateAffiliateNumber starts");
        LOG.info(URL_REQUEST_OBJ_LOG,urlString,requestObject);
        var response = this.webClient
                .post()
                .uri(urlString)
                .headers(httpHeaders -> {
                    httpHeaders.add(CONTENT_TYPE, JSON_TYPE);
                    httpHeaders.setBasicAuth(utilProperties.getRacfId(), utilProperties.getRacfpwd());
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .bodyValue(requestObject)
                .retrieve()
                .bodyToMono(responseClass)
                .doOnError(err -> {
                    LOG.error(WEBCLIENT_ERROR);
                    LOG.error(ERROR_INFO, requestObject, err.getMessage());
                    LOG.info("Affiliate response body is: {}", responseClass);
                    throw new AffiliateException(ErrorMessages.AFFILIATE_UPDATE_FAILED, ErrorMessages.AFFILIATE_UPDATE_FAILED_CODE);
                }).block();
        LOG.info("Affiliate Response Object: {}", response);
        return response;
    }


    public <T, R extends LAAutodebitResponse> R getAutoDebitDataFromLa(T requestObject, String urlString,
                                                                       Class<R> responseClass) {
        LOG.info("BaseClient getAutoDebitDataFromLa starts");
        LOG.info(URL_REQUEST_OBJ_LOG,urlString,requestObject);
        var response = this.webClient
                .post()
                .uri(urlString)
                .headers(httpHeaders -> {
                    httpHeaders.setBasicAuth(utilProperties.getRacfId(), utilProperties.getRacfpwd());
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .bodyValue(requestObject)
                .retrieve()
                .bodyToMono(responseClass)
                .doOnError(err -> {
                    LOG.error(WEBCLIENT_ERROR);
                    LOG.error(ERROR_INFO, requestObject, err.getMessage());
                    throw new AffiliateException(ErrorMessages.LA_AUTO_DEBIT_UPDATE_FAILED, ErrorMessages.AUTO_DEBIT_FLAG_UPDATE_FAILED_CODE);
                }).block();
        LOG.info("AutoDebit Response Object: {}", response);
        return response;
    }


    public <T, R extends LaAutoChangeProcessResponse> R updateAutoChangeProcessFlag(T requestObject, String urlString,
                                                                                    Class<R> responseClass) {
        LOG.info("BaseClient getAutoDebitDataFromLa starts");
        LOG.info(URL_REQUEST_OBJ_LOG,urlString,requestObject);
        var response = this.webClient
                .post()
                .uri(urlString)
                .headers(httpHeaders -> {
                    httpHeaders.setBasicAuth(utilProperties.getRacfId(), utilProperties.getRacfpwd());
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .bodyValue(requestObject)
                .retrieve()
                .bodyToMono(responseClass)
                .doOnError(err -> {
                    LOG.error(WEBCLIENT_ERROR);
                    LOG.error("Error retrieving information from LaAutoDebit. Request: {}, Error: {} ", requestObject, err.getMessage());
                    LOG.error(err.getMessage(), err);
                    throw new AffiliateException(ErrorMessages.LA_AUTO_DEBIT_UPDATE_FAILED, ErrorMessages.AUTO_DEBIT_FLAG_UPDATE_FAILED_CODE);
                }).block();
        LOG.info("Billingchange Process Response Object: {}", response);
        return response;

    }

    private String getAccessToken() throws JsonProcessingException {
        String adfsUrl = utilProperties.getPinnacleAdfsUrl();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", utilProperties.getPinnacleAdfsClientId());
        map.add("client_secret", utilProperties.getPinnacleAdfsSecret());
        map.add("grant_type", utilProperties.getPinnacleAdfsGrantType());
        map.add("resource", utilProperties.getPinnacleAdfsResource());
        return getToken(this.webClient
                .post()
                .uri(adfsUrl)
                .header(LaConstants.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .bodyValue(map)
                .retrieve()
                .bodyToMono(String.class).block());
    }

    private String getToken(String response) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        JsonNode map = mapper.readTree(response);
        return map.has(ACCESS_TOKEN) ? map.get(ACCESS_TOKEN).asText() : "";
    }

    public <T, R extends LaAutoLoanAcctResponse> R getDataFromLaForAutoLoanAcct(T requestObject, String urlString,
                                                                                Class<R> responseClass) {
        LOG.info("BaseClient getDataFromLaForAutoLoanAcct starts");
        LOG.info(URL_REQUEST_OBJ_LOG,urlString,requestObject);
        var response = this.webClient
                .post()
                .uri(urlString)
                .headers(httpHeaders -> {
                    httpHeaders.add(CONTENT_TYPE, JSON_TYPE);
                    httpHeaders.setBasicAuth(utilProperties.getRacfId(), utilProperties.getRacfpwd());
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .bodyValue(requestObject)
                .retrieve()
                .bodyToMono(responseClass)
                .doOnError(err -> {
                    LOG.error(WEBCLIENT_ERROR);
                    LOG.error(err.getMessage(), err);
                    throw new LaSystemException(err.getMessage());
                }).block();
        LOG.info("AutoLoanAccount Response Object: {}", response);
        return response;

    }

    public <T, R extends LAAutoLoanEndorserResponse> R getDataFromLaForAutoLoanEndorser(T requestObject, String urlString,
                                                                                        Class<R> endorserResponseClass) {

        LOG.info("BaseClient getDataFromLaForAutoLoanEndorser starts");
        LOG.info(URL_REQUEST_OBJ_LOG,urlString,requestObject);
        var response = this.webClient
                .post()
                .uri(urlString)
                .headers(httpHeaders -> {
                    httpHeaders.add(CONTENT_TYPE, JSON_TYPE);
                    httpHeaders.setBasicAuth(utilProperties.getRacfId(), utilProperties.getRacfpwd());
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .bodyValue(requestObject)
                .retrieve()
                .bodyToMono(endorserResponseClass)
                .doOnError(err -> {
                    LOG.error(WEBCLIENT_ERROR);
                    LOG.error(err.getMessage(), err);
                    throw new LaSystemException(err.getMessage());
                }).block();
        LOG.info("AutoLoan Endorser Response Object: {}", response);
        return response;
    }


    public <T, R extends LAAutoLoanCustNameAddrFormat2Response> R getDataFromLaForAutoLoanCustNameAddrFormat2(T requestObject, String urlString, Class<R> custResponseClass) {

        LOG.info("BaseClient getDataFromLaForAutoLoanCustNameAddrFormat2 starts");
        LOG.info(URL_REQUEST_OBJ_LOG,urlString,requestObject);
        var response = this.webClient
                .post()
                .uri(urlString)
                .headers(httpHeaders -> {
                    httpHeaders.add(CONTENT_TYPE, JSON_TYPE);
                    httpHeaders.setBasicAuth(utilProperties.getRacfId(), utilProperties.getRacfpwd());
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .bodyValue(requestObject)
                .retrieve()
                .bodyToMono(custResponseClass)
                .doOnError(err -> {
                    LOG.error(WEBCLIENT_ERROR);
                    LOG.error(err.getMessage(), err);
                    throw new LaSystemException(err.getMessage());
                }).block();
        LOG.info("AutoLoan CustAddress Response Object: {}", response);
        return response;
    }

    public <T, R> R getDataFromLA(T requestObject, String urlString, Class<R> responseObjectClass) {
        LOG.info("BaseClient LA Call  starts");
        LOG.info(URL_REQUEST_OBJ_LOG,urlString,requestObject);
        var response = this.webClient
                .post()
                .uri(urlString)
                .headers(httpHeaders -> {
                    httpHeaders.add(CONTENT_TYPE, JSON_TYPE);
                    httpHeaders.setBasicAuth(utilProperties.getRacfId(), utilProperties.getRacfpwd());
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .bodyValue(requestObject)
                .retrieve()
                .bodyToMono(responseObjectClass)
                .doOnError(err -> {
                    LOG.error(WEBCLIENT_ERROR);
                    LOG.error(err.getMessage(), err);
                    throw new LaSystemException(err.getMessage());
                }).block();
        LOG.info("Base Client LA Response Object: {}", response);
        return response;
    }


}
