package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true) // This will ignore any extra fields that are not defined in your model
public class ActiveEntriesResponse {

    private String statusCode; // Add this field to match the JSON
    private String title;
    private String message;
    private List<ResponseData> responseData;

    // Getters and setters
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

    public List<ResponseData> getResponseData() {
        return responseData;
    }

    public void setResponseData(List<ResponseData> responseData) {
        this.responseData = responseData;
    }
}
