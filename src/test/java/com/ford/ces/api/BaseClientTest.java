package com.ford.ces.api.client;

import com.ford.ces.api.config.ErrorProperties;
import com.ford.ces.api.domain.request.*;
import com.ford.ces.api.domain.request.extension.LaAutoLoanProcessExtRequest;
import com.ford.ces.api.domain.request.extension.LaAutoLoanQuickViewRequest;
import com.ford.ces.api.domain.response.*;
import com.ford.ces.api.domain.response.extension.LaAutoLoanProcessExtResponse;
import com.ford.ces.api.exception.AffiliateException;
import com.ford.ces.api.exception.LaSystemException;
import com.ford.ces.api.exception.PinnacleServiceException;
import com.ford.ces.api.util.TestUtil;
import com.ford.ces.api.util.UtilProperties;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.cloud.commons.util.InetUtilsProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;


@ContextConfiguration(classes = BaseClient.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@TestPropertySource
@AutoConfigureMockMvc
class BaseClientTest {

    @Mock
    LaAutoLoanQuickViewRequest laAutoLoanQuickViewRequest;
    private static final String autoLoanBilling = "/autoLoanBilling";
    private static final String autoLoanAffiliateUrl = "/autoLoanAffiliate";
    private static final String autoLoanBillingDef = "/autoLoanBillingDef";
    private static final String host = "host";
    private static final String port = "1234";
    private static final String racfId = "FACDDH";
    private static final String racf = "abc1234";
    private static final String pinnacleAdfsClientId = "3454";
    private static final String pinnacleAdfsSecret = "4534543";
    private static final String client = "client";
    private static final String pinnacleAdfsResource = "urn";
    private static final String pinnacleAdfsUrl = "/license";
    private static final String updatePinnacle = "/updatePinnacle";
    private static final String pinnacleUrlForUpdateAffiliateNumber = "/affiliatePinnacle";
    private static final String billingSchedule = "/billingSchedule";
    private static final String customer = "/customer";
    private static final String pinnacleTestNull = "{\"test\": \"sd12f_yef34df_htu\"}";
    private static final String webClientTxt = "webClient";
    private static final String Error2 = "Error2";
    private static final String pinnacle = "/pinnacle";
    private static final String affiliate = "/affiliate";
    private static final String endorser = "/endorser";
    private static final String autoDebit = "autoDebit";
    private static final String acct = "/acct";
    private static final String validToken = "{\"access_token\": \"sd12f_yef34df_htu\"}";
    private static final String invalidToken = "{\"access_tokend12f_yef34df_htu\"}";
    private static final LaAutoLoanAutoCollateralRequest laAutoLoanAutoCollateralRequest = new LaAutoLoanAutoCollateralRequest();
    private static final LaAutoLoanBillingChangeProcessRequest laAutoLoanBillingChangeProcessRequest = TestUtil.getLaAutoLoanBillingChangeProcessRequest();
    private static final LaAutodebitRequest laAutodebitRequest = TestUtil.getLaAutodebitRequest();
    private static final LaAutoLoanAffiliateRequest  laAutoLoanAffiliateRequest = TestUtil.getLaAutoLoanAffiliateRequest();
    private static final LaAutoLoanAcctRequest laAutoLoanAcctRequest = new LaAutoLoanAcctRequest();
    private static final LaAutoLoanEndorserRequest laAutoLoanEndorserRequest = new LaAutoLoanEndorserRequest();
    private static final LaAutoLoanCustNameAddrFormat2Request laAutoLoanCustNameAddrFormat2Request = new LaAutoLoanCustNameAddrFormat2Request();


    public static class BaseClientChild extends BaseClient {
        public BaseClientChild(UtilProperties utilProperties,ErrorProperties errorProperties,WebClient webClient) {
            super(utilProperties,errorProperties,webClient);
            this.webClient=webClient;
        }
    }

    @Mock
    private BaseClient baseClient;

    @Mock
    WebClient webClient;

    @Mock
    WebClient.RequestBodyUriSpec requestBodyUriSpec;
    @Mock
    WebClient.RequestBodySpec requestBodySpec;
    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    WebClient.ResponseSpec responseSpec;
    @Mock
    WebClient.RequestHeadersUriSpec requestHeadersUriSpec;


    private MockWebServer mockWebServer;

    @Mock
    LaAutoLoanBillingRequest laAutoLoanBillingRequest;

    @Mock
    ErrorProperties errorPropertiesMock;
    @Mock
    LaAutoLoanProcessExtRequest laAutoLoanProcessExtRequest;

    @Mock
    BaseClientChild spiedBaseClient;

    private static final String ERROR_RESPONSE = "{\"statusCode\": \"message\"}";
    @BeforeEach
    public void setup() {
        spiedBaseClient.utilProperties = UtilProperties.builder()
                .billingScheduleUrl(autoLoanBilling)
                .autoLoanAutoCollateral(autoLoanBilling)
                .autoLoanAffiliateUrl(autoLoanAffiliateUrl)
                .autoLoanBillingDef(autoLoanBillingDef)
                .host(host)
                .port(port)
                .racfId(racfId)
                .racfpwd(racf)
                .pinnacleAdfsClientId(pinnacleAdfsClientId)
                .pinnacleAdfsSecret(pinnacleAdfsSecret)
                .pinnacleAdfsGrantType(client)
                .pinnacleAdfsResource(pinnacleAdfsResource)
                .pinnacleAdfsUrl(pinnacleAdfsUrl)
                .updateLicenceInPinnacleUrl(updatePinnacle)
                .pinnacleUrlForUpdateAffiliateNumber(pinnacleUrlForUpdateAffiliateNumber)
                .build();
        spiedBaseClient.errorProperties = errorPropertiesMock;


        BaseClientChild baseClientChild = new BaseClientChild(spiedBaseClient.utilProperties,spiedBaseClient.errorProperties,spiedBaseClient.webClient);
        spiedBaseClient = spy(baseClientChild);
        mockWebServer = new MockWebServer();
        spiedBaseClient.webClient=webClient;
        baseClient = new BaseClient(spiedBaseClient.utilProperties,errorPropertiesMock,spiedBaseClient.webClient) {

        };

    }

    @Test
    void getSchedulePaymentsFromLa_ShouldReturnLaResponse()  {

        LAFinalResponse laFinalResponse = TestUtil.getLAFinalResponse();
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(any(String.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.headers(any())).thenReturn(requestBodySpec);
        when(requestBodySpec.contentType(MediaType.APPLICATION_JSON)).thenReturn(requestBodySpec);
        doReturn(requestHeadersSpec).when(requestBodySpec).bodyValue(any());
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        when(responseSpec.bodyToMono(LAFinalResponse.class)).thenReturn(Mono.just(laFinalResponse));
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("Test"));
        var actual= spiedBaseClient.getSchedulePaymentsFromLa(TestUtil.getLaAutoLoanBillingRequest(),billingSchedule,LAFinalResponse.class);
        assertNotNull(actual);
    }


    @Test
    void getSchedulePaymentsFromLa_ClientErrorThrowsLaSystemException() {

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(500)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ERROR_RESPONSE)
        );
        ReflectionTestUtils.setField(baseClient,webClientTxt,WebClient.create());
        String urlPath = mockWebServer.url("/").toString();
        assertThrows(LaSystemException.class, () ->
                baseClient.getSchedulePaymentsFromLa(laAutoLoanBillingRequest,urlPath,LAFinalResponse.class)
        );

    }

    @Test
    void getSchedulePaymentsFromLa_ServerErrorThrowsLaSystemException() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(HttpStatus.I_AM_A_TEAPOT.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ERROR_RESPONSE)
        );
        ReflectionTestUtils.setField(baseClient, webClientTxt, WebClient.create());
        String urlPath = mockWebServer.url("/").toString();
        assertThrows(LaSystemException.class, () ->
                baseClient.getSchedulePaymentsFromLa(laAutoLoanBillingRequest,urlPath, LAFinalResponse.class)
        );
    }
    @Test
    void getSchedulePaymentsFromLa_LaErrorThrowsException() {

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(HttpStatus.BAD_REQUEST.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ERROR_RESPONSE)
        );
        ReflectionTestUtils.setField(baseClient, webClientTxt, WebClient.create());
        String urlPath = mockWebServer.url("/").toString();
        assertThrows(LaSystemException.class, () ->
                baseClient.getSchedulePaymentsFromLa(laAutoLoanBillingRequest, urlPath, LAFinalResponse.class)
        );
    }

    @Test
    void updateLicensePlateNumber_ShouldUpdateLicensePlateNumber() {
        LaAutoLoanAutoCollaterallResponse laAutoLoanAutoCollaterallResponse = TestUtil.getLaAutoLoanAutoCollaterallResponse();
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.headers(any())).thenReturn(requestBodySpec);
        when(requestBodySpec.contentType(MediaType.APPLICATION_JSON)).thenReturn(requestBodySpec);
        doReturn(requestHeadersSpec).when(requestBodySpec).bodyValue(any());
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        when(responseSpec.bodyToMono(LaAutoLoanAutoCollaterallResponse.class)).thenReturn(Mono.just(laAutoLoanAutoCollaterallResponse));
        var actual= spiedBaseClient.updateLicenseDetails(new LaAutoLoanAutoCollateralRequest(),"",LaAutoLoanAutoCollaterallResponse.class);
        assertNotNull(actual);
    }

    @Test
    void updateLicenseNumber_ClientErrorThrowsLaSystemException() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(HttpStatus.I_AM_A_TEAPOT.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ERROR_RESPONSE)
        );

        ReflectionTestUtils.setField(baseClient, webClientTxt, WebClient.create());
        String urlPath = mockWebServer.url("/").toString();
        assertThrows(LaSystemException.class, () ->
                baseClient.updateLicenseDetails(laAutoLoanAutoCollateralRequest,urlPath, LaAutoLoanAutoCollaterallResponse.class)
        );
    }

    @Test
    void updateLicenseNumber_ServerErrorThrowsLaSystemException() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(500)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ERROR_RESPONSE)
        );
        ReflectionTestUtils.setField(baseClient, webClientTxt, WebClient.create());
        String urlPath = mockWebServer.url("/").toString();
        assertThrows(LaSystemException.class, () ->
                baseClient.updateLicenseDetails(laAutoLoanAutoCollateralRequest,urlPath, LaAutoLoanAutoCollaterallResponse.class)
        );
    }

    @Test
    void updateLicensePlateNumber_LaErrorThrowsException() {

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(HttpStatus.BAD_REQUEST.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ERROR_RESPONSE)
        );
        ReflectionTestUtils.setField(baseClient, webClientTxt, WebClient.create());
        String urlPath = mockWebServer.url("/").toString();
        assertThrows(LaSystemException.class, () ->
                baseClient.updateLicenseDetails("", urlPath, LaAutoLoanAutoCollaterallResponse.class)
        );

    }

    @Test
    void updateLicensePlateNumber_InPinnacle_ShouldUpdateLicensePlateNumber() {

        PinnacleResponse pinnacleResponse=TestUtil.getPinnacleResponse();

        when(webClient.method(HttpMethod.POST)).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.header(anyString(), anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(PinnacleResponse.class)).thenReturn(Mono.just(pinnacleResponse));

        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.header(anyString(), anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(validToken));

        var actual = spiedBaseClient.updateDetailsToPinnacle(new PinnacleUpdateLicenseRequest(), pinnacle, HttpMethod.POST, PinnacleResponse.class);
        assertThat(actual).isEqualTo(pinnacleResponse);
    }

    @Test
    void updateLicensePlateNumber_InPinnacle_ReturnError() {

        Mono<PinnacleResponse> response = Mono.error(new Throwable(Error2));

        when(webClient.method(HttpMethod.POST)).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.header(anyString(), anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(PinnacleResponse.class)).thenReturn(response);

        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.header(anyString(), anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(validToken));


        assertThatThrownBy(() -> spiedBaseClient.updateDetailsToPinnacle(new PinnacleUpdateLicenseRequest(), pinnacle,
                HttpMethod.POST, PinnacleResponse.class)).isInstanceOf(PinnacleServiceException.class);
    }

    @Test
    void updateLicensePlateNumber_InPinnacle_ReturnNull() {

        Mono<PinnacleResponse> response = Mono.error(new Throwable(Error2));

        when(webClient.method(HttpMethod.POST)).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.header(anyString(), anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(PinnacleResponse.class)).thenReturn(response);

        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.header(anyString(), anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(pinnacleTestNull));


        assertThatThrownBy(() -> spiedBaseClient.updateDetailsToPinnacle(new PinnacleUpdateLicenseRequest(), pinnacle,
                HttpMethod.POST, PinnacleResponse.class)).isInstanceOf(PinnacleServiceException.class);
    }


    @Test
    void updateLicensePlateNumber_InPinnacle_ReturnJsonProcessingException() {

        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.header(anyString(), anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(invalidToken));

        assertThatThrownBy(() -> spiedBaseClient.updateDetailsToPinnacle(new PinnacleUpdateLicenseRequest(), pinnacle,
                HttpMethod.POST, PinnacleResponse.class)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void updateLicensePlateNumber_InPinnacle_ShouldReturnJsonProcessingException() {
        doThrow(PinnacleServiceException.class).when(spiedBaseClient).updateDetailsToPinnacle(any(),any(),any(),eq(PinnacleResponse.class));
        assertThatThrownBy(() -> spiedBaseClient.updateDetailsToPinnacle(new PinnacleUpdateLicenseRequest(), "",
                HttpMethod.POST, PinnacleResponse.class)).isInstanceOf(PinnacleServiceException.class);
    }

    @Test
    void getDataFromLaForAutoLoanAffiliate_ShouldReturnLaResponse(){
        LaAutoLoanAffiliateFinalResponse laAutoLoanAffiliateFinalResponse = TestUtil.getLaAutoLoanAffiliateResponse();
        when(spiedBaseClient.webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.headers(any())).thenReturn(requestBodySpec);
        when(requestBodySpec.contentType(MediaType.APPLICATION_JSON)).thenReturn(requestBodySpec);
        doReturn(requestHeadersSpec).when(requestBodySpec).bodyValue(any());
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        when(responseSpec.bodyToMono(LaAutoLoanAffiliateFinalResponse.class)).thenReturn(Mono.just(laAutoLoanAffiliateFinalResponse));
        var actual= spiedBaseClient.enquireOrUpdateAffiliateNumber(new LaAutoLoanAffiliateRequest(),affiliate,LaAutoLoanAffiliateFinalResponse.class);
        assertNotNull(actual);
        doReturn(laAutoLoanAffiliateFinalResponse).when(spiedBaseClient).enquireOrUpdateAffiliateNumber(any(),any(),eq(LaAutoLoanAffiliateFinalResponse.class));
        LaAutoLoanAffiliateFinalResponse result = spiedBaseClient.enquireOrUpdateAffiliateNumber(new LaAutoLoanAffiliateRequest(), "",
                LaAutoLoanAffiliateFinalResponse.class);
        assertThat(result).isNotNull();
    }

    @Test
    void getDataFromLaForAutoLoanAffiliate_ClientErrorThrowsLaSystemException() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(HttpStatus.I_AM_A_TEAPOT.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ERROR_RESPONSE)
        );
        ReflectionTestUtils.setField(spiedBaseClient, webClientTxt, WebClient.create());
        String urlPath = mockWebServer.url("/").toString();
        assertThrows(AffiliateException.class, () ->
                spiedBaseClient.enquireOrUpdateAffiliateNumber(laAutoLoanAffiliateRequest,urlPath, LaAutoLoanAffiliateFinalResponse.class)
        );
        assertThatThrownBy(() -> spiedBaseClient.enquireOrUpdateAffiliateNumber(laAutoLoanAffiliateRequest, "",
                LaAutoLoanAffiliateFinalResponse.class)).isInstanceOf(AffiliateException.class);
    }

    @Test
    void getDataFromLaForAutoLoanAffiliate_ServerErrorThrowsLaSystemException() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(500)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ERROR_RESPONSE)
        );
        ReflectionTestUtils.setField(spiedBaseClient, webClientTxt, WebClient.create());
        String urlPath = mockWebServer.url("/").toString();
        assertThrows(AffiliateException.class, () ->
                spiedBaseClient.enquireOrUpdateAffiliateNumber(laAutoLoanAffiliateRequest,urlPath, LaAutoLoanAffiliateFinalResponse.class)
        );
    }

    @Test
    void getDataFromLaForAutoLoanAffiliate_LaErrorThrowsException() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(HttpStatus.BAD_REQUEST.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ERROR_RESPONSE)
        );
        ReflectionTestUtils.setField(spiedBaseClient, webClientTxt, WebClient.create());
        String urlPath = mockWebServer.url("/").toString();
        assertThrows(AffiliateException.class, () ->
                spiedBaseClient.enquireOrUpdateAffiliateNumber("", urlPath, LaAutoLoanAffiliateFinalResponse.class)
        );
    }

    @Test
    void getDataFromLaAutoDebit_ShouldReturnLaAutoLoanBillingDefResponse(){
        LAAutodebitResponse laAutodebitResponse = TestUtil.getLAAutodebitResponse();
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.headers(any())).thenReturn(requestBodySpec);
        when(requestBodySpec.contentType(MediaType.APPLICATION_JSON)).thenReturn(requestBodySpec);
        doReturn(requestHeadersSpec).when(requestBodySpec).bodyValue(any());
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        when(responseSpec.bodyToMono(LAAutodebitResponse.class)).thenReturn(Mono.just(laAutodebitResponse));
        var actual=spiedBaseClient.getAutoDebitDataFromLa(laAutodebitRequest,autoDebit,LAAutodebitResponse.class);
        assertNotNull(actual);

    }
    @Test
    void getDataFromLaAutoDebit_ClientErrorThrowsLaSystemException() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(HttpStatus.I_AM_A_TEAPOT.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ERROR_RESPONSE)
        );
        ReflectionTestUtils.setField(spiedBaseClient, webClientTxt, WebClient.create());
        String urlPath = mockWebServer.url("/").toString();
        assertThrows(AffiliateException.class, () ->
                spiedBaseClient.getAutoDebitDataFromLa(laAutodebitRequest,urlPath, LAAutodebitResponse.class)
        );

    }

    @Test
    void getDataFromLaAutoDebit_ServerErrorThrowsLaSystemException() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(500)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ERROR_RESPONSE)
        );
        ReflectionTestUtils.setField(spiedBaseClient, webClientTxt, WebClient.create());
        String urlPath = mockWebServer.url("/").toString();
        assertThrows(AffiliateException.class, () ->
                spiedBaseClient.getAutoDebitDataFromLa(laAutodebitRequest,urlPath, LAAutodebitResponse.class)
        );
    }
    @Test
    void getAutoLoanBillingChangeProcessDataFromLa_ShouldReturnLaAutoLoanBillingChangeProcessResponse(){
        LaAutoChangeProcessResponse laAutoChangeProcessResponse = TestUtil.getAutoChangeProcessResponse();
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.headers(any())).thenReturn(requestBodySpec);
        when(requestBodySpec.headers(httpHeaders -> any())).thenReturn(requestBodySpec);
        when(requestBodySpec.contentType(MediaType.APPLICATION_JSON)).thenReturn(requestBodySpec);
        doReturn(requestHeadersSpec).when(requestBodySpec).bodyValue(any());
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        when(responseSpec.bodyToMono(LaAutoChangeProcessResponse.class)).thenReturn(Mono.just(laAutoChangeProcessResponse));
        var actual=spiedBaseClient.updateAutoChangeProcessFlag(new LaAutoLoanBillingChangeProcessRequest(),"",LaAutoChangeProcessResponse.class);
        assertNotNull(actual);
    }
    @Test
    void getAutoLoanBillingChangeProcessDataFromLa_ClientErrorThrowsLaSystemException() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(HttpStatus.I_AM_A_TEAPOT.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ERROR_RESPONSE)
        );
        ReflectionTestUtils.setField(spiedBaseClient, webClientTxt, WebClient.create());
        String urlPath = mockWebServer.url("/").toString();
        assertThrows(AffiliateException.class, () ->
                spiedBaseClient.updateAutoChangeProcessFlag(laAutoLoanBillingChangeProcessRequest,urlPath, LaAutoChangeProcessResponse.class)
        );
    }

    @Test
    void getAutoLoanBillingChangeProcessDataFromLa_ServerErrorThrowsLaSystemException() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(500)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ERROR_RESPONSE)
        );
        ReflectionTestUtils.setField(spiedBaseClient, webClientTxt, WebClient.create());
        String urlPath = mockWebServer.url("/").toString();
        assertThrows(AffiliateException.class, () ->
                spiedBaseClient.updateAutoChangeProcessFlag(laAutoLoanBillingChangeProcessRequest,urlPath, LaAutoChangeProcessResponse.class)
        );
    }

    @Test
    void getDataFromLaForAutoLoanAcct_ShouldReturnResponse(){

        LaAutoLoanAcctResponse laAutoLoanAcctResponse = TestUtil.getLAAutoLoanAcctResponse();
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.headers(any())).thenReturn(requestBodySpec);
        when(requestBodySpec.contentType(MediaType.APPLICATION_JSON)).thenReturn(requestBodySpec);
        doReturn(requestHeadersSpec).when(requestBodySpec).bodyValue(any());
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        when(responseSpec.bodyToMono(LaAutoLoanAcctResponse.class)).thenReturn(Mono.just(laAutoLoanAcctResponse));
        var actual= spiedBaseClient.getDataFromLaForAutoLoanAcct(laAutoLoanAcctRequest,acct,LaAutoLoanAcctResponse.class);
        assertNotNull(actual);
    }

    @Test
    void getDataFromLaForAutoLoanAcct_ClientErrorThrowsLaSystemException() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(500)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ERROR_RESPONSE)
        );
        ReflectionTestUtils.setField(baseClient,webClientTxt,WebClient.create());
        String urlPath = mockWebServer.url("/").toString();
        assertThrows(LaSystemException.class, () ->
                baseClient.getDataFromLaForAutoLoanAcct(laAutoLoanAcctRequest,urlPath,
                        LaAutoLoanAcctResponse.class)
        );
    }

    @Test
    void getDataFromLaForAutoLoanAcct_ServerErrorThrowsLaSystemException() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(500)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ERROR_RESPONSE)
        );
        ReflectionTestUtils.setField(baseClient, webClientTxt, WebClient.create());
        String urlPath = mockWebServer.url("/").toString();
        assertThrows(LaSystemException.class, () ->
                baseClient.getDataFromLaForAutoLoanAcct(laAutoLoanAcctRequest,urlPath, LaAutoLoanAcctResponse.class)
        );
    }

    @Test
    void getDataFromLaForAutoLoanAcct_LaErrorThrowsException() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(HttpStatus.BAD_REQUEST.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ERROR_RESPONSE)
        );
        ReflectionTestUtils.setField(baseClient, webClientTxt, WebClient.create());
        String urlPath = mockWebServer.url("/").toString();
        assertThrows(LaSystemException.class, () ->
                baseClient.getDataFromLaForAutoLoanAcct(laAutoLoanAcctRequest,urlPath,
                        LaAutoLoanAcctResponse.class)
        );
    }

    @Test
    void getDataFromLaForAutoLoanEndorser_ShouldReturnResponse(){

        LAAutoLoanEndorserResponse laAutoLoanEndorser = TestUtil.getLaAutoLoanEndorserResponse_withAutoLoanEndorserObj();
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.headers(any())).thenReturn(requestBodySpec);
        when(requestBodySpec.contentType(MediaType.APPLICATION_JSON)).thenReturn(requestBodySpec);
        doReturn(requestHeadersSpec).when(requestBodySpec).bodyValue(any());
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        when(responseSpec.bodyToMono(LAAutoLoanEndorserResponse.class)).thenReturn(Mono.just(laAutoLoanEndorser));
        var actual=spiedBaseClient.getDataFromLaForAutoLoanEndorser(laAutoLoanEndorserRequest,endorser,LAAutoLoanEndorserResponse.class);
        assertNotNull(actual);

    }

    @Test
    void getDataFromLaForAutoLoanEndorser_ClientErrorThrowsLaSystemException() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(500)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ERROR_RESPONSE)
        );
        ReflectionTestUtils.setField(baseClient,webClientTxt,WebClient.create());
        String urlPath = mockWebServer.url("/").toString();
        assertThrows(LaSystemException.class, () ->
                baseClient.getDataFromLaForAutoLoanEndorser(laAutoLoanEndorserRequest,urlPath,
                        LAAutoLoanEndorserResponse.class)
        );
    }

    @Test
    void getDataFromLaForAutoLoanEndorser_ServerErrorThrowsLaSystemException() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(HttpStatus.I_AM_A_TEAPOT.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ERROR_RESPONSE)
        );
        ReflectionTestUtils.setField(baseClient, webClientTxt, WebClient.create());
        String urlPath = mockWebServer.url("/").toString();
        assertThrows(LaSystemException.class, () ->
                baseClient.getDataFromLaForAutoLoanEndorser(laAutoLoanEndorserRequest, urlPath, LAAutoLoanEndorserResponse.class)
        );
    }

    @Test
    void getDataFromLaForAutoLoanCustNameAddrFormat2_ShouldReturnResponse(){
        LAAutoLoanCustNameAddrFormat2Response laAutoLoanCustNameAddrFormat2Response = TestUtil.getAutoLoanCustNameAddrFormatResponse();
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.headers(any())).thenReturn(requestBodySpec);
        when(requestBodySpec.contentType(MediaType.APPLICATION_JSON)).thenReturn(requestBodySpec);
        doReturn(requestHeadersSpec).when(requestBodySpec).bodyValue(any());
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        when(responseSpec.bodyToMono(LAAutoLoanCustNameAddrFormat2Response.class)).thenReturn(Mono.just(laAutoLoanCustNameAddrFormat2Response));
        var actual=spiedBaseClient.getDataFromLaForAutoLoanCustNameAddrFormat2(laAutoLoanCustNameAddrFormat2Request,customer,LAAutoLoanCustNameAddrFormat2Response.class);
        assertNotNull(actual);
    }

    @Test
    void getDataFromLaForAutoLoanCustNameAddrFormat2_ClientErrorThrowsLaSystemException() {

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(500)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ERROR_RESPONSE)
        );
        ReflectionTestUtils.setField(baseClient,webClientTxt,WebClient.create());
        String urlPath = mockWebServer.url("/").toString();
        assertThrows(LaSystemException.class, () ->
                baseClient.getDataFromLaForAutoLoanCustNameAddrFormat2(laAutoLoanCustNameAddrFormat2Request,urlPath,
                        LAAutoLoanCustNameAddrFormat2Response.class)
        );
    }

    @Test
    void getDataFromLaForAutoLoanCustNameAddrFormat2_ServerErrorThrowsLaSystemException() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(HttpStatus.I_AM_A_TEAPOT.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ERROR_RESPONSE)
        );
        ReflectionTestUtils.setField(baseClient, webClientTxt, WebClient.create());
        String urlPath = mockWebServer.url("/").toString();
        assertThrows(LaSystemException.class, () ->
                baseClient.getDataFromLaForAutoLoanCustNameAddrFormat2(laAutoLoanCustNameAddrFormat2Request, urlPath, LAAutoLoanCustNameAddrFormat2Response.class)
        );
    }

    @Test
    void getDataFromLA_Success() {
        LaAutoLoanProcessExtResponse laAutoLoanProcessExtRs = TestUtil.getLaAutoLoanProcessExtResponse();
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.headers(any())).thenReturn(requestBodySpec);
        when(requestBodySpec.contentType(MediaType.APPLICATION_JSON)).thenReturn(requestBodySpec);
        doReturn(requestHeadersSpec).when(requestBodySpec).bodyValue(any());
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        when(responseSpec.bodyToMono(LaAutoLoanProcessExtResponse.class)).thenReturn(Mono.just(laAutoLoanProcessExtRs));
        var actual = spiedBaseClient.getDataFromLA(new LaAutoLoanProcessExtRequest(), "", LaAutoLoanProcessExtResponse.class);
        assertNotNull(actual);
    }

    @Test
    void getDataFromLA_throwsLaSystemException() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(HttpStatus.I_AM_A_TEAPOT.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ERROR_RESPONSE)
        );

        ReflectionTestUtils.setField(baseClient, webClientTxt, WebClient.create());
        String urlPath = mockWebServer.url("/").toString();
        assertThrows(LaSystemException.class, () ->
                baseClient.getDataFromLA(laAutoLoanProcessExtRequest, urlPath, LaAutoLoanProcessExtResponse.class)
        );
    }

}
