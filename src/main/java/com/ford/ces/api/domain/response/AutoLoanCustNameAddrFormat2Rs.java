package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AutoLoanCustNameAddrFormat2Rs {

    @JsonProperty("CustCtl1")
    public String custCtl1;
    @JsonProperty("CustCtl2")
    public String custCtl2;
    @JsonProperty("CustCtl3")
    public String custCtl3;
    @JsonProperty("CustCtl4")
    public String custCtl4;
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
    @JsonProperty("LastAddrChg")
    public String lastAddrChg;
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
