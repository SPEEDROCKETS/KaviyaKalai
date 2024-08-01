package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressFormat2 {
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
}
