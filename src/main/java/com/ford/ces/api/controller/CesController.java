package com.ford.ces.api.controller;

import com.ford.ces.api.config.SwaggerConfig.SystemWideResponses;
import com.ford.ces.api.domain.request.*;
import com.ford.ces.api.domain.response.extension.ExtensionDetailsResponse;
import com.ford.ces.api.domain.response.extension.ExtensionFeeResponse;
import com.ford.ces.api.domain.response.LAFinalResponse;
import com.ford.ces.api.domain.response.LaAutoLoanAutoCollaterallResponse;
import com.ford.ces.api.domain.response.NationalIdDatesResponse;
import com.ford.ces.api.domain.response.extension.ProcessExtensionResponse;
import com.ford.ces.api.service.AffiliateNumberUpdateService;
import com.ford.ces.api.service.BillingScheduleService;
import com.ford.ces.api.service.ExtensionService;
import com.ford.ces.api.service.ValidateNationalIdDatesService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/fafcces/v1/ces")
public class CesController {

    private static final Logger LOG = LoggerFactory.getLogger(CesController.class.getName());

    private final BillingScheduleService billingScheduleService;
    private final AffiliateNumberUpdateService affiliateNumberUpdateService;
    private final ValidateNationalIdDatesService validateNationalIdDatesService;
    private final ExtensionService extensionService;


    @Autowired
    public CesController(BillingScheduleService billingScheduleService, AffiliateNumberUpdateService affiliateNumberUpdateService, ValidateNationalIdDatesService validateNationalIdDatesService
            , ExtensionService extensionService) {
        this.billingScheduleService = billingScheduleService;
        this.affiliateNumberUpdateService = affiliateNumberUpdateService;
        this.validateNationalIdDatesService = validateNationalIdDatesService;
        this.extensionService = extensionService;
    }

    @PostMapping(value = "/schedule", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get billing schedule details", notes = "Returns scheduled billing details")
    @SystemWideResponses
    public ResponseEntity<LAFinalResponse> getBillingSchedulePayments(@Valid @RequestBody ReceivableAccountNumber receivableAccountNumber) {
        LOG.info("BillingScheduleController getBillingSchedulePayments starts - ReceivableAccountNumber " + receivableAccountNumber.getReceivableAccountNumber());
        return ResponseEntity.ok(billingScheduleService.getBillingSchedulePayments(receivableAccountNumber));
    }

    @PostMapping(value = "/updateLicenseNumber", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "To update the licence number", notes = "Allows to update the vehicle licence plate number")
    @SystemWideResponses
    public ResponseEntity<LaAutoLoanAutoCollaterallResponse> updateLicensePlateNumber(@Valid @RequestBody LicenseNumber licenseNumber) {
        LOG.info("BillingScheduleController updateLicensePlateNumber starts -" + licenseNumber.getReceivableAccountNumber());
        return ResponseEntity.ok(billingScheduleService.updateLicensePlateNumber(licenseNumber));
    }

    @PostMapping(value = "/updateAffiliateNumber", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "To update Affiliate number", notes = "Update Affiliate Details")
    @SystemWideResponses
    public ResponseEntity<String> updateAffiliateNumber(@Valid @RequestBody UpdateAffiliateDetailsRequest updateAffiliateDetailsRequest) {
        LOG.info("BillingScheduleController updateAffiliateBankAccountNumber starts -" + updateAffiliateDetailsRequest.getReceivableAccountNumber());
        return ResponseEntity.ok(affiliateNumberUpdateService.updateAffiliateNumber(updateAffiliateDetailsRequest));
    }

    @PostMapping(value = "/retrieveDate", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get the National ID Dates details", notes = "Returns National ID Dates details")
    @SystemWideResponses
    public ResponseEntity<NationalIdDatesResponse> retrieveDate(@Valid @RequestBody ReceivableAccountNumber receivableAccountNumber) {
        LOG.info("retrieveDate starts - ReceivableAccountNumber " + receivableAccountNumber.getReceivableAccountNumber());
        return ResponseEntity.ok(validateNationalIdDatesService.validateNationalIdDates(receivableAccountNumber));
    }

    @PostMapping(value = "/updateDate", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "update National ID Date details", notes = "Returns updateDate details")
    @SystemWideResponses
    public ResponseEntity<String> updateDate(@Valid @RequestBody NationalIdUpdateRequest nationalIdUpdateRequest) {
        LOG.info("updateDate starts - ReceivableAccountNumber " + nationalIdUpdateRequest.getReceivableAccountNumber());
        return ResponseEntity.ok(validateNationalIdDatesService.updateNationalIdDates(nationalIdUpdateRequest));
    }

    @ResponseBody
    @PostMapping(value = "/extension/get-eligibility-details", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get extension details", notes = "Returns extension details")
    @SystemWideResponses
    public ResponseEntity<ExtensionDetailsResponse> getExtensionEligibilityDetails(@Valid @RequestBody ReceivableAccountNumber receivableAccountNumber) {
        LOG.info("Extension Details starts - ReceivableAccountNumber " + receivableAccountNumber.getReceivableAccountNumber());
        return ResponseEntity.ok(extensionService.getExtensionDetails(receivableAccountNumber));
    }

    @ResponseBody
    @PostMapping(value = "/process-extension", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Process the Extension Request", notes = "Returns message")
    @SystemWideResponses
    public ResponseEntity<ProcessExtensionResponse> processExtension(@Valid @RequestBody ProcessExtensionRequest extensionRequest) {
        return ResponseEntity.ok(extensionService.processExtension(extensionRequest));
    }

    @ResponseBody
    @PostMapping(value = "/get-extension-fees", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get extension fee details", notes = "Returns extension fee details")
    @SystemWideResponses
    public ResponseEntity<ExtensionFeeResponse> retrieveExtensionFeeDetail(@Valid @RequestBody ExtensionRequest extensionRequest) {
        LOG.info("Extension fee started - ReceivableAccountNumber {} for {} months " , extensionRequest.getReceivableAccountNumber(), extensionRequest.getExtensionReq().getNumberOfMonths());
        return ResponseEntity.ok(extensionService.getExtensionFee(extensionRequest));
    }
}
