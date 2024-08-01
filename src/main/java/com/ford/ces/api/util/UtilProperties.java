package com.ford.ces.api.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EnableConfigurationProperties
@ConfigurationProperties("la")
public class UtilProperties {

    private String host;
    private String port;
    private String billingScheduleUrl;
    private String autoLoanAutoCollateral;
    private String racfId;
    private String racfpwd;
    private String clientId;
    private String updateLicenceInPinnacleUrl;
    private List<String> adfsClientIds;
    private String pinnacleAdfsClientId;
    private String pinnacleAdfsSecret;
    private String pinnacleAdfsGrantType;
    private String pinnacleAdfsUrl;
    private String pinnacleAdfsResource;
    private String autoLoanAffiliateUrl;
    private String autoLoanBillingDef;
    private String autoLoanChangeProcess;
    private String pinnacleUrlForUpdateAffiliateNumber;
    private String autoLoanAcctUrl;
    private String autoLoanEndorserUrl;
    private String autoLoanCustAddrFormat2Url;
    private String autoLoanAcctQuickViewUrl;
    private String autoLoanProcessExt;
}
