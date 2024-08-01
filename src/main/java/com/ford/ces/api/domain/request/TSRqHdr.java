package com.ford.ces.api.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TSRqHdr {

    @JsonProperty("MessageFormat")
    private String messageFormat;

    @JsonProperty("EmployeeId")
    private String employeeId;

    @JsonProperty("LanguageCd")
    private String languageCd;

    @JsonProperty("ApplCode")
    private String applCode;

    @JsonProperty("FuncSecCode")
    private String funcSecCode;

    @JsonProperty("SourceCode")
    private String sourceCode;

    @JsonProperty("EffectiveDate")
    private String effectiveDate;

    @JsonProperty("TransDate")
    private String transDate;

    @JsonProperty("TransTime")
    private String transTime;

    @JsonProperty("TransSeq")
    private String transSeq;

    @JsonProperty("SuperOverride")
    private String superOverride;

    @JsonProperty("TellerOverride")
    private String tellerOverride;

    @JsonProperty("ReportLevels")
    private String reportLevels;

    @JsonProperty("PhysicalLocation")
    private String physicalLocation;

    @JsonProperty("Rebid")
    private String rebid;

    @JsonProperty("Reentry")
    private String reentry;
    @JsonProperty("Correction")
    private String correction;

    @JsonProperty("Training")
    private String training;

}
