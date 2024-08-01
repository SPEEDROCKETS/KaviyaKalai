package com.ford.ces.api.util;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("classpath:/ValidationMessages.properties")
@Builder
public class AffiliateUtil {

    @Value("${listOfBankCodes}")
    String[] listOfBankCodes;

}
