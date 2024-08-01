
package com.ford.ces.api.adapter;


import com.ford.ces.api.domain.constants.LaConstants;
import com.ford.ces.api.domain.request.*;
import org.springframework.stereotype.Component;

@Component
public class ValidateNationalIdDatesAdapter {

      public LaAutoLoanAcctRequest buildAutoLoanAcctRequest(ReceivableAccountNumber receivableAccountNumber){

        var rqstHdr = RqstHdr.builder()
                .lclPref(LaConstants.LCL_PREF)
                .build();
        var tSRqHdr = TSRqHdr.builder()
                .employeeId(LaConstants.EMPLOYEE_ID)
                .superOverride(receivableAccountNumber.getClientId())
                .build();


        var autoLoanAcctRq = AutoLoanAcctRq.builder()
                .ctl1(LaConstants.CONTROL1)
                .ctl2(LaConstants.CONTROL2)
                .acctId(receivableAccountNumber.getReceivableAccountNumber())
                .build();
        var wIARACTOperation = WIARACTOperation.builder()
                .rqstHdr(rqstHdr)
                .tSRqHdr(tSRqHdr)
                .autoLoanAcctRq(autoLoanAcctRq)
                .build();

        return LaAutoLoanAcctRequest.builder()
                .wiaractOperation(wIARACTOperation)
                .build();
    }


    public LaAutoLoanEndorserRequest buildAutoLoanEndorserRequest(ReceivableAccountNumber receivableAccountNumber){

        var rqstHdr = RqstHdr.builder()
                .lclPref(LaConstants.LCL_PREF)
                .build();

        var tSRqHdr = TSRqHdr.builder()
                .employeeId(LaConstants.EMPLOYEE_ID)
                .superOverride(receivableAccountNumber.getClientId())
                .build();

        var autoLoanEndorserRq = AutoLoanEndorserRq.builder()
                .ctl1(LaConstants.CONTROL1)
                .ctl2(LaConstants.CONTROL2)
                .acctId(receivableAccountNumber.getReceivableAccountNumber())
                .build();

        var wIAREDROperation = WIAREDROperation.builder()
                .rqstHdr(rqstHdr)
                .tSRqHdr(tSRqHdr)
                .autoLoanEndorserRq(autoLoanEndorserRq)
                .build();

        return LaAutoLoanEndorserRequest.builder()
                .wIAREDROperation(wIAREDROperation)
                .build();
    }

    public LaAutoLoanCustNameAddrFormat2Request buildAutoLoanCustAddrFormat2Request(String buyerCustomerId, ReceivableAccountNumber receivableAccountNumber){

        var rqstHdr = RqstHdr.builder()
                .lclPref(LaConstants.LCL_PREF)
                .build();

        var tSRqHdr = TSRqHdr.builder()
                .employeeId(LaConstants.EMPLOYEE_ID)
                .superOverride(receivableAccountNumber.getClientId())
                .build();

        var autoLoanCustNameFormat2 = AutoLoanCustNameAddrFormat2Rq.builder()
                .ctl1(LaConstants.CONTROL1)
                .ctl2(LaConstants.CONTROL2)
                .custId(buyerCustomerId)
                .build();

        var wIARCN2Operation = WIARCN2Operation.builder()
                .rqstHdr(rqstHdr)
                .tSRqHdr(tSRqHdr)
                .autoLoanCustNameAddrFormat2Rq(autoLoanCustNameFormat2)
                .build();

        return LaAutoLoanCustNameAddrFormat2Request.builder()
                .wiarcn2Operation(wIARCN2Operation)
                .build();
    }

    public LaAutoLoanCustNameAddrFormat2Request buildAutoLoanCustAddrFormat2RequestForUpdate(String buyerCustomerId,ReceivableAccountNumber receivableAccountNumber,String validDate,String expDate){

        var rqstHdr = RqstHdr.builder()
                .lclPref(LaConstants.LCL_PREF)
                .build();

        var tSRqHdr = TSRqHdr.builder()
                .employeeId(LaConstants.EMPLOYEE_ID)
                .superOverride(receivableAccountNumber.getClientId())
                .build();

        var autoLoanCustNameFormat2 = AutoLoanCustNameAddrFormat2Rq.builder()
                .ctl1(LaConstants.CONTROL1)
                .ctl2(LaConstants.CONTROL2)
                .custId(buyerCustomerId)
                .nationalIdValidDt(validDate)
                .nationalIdExpireDt(expDate)
                .build();

        var wIARCN2Operation = WIARCN2Operation.builder()
                .rqstHdr(rqstHdr)
                .tSRqHdr(tSRqHdr)
                .autoLoanCustNameAddrFormat2Rq(autoLoanCustNameFormat2)
                .build();

        return LaAutoLoanCustNameAddrFormat2Request.builder()
                .wiarcn2Operation(wIARCN2Operation)
                .build();
    }
}

