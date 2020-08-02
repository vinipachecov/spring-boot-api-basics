package com.mobileapi.mobileapi.ui.model.response;

public enum ErrorMessages {
    MISSING_REQUIRED_FIELD("Missing required filed. Please check documentation for required fields"),
    RECORD_ALREADY_EXISTS("Record already exists."), INTERNAL_SERVER_ERROR("Internal server error."),
    NO_RECORD_FOUND("Record with provided data is not found"), AUTHENTICATION_FAIELD("Authentication failed."),
    COULD_NOT_UPDATE_RECORD("Could not update record"), COULD_NOT_DELETE_RECORD("Could not delete record"),
    EMAIL_ADDRESS_NOT_VERIFIED("Could not update record");

    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return String return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}