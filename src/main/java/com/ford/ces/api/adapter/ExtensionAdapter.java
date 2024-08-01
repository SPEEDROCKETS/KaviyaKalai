package com.ford.ces.api.adapter;

import com.ford.ces.api.domain.constants.LaConstants;
import com.ford.ces.api.domain.request.*;
import com.ford.ces.api.domain.request.extension.*;
import com.ford.ces.api.domain.response.LAAutoLoanEndorserResponse;
import com.ford.ces.api.domain.response.extension.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ford.ces.api.constant.CESConstant.RECEIVABLE_ACCOUNT_REGEX;
import static com.ford.ces.api.domain.constants.LaConstants.ITEMNUM;

@Component
public class ExtensionAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(ExtensionAdapter.class.getName());

    public LaAutoLoanAutoCollateralRequest buildExtensionLaAutoLoanAutoCollateralRequest(ReceivableAccountNumber receivableAccountNumber) {

        var rqstHdr = RqstHdr.builder()
                .lclPref(LaConstants.LCL_PREF)
                .build();
        var tSRqHdr = TSRqHdr.builder()
                .employeeId(LaConstants.EMPLOYEE_ID)
                .superOverride(receivableAccountNumber.getClientId())
                .build();
        var autoLoanAutoCollateralRq = com.ford.ces.api.domain.request.AutoLoanAutoCollateralRq.builder()
                .ctl1(LaConstants.CONTROL1)
                .ctl2(LaConstants.CONTROL2)
                .itemNum(ITEMNUM)
                .acctId(receivableAccountNumber.getReceivableAccountNumber())
                .build();
        var wIARACOOperation = WIARACOOperation.builder()
                .rqstHdr(rqstHdr)
                .tSRqHdr(tSRqHdr)
                .autoLoanAutoCollateralRq(autoLoanAutoCollateralRq)
                .build();

        return LaAutoLoanAutoCollateralRequest.builder()
                .wIARACOOperation(wIARACOOperation)
                .build();
    }

    public LaAutoLoanQuickViewRequest buildLaAutoLoanAcctQuickViewRequest(ReceivableAccountNumber receivableAccountNumber) {

        var rqstHdr = RqstHdr.builder()
                .lclPref(LaConstants.LCL_PREF)
                .build();
        var tSRqHdr = TSRqHdr.builder()
                .employeeId(LaConstants.EMPLOYEE_ID)
                .superOverride(receivableAccountNumber.getClientId())
                .build();
        var autoLoanAcctQuickViewRq = AutoLoanAcctQuickViewRq.builder()
                .ctl1(LaConstants.CONTROL1)
                .ctl2(LaConstants.CONTROL2)
                .acctId(receivableAccountNumber.getReceivableAccountNumber())
                .build();
        var wIARAQVOperation = WIARAQVOperation.builder()
                .rqstHdr(rqstHdr)
                .tSRqHdr(tSRqHdr)
                .autoLoanAcctQuickViewRq(autoLoanAcctQuickViewRq)
                .build();

        return LaAutoLoanQuickViewRequest.builder()
                .wIARAQVOperation(wIARAQVOperation)
                .build();
    }

    public ExtensionDetailsResponse buildExtensionResponse(LaAutoLoanAutoCollateralResponseINQ extAutoLoanAutoCollateralResponse, LAAutoLoanEndorserResponse extAutoLoanEndorserResponse, LaAutoLoanAcctQuickViewRs autoLoanAcctQuickViewRS) {
        ExtensionDetailsResponse extensionDetailsResponse = ExtensionDetailsResponse.builder()
                .extensionResponse(ExtensionResponse.builder()
                        .productCd(autoLoanAcctQuickViewRS.getWiaraqvOperationResponse().getAutoLoanAcctQuickViewRs().get(0).getProductCd())
                        .relationshipFound(getRelationshipStatus(extAutoLoanEndorserResponse))
                        .newUsedCd(extAutoLoanAutoCollateralResponse.getExtensionWIARACOOperationResponse().getAutoLoanAutoCollateralRs().get(0).getAutoCollateralListList().get(0).getNewUsedCd())
                        .daysPastDue(autoLoanAcctQuickViewRS.getWiaraqvOperationResponse().getAutoLoanAcctQuickViewRs().get(0).getDaysPastDue())
                        .daysPastDue30(autoLoanAcctQuickViewRS.getWiaraqvOperationResponse().getAutoLoanAcctQuickViewRs().get(0).getDaysPastDue30())
                        .daysPastDue60(autoLoanAcctQuickViewRS.getWiaraqvOperationResponse().getAutoLoanAcctQuickViewRs().get(0).getDaysPastDue60())
                        .daysPastDue90(autoLoanAcctQuickViewRS.getWiaraqvOperationResponse().getAutoLoanAcctQuickViewRs().get(0).getDaysPastDue90())
                        .daysPastDue120(autoLoanAcctQuickViewRS.getWiaraqvOperationResponse().getAutoLoanAcctQuickViewRs().get(0).getDaysPastDue120())
                        .daysPastDue150(autoLoanAcctQuickViewRS.getWiaraqvOperationResponse().getAutoLoanAcctQuickViewRs().get(0).getDaysPastDue150())
                        .daysPastDue180(autoLoanAcctQuickViewRS.getWiaraqvOperationResponse().getAutoLoanAcctQuickViewRs().get(0).getDaysPastDue180())
                        .originalTerm(autoLoanAcctQuickViewRS.getWiaraqvOperationResponse().getAutoLoanAcctQuickViewRs().get(0).getOriginalTerm())
                        .numPmtsRemaining(autoLoanAcctQuickViewRS.getWiaraqvOperationResponse().getAutoLoanAcctQuickViewRs().get(0).getNumPmtsRem())
                        .numPmtsMade(autoLoanAcctQuickViewRS.getWiaraqvOperationResponse().getAutoLoanAcctQuickViewRs().get(0).getNumPmtsMade())
                        .monthsExtendedItd(autoLoanAcctQuickViewRS.getWiaraqvOperationResponse().getAutoLoanAcctQuickViewRs().get(0).getMonthsExtendedItd())
                        .currentTerm(autoLoanAcctQuickViewRS.getWiaraqvOperationResponse().getAutoLoanAcctQuickViewRs().get(0).getCurrentTerm())
                        .maturityDt(autoLoanAcctQuickViewRS.getWiaraqvOperationResponse().getAutoLoanAcctQuickViewRs().get(0).getMaturityDt())
                        .nextDueDt(autoLoanAcctQuickViewRS.getWiaraqvOperationResponse().getAutoLoanAcctQuickViewRs().get(0).getNextDueDt())
                        .scheduledPmtAmt(autoLoanAcctQuickViewRS.getWiaraqvOperationResponse().getAutoLoanAcctQuickViewRs().get(0).getBillingFlatAmt())
                        .pastDueDt(autoLoanAcctQuickViewRS.getWiaraqvOperationResponse().getAutoLoanAcctQuickViewRs().get(0).getOldestDueDt())
                        .acctStatusCd(autoLoanAcctQuickViewRS.getWiaraqvOperationResponse().getAutoLoanAcctQuickViewRs().get(0).getAcctStatusCd())
                        .processStatusCd(autoLoanAcctQuickViewRS.getWiaraqvOperationResponse().getAutoLoanAcctQuickViewRs().get(0).getProcessStatusCd())
                        .lastBalChangeDt(autoLoanAcctQuickViewRS.getWiaraqvOperationResponse().getAutoLoanAcctQuickViewRs().get(0).getLastBalChangeDt())
                        .receivableAccountNumber(autoLoanAcctQuickViewRS.getWiaraqvOperationResponse().getAutoLoanAcctQuickViewRs().get(0).getAcctId().replaceAll(RECEIVABLE_ACCOUNT_REGEX,""))
                        .build())
                .build();
        LOG.info("Retrieve extension completed successfully");
        return extensionDetailsResponse;
    }

    public LaAutoLoanProcessExtRequest buildLaAutoLoanProcessExtReqExtensionFee(ExtensionRequest extensionRequest, int numberOfMonths) {
        var rqstHdr = RqstHdr.builder()
                .lclPref(LaConstants.LCL_PREF)
                .build();

        var tSRqHdr = TSRqHdr.builder()

                .employeeId(LaConstants.EMPLOYEE_ID)
                .superOverride(extensionRequest.getClientId())
                .build();

        var autoLoanProcessExtRq = AutoLoanProcessExtRq.builder()
                .ctl1(LaConstants.CONTROL1)
                .ctl2(LaConstants.CONTROL2)
                .acctId(extensionRequest.getReceivableAccountNumber())
                .paymentsToExtend(numberOfMonths)
                .extensionReasonCd(extensionRequest.getExtensionReq().getReasonCd())
                .build();

        List<AutoLoanProcessExtRq> listOfAutoLoanProcessExtRq = new ArrayList<>();
        listOfAutoLoanProcessExtRq.add(autoLoanProcessExtRq);

        var wIAR2EXOperation = WIAR2EXOperation.builder()
                .rqstHdr(rqstHdr)
                .tSRqHdr(tSRqHdr)
                .autoLoanProcessExtRq(listOfAutoLoanProcessExtRq).build();

        return LaAutoLoanProcessExtRequest.builder().wiar2EXOperation(wIAR2EXOperation).build();

    }

    public LaAutoLoanProcessExtRequest buildLaAutoLoanProcessExtRequest(ProcessExtensionRequest extensionRequest, int numberOfMonths) {
        var rqstHdr = RqstHdr.builder()
                .lclPref(LaConstants.LCL_PREF)
                .build();

        var tSRqHdr = TSRqHdr.builder()

                .employeeId(LaConstants.EMPLOYEE_ID)
                .superOverride(extensionRequest.getClientId())
                .build();

        var autoLoanProcessExtRq = AutoLoanProcessExtRq.builder()
                .ctl1(LaConstants.CONTROL1)
                .ctl2(LaConstants.CONTROL2)
                .acctId(extensionRequest.getReceivableAccountNumber())
                .paymentsToExtend(numberOfMonths)
                .extensionReasonCd(extensionRequest.getProcessExtensionReq().getReasonCd())
                .effectiveDt(extensionRequest.getProcessExtensionReq().getEffectiveDt())
                .build();

        List<AutoLoanProcessExtRq> listOfAutoLoanProcessExtRq = new ArrayList<>();
        listOfAutoLoanProcessExtRq.add(autoLoanProcessExtRq);

        var wIAR2EXOperation = WIAR2EXOperation.builder()
                .rqstHdr(rqstHdr)
                .tSRqHdr(tSRqHdr)
                .autoLoanProcessExtRq(listOfAutoLoanProcessExtRq).build();

        return LaAutoLoanProcessExtRequest.builder().wiar2EXOperation(wIAR2EXOperation).build();

    }

    public ProcessExtensionResponse buildProcessExtensionResponse(LaAutoLoanProcessExtResponse laAutoLoanProcessExtResponse) {

        ProcessExtensionResponse processExtensionResponse = ProcessExtensionResponse.builder().message(laAutoLoanProcessExtResponse.getWIAR2EXOperationResponse().getTsRsHdr().getProcessMessage()).build();
        LOG.info("Process extension completed successfully");
        return processExtensionResponse;
    }

    public ExtensionFeeResponse buildExtensionFeeResponse(List<LaAutoLoanProcessExtResponse> extensionFeeList) {
        ExtensionFeeResponse extensionFeeResponse = ExtensionFeeResponse.builder()
                .receivableAccountNumber(extensionFeeList.get(0).getWIAR2EXOperationResponse().getAutoLoanProcessExtRs().get(0).getAcctId().replaceAll(RECEIVABLE_ACCOUNT_REGEX,""))
                .extensionFeeList(extensionFeeList.stream().map(extensionFee ->
                     ExtensionFee.builder()
                            .numberOfMonthsExtended(extensionFee.getWIAR2EXOperationResponse().getAutoLoanProcessExtRs().get(0).getPaymentsToExtend())
                            .feeAmt(extensionFee.getWIAR2EXOperationResponse().getAutoLoanProcessExtRs().get(0).getMaxExtensionFeeAmt())
                            .nextPaymentStartDate(extensionFee.getWIAR2EXOperationResponse().getAutoLoanProcessExtRs().get(0).getFirstDueAfterExtensionDt())
                            .newMaturityDate(extensionFee.getWIAR2EXOperationResponse().getAutoLoanProcessExtRs().get(0).getMaturityDtAfterExtension())
                            .build()
                ).collect(Collectors.toList()))
                .build();
        LOG.info("Get extension fee completed successfully");
        return extensionFeeResponse;
    }
    public boolean getRelationshipStatus(LAAutoLoanEndorserResponse autoLoanEndorserResponse) {
        return !ObjectUtils.isEmpty(autoLoanEndorserResponse.getWIAREDROperationResponse()
                .getAutoLoanEndorserRs());
    }
}
