package com.ford.ces.api.adapter;

import com.ford.ces.api.domain.constants.LaConstants;
import com.ford.ces.api.domain.request.*;
import com.ford.ces.api.domain.response.AffiliateAccounts;
import org.springframework.stereotype.Component;

@Component
public class AffiliateNumberUpdateAdapter {

    private static final String YES = "YES";
    private static final String NO = "NO";

    public LaAutoLoanAffiliateRequest buildLaAutoLoanAffiliateRequest(UpdateAffiliateDetailsRequest updateAffiliateDetailsRequest){

        var rqstHdr = RqstHdr.builder()
                .lclPref(LaConstants.LCL_PREF)
                .build();

        var tSRqHdr = TSRqHdr.builder()
                .employeeId(LaConstants.EMPLOYEE_ID)
                .superOverride(updateAffiliateDetailsRequest.getClientId())
                .build();

        var autoLoanAffiliateRq = AutoLoanAffiliateRq.builder()
                .acctId(updateAffiliateDetailsRequest.getReceivableAccountNumber())
                .build();

        var wIARAFLOperation = WIARAFLOperation.builder()
                .rqstHdr(rqstHdr)
                .tSRqHdr(tSRqHdr)
                .autoLoanAffiliateRq(autoLoanAffiliateRq)
                .build();

        return LaAutoLoanAffiliateRequest.builder()
                .wIARAFLOperation(wIARAFLOperation)
                .build();
    }

    public LaAutoLoanAffiliateRequest buildLaAutoLoanAffiliateUpdateOrAddRequest(AffiliateAccounts affiliateAccount, UpdateAffiliateDetailsRequest updateAffiliateDetailsRequest, Integer seqNum){

        var rqstHdr = RqstHdr.builder()
                .lclPref(LaConstants.LCL_PREF)
                .build();

        var tSRqHdr = TSRqHdr.builder()
                .employeeId(LaConstants.EMPLOYEE_ID)
                .superOverride(updateAffiliateDetailsRequest.getClientId())
                .build();

        var autoLoanAffiliateRq = AutoLoanAffiliateRq.builder()
                .acctId(updateAffiliateDetailsRequest.getReceivableAccountNumber())
                .seqNum("0"+ seqNum)
                .affCtls(affiliateAccount.getAffCtls())
                .affName(affiliateAccount.getAffName())
                .affId(updateAffiliateDetailsRequest.getBankAccountNumber())
                .sysCd(affiliateAccount.getSysCd())
                .abaNum(updateAffiliateDetailsRequest.getBankCode())
                .autoDebitInd(affiliateAccount.getAutoDebitInd())
                .autoCredCd(affiliateAccount.getAutoCreditCd())
                .compensateBalCd(affiliateAccount.getCompensateBalCd())
                .maturityBillInd(checkSequenceNumber(seqNum))
                .userInd1(affiliateAccount.getUserInd1())
                .userInd2(affiliateAccount.getUserInd2())
                .userInd3(affiliateAccount.getUserInd3())
                .autoDebitFirstDt(affiliateAccount.getAutoDebitFirstDt())
                .autoDebitStopDt(affiliateAccount.getAutoDebitStopDt())
                .autoDebitMethod(affiliateAccount.getAutoDebitMethod())
                .autoDebitFreq(affiliateAccount.getAutoDebitFreq())
                .autoDebitIncrement(affiliateAccount.getAutoDebitIncrement())
                .autoDebitLeadDays(affiliateAccount.getAutoDebitLeadDays())
                .autoDebitDay(affiliateAccount.getAutoDebitDay())
                .build();

        var wIARAFLOperation = WIARAFLOperation.builder()
                .rqstHdr(rqstHdr)
                .tSRqHdr(tSRqHdr)
                .autoLoanAffiliateRq(autoLoanAffiliateRq)
                .build();

        return LaAutoLoanAffiliateRequest.builder()
                .wIARAFLOperation(wIARAFLOperation)
                .build();
    }


    public LaAutodebitRequest buildLaAutodebitRequest(UpdateAffiliateDetailsRequest updateAffiliateDetailsRequest){

        var rqstHdr = RqstHdr.builder()
                .lclPref(LaConstants.LCL_PREF)
                .build();
        var tSRqHdr = TSRqHdr.builder()
                .employeeId(LaConstants.EMPLOYEE_ID)
                .superOverride(updateAffiliateDetailsRequest.getClientId())
                .build();

        var autoLoanBillingDefRq = AutoLoanBillingDefRq.builder()
                .ctl1(LaConstants.CONTROL1)
                .ctl2(LaConstants.CONTROL2)
                .acctId(updateAffiliateDetailsRequest.getReceivableAccountNumber())
                .build();
        var  wiarbilOperation= WIARBILOperation.builder()
                .rqstHdr(rqstHdr)
                .tSRqHdr(tSRqHdr)
                .autoLoanBillingDefRq(autoLoanBillingDefRq)
                .build();

        return LaAutodebitRequest.builder()
                .wiarbilOperation(wiarbilOperation)
                .build();
    }

    public LaAutoLoanBillingChangeProcessRequest buildLaAutoLoanBillingChangeProcessRequest(UpdateAffiliateDetailsRequest updateAffiliateDetailsRequest){

        var rqstHdr = RqstHdr.builder()
                .lclPref(LaConstants.LCL_PREF)
                .build();
        var tSRqHdr = TSRqHdr.builder()
                .employeeId(LaConstants.EMPLOYEE_ID)
                .superOverride(updateAffiliateDetailsRequest.getClientId())
                .build();
        var autoLoanBillingChangeProcessRq=AutoLoanBillingChangeProcessRq.builder()
                .billingChangeAcctCd("E")
                .billingDefinitionId("001")
                .acctId(updateAffiliateDetailsRequest.getReceivableAccountNumber())
                .affilateAcctSeqNum(2)
                .build();

        var  wIARBCPOperation= WIARBCPOperation.builder()
                .rqstHdr(rqstHdr)
                .tSRqHdr(tSRqHdr)
                .autoLoanBillingChangeProcessRq(autoLoanBillingChangeProcessRq)
                .build();

        return LaAutoLoanBillingChangeProcessRequest.builder()
                .wiarbcpOperation(wIARBCPOperation)
                .build();
    }


    public PinnacleUpdateAffiliateRequest buildPinnacleAffiliateUpdateRequest(UpdateAffiliateDetailsRequest updateAffiliateDetailsRequest, String autoDebitValueForPinnacle) {

        return PinnacleUpdateAffiliateRequest.builder()
                .rcvblAccNbr(updateAffiliateDetailsRequest.getReceivableAccountNumber())
                .bankAcctNumber(updateAffiliateDetailsRequest.getBankAccountNumber())
                .bankCode(updateAffiliateDetailsRequest.getBankCode())
                .autoDebtStatusCd(autoDebitValueForPinnacle)
                .build();

    }

    public String checkSequenceNumber(Integer sequenceNumber) {
        return sequenceNumber==1 ? NO : YES; //NOSONAR
    }
}
