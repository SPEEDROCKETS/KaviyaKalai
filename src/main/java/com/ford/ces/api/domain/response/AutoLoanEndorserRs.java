package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AutoLoanEndorserRs {

    @JsonProperty("EndorserList")
    public ArrayList<EndorserList> endorserList;
    @JsonProperty("Ctl1")
    public String ctl1;
    @JsonProperty("Ctl2")
    public String ctl2;
    @JsonProperty("Ctl3")
    public String ctl3;
    @JsonProperty("Ctl4")
    public String ctl4;
    @JsonProperty("AcctId")
    public String acctId;
    @JsonProperty("ShortName")
    public String shortName;
    @JsonProperty("RecsReturned")
    public int recsReturned;
    @JsonProperty("MoreInd")
    public String moreInd;
    @JsonProperty("MoreCustCtl1")
    public int moreCustCtl1;
    @JsonProperty("MoreCustCtl2")
    public int moreCustCtl2;
    @JsonProperty("MoreCustCtl3")
    public int moreCustCtl3;
    @JsonProperty("MoreCustCtl4")
    public int moreCustCtl4;
    @JsonProperty("MoreCustId")
    public int moreCustId;
    @JsonProperty("MoreRelTypeCd")
    public String moreRelTypeCd;
}
