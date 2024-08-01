package com.ford.ces.api.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorMessages {
	public static final String SPLITTER =  ":";
	public static final String COMMA_SPLITTER =  ",";
	public static final String ERROR_CODE_SPLITTER =  "-";
	public static final String ACCOUNT_NUMBER_NOT_FOUND = "No Data Found for the given Account Number";
	public static final String INVALID_ACCOUNT = "Invalid Account Number";
	public static final String CLIENT_ID_VALIDATION_FAILED = "Invalid Client ID ";
	public static final String CLIENT_ID_ERROR_CODE  = "EC2006";
	public static final String INVALID_LICENSE = "Invalid License Number - License number should start with Chinese character";
	public static final String LICENSE_NUMBER_EXIST = "The License Number already exist for the given account number";
	public static final String LA_UPDATED_SUCCESSFULLY_AND_PINNACLE_UPDATE_FAILED = "LA updated successfully and Pinnacle update failed";
	public static final String ACCOUNT_NOT_FOUND = "ACCOUNT NOT FOUND";
	public static final String INVALID_LIC_REG_NUMBER_FOUND_DURING_USER_EDITS = "INVALID LIC/REG NUMBER FOUND DURING USER EDITS";
	public static final String UPDATE_FUNCTION_SELECTED_BUT_NO_INPUT_ENTERED = "UPDATE FUNCTION SELECTED BUT NO INPUT ENTERED";
	public static final String AFFILIATE_INFORMATION_EXIST = "The Affiliate Information already exist for the given Account Number";
	public static final String LA_SEQUENCE_UPDATE_FAILED = "Sequence update failed";
	public static final String LA_AUTO_DEBIT_UPDATE_FAILED = "AutoDebitFlag Update Failed";

	public static final String AFFILIATE_UPDATE_FAILED = "Affiliate update failed";
	public static final String AFFILIATE_UPDATE_FAILED_CODE = "EC2016";
	public static final String INVALID_BANK_CODE = "Invalid bank code";
	public static final String AUTO_DEBIT_FLAG_UPDATE_FAILED_CODE = "EC2024";

	public static final String DATES_ALREADY_EXITS_FOR_BUYER_COBUYER = "The Valid Date (or) Expiry Date already exists for Buyer/Co-Buyer with the given account number";
	public static final String CUSTOMER_RELATIONSHIP_NOT_FOUND = "CUSTOMER RELATIONSHIP NOT FOUND";
	public static final String BUYER_COBUYER_FAILED = "National Id Date Update Failed";
	public static final String UPDATE_DATE_LOGGER = "UpdateDate -";
	public static final String UPDATE_DATE_SUCCESS_LOGGER = "Update date completed successfully";
	public static final String UPDATE_LICENSE_LOGGER = "UpdateLicensePlateNumber - ";
	public static final String UPDATE_AFFILIATE_LOGGER = "UpdateAffiliateNumber -";
}
