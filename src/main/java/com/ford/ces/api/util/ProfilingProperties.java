package com.ford.ces.api.util;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Configuration
public class ProfilingProperties {

    @Value("${spring.audience-id}")
    private String audience;
}
