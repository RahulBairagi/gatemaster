package model;

import java.util.List;

public class LogoutResponse {

    private String statusCode;
    private String title;
    private String message;
    private List<Object> responseData;
    private List<Object> error;

    // Getters and Setters
    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Object> getResponseData() {
        return responseData;
    }

    public void setResponseData(List<Object> responseData) {
        this.responseData = responseData;
    }

    public List<Object> getError() {
        return error;
    }

    public void setError(List<Object> error) {
        this.error = error;
    }
}
