package com.project.group.rupp.dse.classtracking.models;

public class GetSignIn {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SignInData getData() {
        return data;
    }

    public void setData(SignInData data) {
        this.data = data;
    }

    private String message;
    private String status;
    private SignInData data;

}