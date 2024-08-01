package com.ford.ces.api.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ford.ces.api.exception.ErrorMessages.ERROR_CODE_SPLITTER;

@ControllerAdvice
public class ChinaAmoExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ChinaAmoExceptionHandler.class.getName());

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleControllerException(Throwable ex, WebRequest request) {
        var responseBody = new ErrorResponse();
        HttpStatus status;
        String requestUri = ((ServletWebRequest) request).getRequest().getRequestURI();
        if (ex instanceof AccountNumerNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            responseBody.getErrorCodes().add(((AccountNumerNotFoundException) ex).getErrorCode());
            responseBody.getErrors().add(ex.getMessage());
        } else if (ex instanceof LaAuthenticationFailedException) {
            status = HttpStatus.NOT_FOUND;
            responseBody.getErrorCodes().add(((LaAuthenticationFailedException) ex).getErrorCode());
            responseBody.getErrors().add(ex.getMessage());
        } else if (ex instanceof LicenseNumberFormatException) {
            status = HttpStatus.NOT_FOUND;
            responseBody.getErrorCodes().add(((LicenseNumberFormatException) ex).getErrorCode());
            responseBody.getErrors().add(ex.getMessage());
        } else if (ex instanceof LaSystemException) {
            status = HttpStatus.SERVICE_UNAVAILABLE;
            responseBody.getErrors().add(ex.getMessage());
        } else if (ex instanceof LicenseNumberConflictException) {
            status = HttpStatus.CONFLICT;
            responseBody.getErrorCodes().add(((LicenseNumberConflictException) ex).getErrorCode());
            responseBody.getErrors().add(ex.getMessage());
        } else if (ex instanceof PinnacleUpdateFailedException) {
            status = HttpStatus.OK;
            responseBody.getErrorCodes().add(((PinnacleUpdateFailedException) ex).getErrorCode());
            responseBody.getErrors().add(ex.getMessage());
        } else if (ex instanceof PinnacleServiceException) {
            status = HttpStatus.OK;
            responseBody.getErrorCodes().add(((PinnacleServiceException) ex).getErrorCode());
            responseBody.getErrors().add(ex.getMessage());
        } else if (ex instanceof AffiliateAlreadyExistException) {
            status = HttpStatus.OK;
            responseBody.getErrorCodes().add(((AffiliateAlreadyExistException) ex).getErrorCode());
            responseBody.getErrors().add(ex.getMessage());
        } else if (ex instanceof LaAutodebitFlagUpdateFailedException) {
            status = HttpStatus.PRECONDITION_FAILED;
            responseBody.getErrorCodes().add(((LaAutodebitFlagUpdateFailedException) ex).getErrorCode());
            responseBody.getErrors().add(ex.getMessage());
        } else if (ex instanceof AffiliateException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            responseBody.getErrorCodes().add(((AffiliateException) ex).getErrorCode());
            responseBody.getErrors().add(ex.getMessage());
        } else if (ex instanceof LaSequenceUpdateFailedException) {
            status = HttpStatus.CONFLICT;
            responseBody.getErrorCodes().add(((LaSequenceUpdateFailedException) ex).getErrorCode());
            responseBody.getErrors().add(ex.getMessage());
        } else if (ex instanceof DatesAlreadyExistsException) {
            status = HttpStatus.CONFLICT;
            responseBody.getErrorCodes().add(((DatesAlreadyExistsException) ex).getErrorCode());
            responseBody.getErrors().add(ex.getMessage());
        } else if (ex instanceof NationalIdUpdateFailedException) {
            status = HttpStatus.PARTIAL_CONTENT;
            responseBody.getErrorCodes().add(((NationalIdUpdateFailedException) ex).getErrorCode());
            responseBody.getErrors().add(ex.getMessage());
        } else if (ex instanceof InvalidBankCodeException) {
            status = HttpStatus.NOT_FOUND;
            responseBody.getErrorCodes().add(((InvalidBankCodeException) ex).getErrorCode());
            responseBody.getErrors().add(ex.getMessage());
        } else if (ex instanceof LAServiceException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            responseBody.getErrorCodes().add(((LAServiceException) ex).getErrorCode());
            responseBody.getErrors().add(ex.getMessage());
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            String message = ex.getMessage() != null ? ex.getMessage() : "An error occurred.";
            responseBody.getErrors().add(message);
            responseBody.getErrorCodes().add(String.valueOf(status.value()));
        }
        LOG.error("{} - Error Occured - URI : {} Error details : {}", status.value(), requestUri, responseBody.getErrors());
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseBody);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "The URL you have reached is not in service at this time (404).");
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var responseBody = new ErrorResponse();
        Set<String> errors = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        Set<String> errorCodes = errors
                .stream()
                .map(error -> error.contains(ERROR_CODE_SPLITTER) ? error.substring(error.lastIndexOf(ERROR_CODE_SPLITTER) + 1).trim() : error)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        errors = errors
                .stream()
                .map(error -> error.contains(ERROR_CODE_SPLITTER) ? error.substring(0, error.lastIndexOf(ERROR_CODE_SPLITTER)).trim() : error)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        responseBody.getErrors().addAll(errors);
        responseBody.getErrorCodes().addAll(errorCodes);
        return handleExceptionInternal(ex, responseBody, headers, HttpStatus.BAD_REQUEST, request);
    }
}
