package com.ford.ces.api.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AutoLoanAutoCollateralRq {

    @JsonProperty("Ctl1")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String ctl1;

    @JsonProperty("Ctl2")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String ctl2;

    @JsonProperty("Ctl3")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String ctl3;

    @JsonProperty("Ctl4")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String ctl4;

    @JsonProperty("AcctId")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String acctId;

    @JsonProperty("EffectiveDt")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String effectiveDt;

    @JsonProperty("RecsRequested")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private float recsRequested;

    @JsonProperty("ItemNum")
    private String itemNum;

    @JsonProperty("VehicleType")
    private String vehicleType;

    @JsonProperty("AdditionalVehicleInfo")
    private String additionalVehicleInfo;

    @JsonProperty("VehicleIdentificationNum")
    private String vehicleIdentificationNum;

    @JsonProperty("VehicleYear")
    private String vehicleYear;

    @JsonProperty("MakeCd")
    private String makeCd;

    @JsonProperty("VehicleMake")
    private String vehicleMake;

    @JsonProperty("ModelCd")
    private String modelCd;

    @JsonProperty("VehicleModel")
    private String vehicleModel;

    @JsonProperty("Derivative")
    private String derivative;

    @JsonProperty("BodyStyle")
    private String bodyStyle;

    @JsonProperty("LicenseNum")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String licenseNum;

    @JsonProperty("LicenseState")
    private String licenseState;

    @JsonProperty("LicenseCountry")
    private String licenseCountry;

    @JsonProperty("IgnitionKeyCd")
    private String ignitionKeyCd;

    @JsonProperty("TrunkKeyCd")
    private String trunkKeyCd;

    @JsonProperty("VehicleColor")
    private String vehicleColor;

    @JsonProperty("Doors")
    private float doors;

    @JsonProperty("EngineType")
    private String engineType;

    @JsonProperty("EngineSize")
    private String engineSize;

    @JsonProperty("FuelType")
    private String fuelType;

    @JsonProperty("DriveType")
    private String driveType;

    @JsonProperty("TransmissionType")
    private String transmissionType;

    @JsonProperty("LicenseDt")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String licenseDt;

    @JsonProperty("LicenseCertificationNum")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String licenseCertificationNum;

    @JsonProperty("Odometer")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private float odometer;

    @JsonProperty("LastTitleMaintenanceDt")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String lastTitleMaintenanceDt;

    @JsonProperty("TitleStatus")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String titleStatus;

    @JsonProperty("OriginalValue")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private float originalValue;

    @JsonProperty("NewUsedCd")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String newUsedCd;

    @JsonProperty("TitleFollowupInd")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String titleFollowupInd;

    @JsonProperty("PoolCarTrackCd")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String poolCarTrackCd;

    @JsonProperty("BaseMsrp")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private float baseMsrp;

    @JsonProperty("InvoiceAmt")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private float invoiceAmt;

    @JsonProperty("MarkupAmt")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private float markupAmt;

    @JsonProperty("GrossVehicleWeight")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private float grossVehicleWeight;

    @JsonProperty("VinSubstituteControl")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String vinSubstituteControl;

    @JsonProperty("PromotionalCharges")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private float promotionalCharges;

    @JsonProperty("MsrpAddons")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private float msrpAddons;

    @JsonProperty("TradeValue")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private float tradeValue;

    @JsonProperty("TotalMsrp")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private float totalMsrp;
}