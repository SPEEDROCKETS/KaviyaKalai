package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrnStatus  {

    @JsonProperty("MsgCode")
    private String msgCode;

    @JsonProperty("MsgSeverity")
    private String msgSeverity;

    @JsonProperty("MsgText")
    private String msgText;

    @JsonProperty("MsgAcct")
    private String msgAcct;

    @JsonProperty("MsgPgm")
    private String msgPgm;

    @JsonProperty("MsgDag")
    private String msgDag;

    @JsonProperty("MsgField")
    private String msgField;

    @JsonProperty("MsgFieldDim1")
    private String msgFieldDim1;

    @JsonProperty("MsgFieldDim2")
    private String msgFieldDim2;

    @JsonProperty("MsgFieldDim3")
    private String msgFieldDim3;
}
