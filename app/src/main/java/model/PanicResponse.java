package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PanicResponse {

    @JsonProperty("statusCode")
    private String statusCode;

    @JsonProperty("title")
    private String title;

    @JsonProperty("message")
    private String message;

    @JsonProperty("responseData")
    private List<Object> responseData; // Replace Object with the specific type if needed

    @JsonProperty("error")
    private List<Object> error; // Replace Object with the specific type if needed

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

