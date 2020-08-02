package com.mobileapi.mobileapi.ui.model.response;

public class OperationStatusModel {
    private String operationResult;
    private String operationName;

    /**
     * @return String return the operationResult
     */
    public String getOperationResult() {
        return operationResult;
    }

    /**
     * @param operationResult the operationResult to set
     */
    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    /**
     * @return String return the operationName
     */
    public String getOperationName() {
        return operationName;
    }

    /**
     * @param operationName the operationName to set
     */
    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

}