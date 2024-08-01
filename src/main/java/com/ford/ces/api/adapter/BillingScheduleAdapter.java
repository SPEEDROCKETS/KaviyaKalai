package com.ford.ces.api.adapter;


import com.ford.ces.api.domain.constants.LaConstants;
import com.ford.ces.api.domain.request.*;
import org.springframework.stereotype.Component;
import static com.ford.ces.api.domain.constants.LaConstants.ITEMNUM;

@Component
public class BillingScheduleAdapter {

    public LaAutoLoanBillingRequest buildLaAutoLoanBillingRequest(ReceivableAccountNumber receivableAccountNumber){

               var rqstHdr = RqstHdr.builder()
                .lclPref(LaConstants.LCL_PREF)
                .build();
        var tSRqHdr = TSRqHdr.builder()
                .employeeId(LaConstants.EMPLOYEE_ID)
                .superOverride(receivableAccountNumber.getClientId())
                .build();

        var autoLoanBillingSchedDetailRq = AutoLoanBillingSchedDetailRq.builder()
                .ctl1(LaConstants.CONTROL1)
                .ctl2(LaConstants.CONTROL2)
                .acctId(receivableAccountNumber.getReceivableAccountNumber())
                .recsRequested(LaConstants.RECSREQUESTED)
                .build();
        var wIARBSDOperation = WIARBSDOperation.builder()
                .rqstHdr(rqstHdr)
                .tSRqHdr(tSRqHdr)
                .autoLoanBillingSchedDetailRq(autoLoanBillingSchedDetailRq)
                .build();

        return LaAutoLoanBillingRequest.builder()
                .wIARBSDOperation(wIARBSDOperation)
                .build();
    }

    public LaAutoLoanAutoCollateralRequest buildLaAutoLoanAutoCollateralRequest(LicenseNumber licenseNumber){

        var rqstHdr = RqstHdr.builder()
                .lclPref(LaConstants.LCL_PREF)
                .build();
        var tSRqHdr = TSRqHdr.builder()
                .employeeId(LaConstants.EMPLOYEE_ID)
                .superOverride(licenseNumber.getClientId())
                .build();
        var autoLoanAutoCollateralRq = com.ford.ces.api.domain.request.AutoLoanAutoCollateralRq.builder()
                .ctl1(LaConstants.CONTROL1)
                .ctl2(LaConstants.CONTROL2)
                .itemNum(ITEMNUM)
                .acctId(licenseNumber.getReceivableAccountNumber())
                .licenseNum(licenseNumber.getLicenseNumber())
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
    public PinnacleUpdateLicenseRequest buildLicenceNumberRequestBodyRequest(LicenseNumber licenseNumber){

        return PinnacleUpdateLicenseRequest.builder()
                .lcnsPlateN(licenseNumber.getLicenseNumber())
                .rcvblAccNbr(licenseNumber.getReceivableAccountNumber())
                .build();
    }
}
