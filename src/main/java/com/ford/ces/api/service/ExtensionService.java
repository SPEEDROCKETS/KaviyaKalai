package com.ford.ces.api.service;

import com.ford.ces.api.client.ExtensionClient;
import com.ford.ces.api.config.ErrorProperties;
import com.ford.ces.api.domain.request.ExtensionRequest;
import com.ford.ces.api.domain.request.ProcessExtensionRequest;
import com.ford.ces.api.domain.request.ReceivableAccountNumber;
import com.ford.ces.api.domain.response.extension.ExtensionDetailsResponse;
import com.ford.ces.api.domain.response.extension.ExtensionFeeResponse;
import com.ford.ces.api.domain.response.extension.ProcessExtensionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtensionService {

    ErrorProperties errorProperties;
    private final ExtensionClient extensionClient;
    private static final Logger LOG = LoggerFactory.getLogger(ExtensionService.class.getName());

    @Autowired
    public ExtensionService(ErrorProperties errorProperties , ExtensionClient extensionClient)
    {
        this.extensionClient = extensionClient;
        this.errorProperties = errorProperties;
    }

    public ExtensionDetailsResponse getExtensionDetails(ReceivableAccountNumber receivableAccountNumber){

        LOG.info("Retrieve extension service started for account number {}",receivableAccountNumber);
        return extensionClient.getExtensionDetailsClient(receivableAccountNumber);
    }

    public ProcessExtensionResponse processExtension(ProcessExtensionRequest extensionRequest) {
        LOG.info("Process extension service started for account number {}", extensionRequest.getReceivableAccountNumber());
        return extensionClient.processExtensionClient(extensionRequest);
    }

    public ExtensionFeeResponse getExtensionFee(ExtensionRequest extensionRequest) {
        LOG.info("Extension fee service started for account number {}", extensionRequest.getReceivableAccountNumber());
        return extensionClient.extensionFeeClient(extensionRequest);
    }

}
