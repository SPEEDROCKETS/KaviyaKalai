package com.ford.ces.api.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ford.ces.api.domain.response.Forename;
import com.ford.ces.api.domain.response.Surname;
import lombok.*;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AutoLoanCustNameAddrFormat2Rq {

    @JsonProperty("Ctl1")
    public String ctl1;
    @JsonProperty("Ctl2")
    public String ctl2;
    @JsonProperty("Ctl3")
    public String ctl3;
    @JsonProperty("Ctl4")
    public String ctl4;
    @JsonProperty("CustId")
    public String custId;
    @JsonProperty("ShortName")
    public String shortName;
    @JsonProperty("CustTitle")
    public String custTitle;
    @JsonProperty("Forenames")
    public ArrayList<Forename> forenames;
    @JsonProperty("Surnames")
    public ArrayList<Surname> surnames;
    @JsonProperty("Generation")
    public String generation;
    @JsonProperty("HouseNumber")
    public String houseNumber;
    @JsonProperty("HouseName")
    public String houseName;
    @JsonProperty("Street")
    public String street;
    @JsonProperty("Locality")
    public String locality;
    @JsonProperty("CityTown")
    public String cityTown;
    @JsonProperty("County")
    public String county;
    @JsonProperty("StateProvince")
    public String stateProvince;
    @JsonProperty("PostCd")
    public String postCd;
    @JsonProperty("AddrFromDt")
    public String addrFromDt;
    @JsonProperty("Country")
    public String country;
    @JsonProperty("ForeignAddrInd")
    public String foreignAddrInd;
    @JsonProperty("Language")
    public String language;
    @JsonProperty("PersonalCommercialCd")
    public String personalCommercialCd;
    @JsonProperty("OptOutCd")
    public String optOutCd;
    @JsonProperty("PurgeDt")
    public String purgeDt;
    @JsonProperty("CreditBureauAddrCd")
    public String creditBureauAddrCd;
    @JsonProperty("LegalEntityId")
    public String legalEntityId;
    @JsonProperty("MasterCustNbr")
    public String masterCustNbr;
    @JsonProperty("NationalId")
    public String nationalId;
    @JsonProperty("NationalType")
    public String nationalType;
    @JsonProperty("TimeZone")
    public String timeZone;
    @JsonProperty("InvalidInd")
    public String invalidInd;
    @JsonProperty("Deceased")
    public String deceased;
    @JsonProperty("NationalIdValidDt")
    public String nationalIdValidDt;
    @JsonProperty("NationalIdExpireDt")
    public String nationalIdExpireDt;
    @JsonProperty("Nationality")
    public String nationality;

}
