package com.ford.ces.api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AutoLoanAcctRs {

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
    @JsonProperty("EffectiveDt")
    public String effectiveDt;
    @JsonProperty("NameAddrDet")
    public ArrayList<NameAddrDet> nameAddrDet;
    @JsonProperty("City")
    public String city;
    @JsonProperty("State")
    public String state;
    @JsonProperty("Zip")
    public String zip;
    @JsonProperty("CountryCd")
    public String countryCd;
    @JsonProperty("PayoffAmt")
    public double payoffAmt;
    @JsonProperty("PayoffDailyAmt")
    public double payoffDailyAmt;
    @JsonProperty("OriginalLoanAmt")
    public double originalLoanAmt;
    @JsonProperty("OriginalProceeds")
    public double originalProceeds;
    @JsonProperty("BankruptcyCd")
    public String bankruptcyCd;
    @JsonProperty("AcctStatus")
    public String acctStatus;
    @JsonProperty("AcctStatusCd")
    public String acctStatusCd;
    @JsonProperty("IntType")
    public String intType;
    @JsonProperty("ProductCd")
    public String productCd;
    @JsonProperty("IntRate")
    public double intRate;
    @JsonProperty("OriginalIntRate")
    public double originalIntRate;
    @JsonProperty("UserGroup6")
    public String userGroup6;
    @JsonProperty("ServiceCenter")
    public String serviceCenter;
    @JsonProperty("LoanEntryDt")
    public String loanEntryDt;
    @JsonProperty("RepossessionDt")
    public String repossessionDt;
    @JsonProperty("StateNum")
    public String stateNum;
    @JsonProperty("RegionCd")
    public String regionCd;
    @JsonProperty("RegionDesc")
    public String regionDesc;
    @JsonProperty("NrcCollectionInd")
    public String nrcCollectionInd;
    @JsonProperty("LateChargesBal")
    public double lateChargesBal;
    @JsonProperty("OtherFeesBal")
    public double otherFeesBal;
    @JsonProperty("PrnBal")
    public double prnBal;
    @JsonProperty("IntBal")
    public double intBal;
    @JsonProperty("BillingFlatAmt")
    public double billingFlatAmt;
    @JsonProperty("BillingMethodCd")
    public String billingMethodCd;
    @JsonProperty("AcctEffectiveDt")
    public String acctEffectiveDt;
    @JsonProperty("MaturityDt")
    public String maturityDt;
    @JsonProperty("CurrentTerm")
    public int currentTerm;
    @JsonProperty("StatusDt")
    public String statusDt;
    @JsonProperty("NumPmtsMade")
    public int numPmtsMade;
    @JsonProperty("NextDueDt")
    public String nextDueDt;
    @JsonProperty("NumPmtsRemaining")
    public int numPmtsRemaining;
    @JsonProperty("NumRenewal")
    public int numRenewal;
    @JsonProperty("NextDueAmt")
    public double nextDueAmt;
    @JsonProperty("OldestDueDt")
    public String oldestDueDt;
    @JsonProperty("MonthsExtendedItd")
    public int monthsExtendedItd;
    @JsonProperty("LastMaintDt")
    public String lastMaintDt;
    @JsonProperty("TaxOutstanding")
    public double taxOutstanding;
    @JsonProperty("YtdIntPd")
    public double ytdIntPd;
    @JsonProperty("TotalAmtPastDue")
    public double totalAmtPastDue;
    @JsonProperty("PartialPmt")
    public double partialPmt;
    @JsonProperty("LastBalChangeDt")
    public String lastBalChangeDt;
    @JsonProperty("LastYtdIntPd")
    public double lastYtdIntPd;
    @JsonProperty("HomePhone")
    public String homePhone;
    @JsonProperty("WorkPhone")
    public String workPhone;
    @JsonProperty("NameAddrNum")
    public String nameAddrNum;
    @JsonProperty("CreditorName")
    public String creditorName;
    @JsonProperty("Desc")
    public String desc;
    @JsonProperty("IdentificationNum")
    public String identificationNum;
    @JsonProperty("Delinquency")
    public ArrayList<Delinquency> delinquency;
    @JsonProperty("EndorserLiabType")
    public String endorserLiabType;
    @JsonProperty("SubApplIdCd")
    public String subApplIdCd;
    @JsonProperty("SingleOrBalloonCd")
    public String singleOrBalloonCd;
    @JsonProperty("SingleOrBalloonDesc")
    public String singleOrBalloonDesc;
    @JsonProperty("AccountingFormatCd")
    public String accountingFormatCd;
    @JsonProperty("TaxQualityInd")
    public String taxQualityInd;
    @JsonProperty("LeaseCurrentEndValueAmt")
    public double leaseCurrentEndValueAmt;
    @JsonProperty("DownPmtAmt")
    public double downPmtAmt;
    @JsonProperty("LastPmtAmt")
    public double lastPmtAmt;
    @JsonProperty("LastPmtDt")
    public String lastPmtDt;
    @JsonProperty("OriginalTerm")
    public int originalTerm;
    @JsonProperty("OriginalMaturityDt")
    public String originalMaturityDt;
    @JsonProperty("OriginalLeaseEndValue")
    public double originalLeaseEndValue;
    @JsonProperty("AddressFormat2")
    public ArrayList<AddressFormat2> addressFormat2;
}
