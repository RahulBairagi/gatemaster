package model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "statusCode",
        "title",
        "message",
        "responseData",
        "error"
})
public class GetGatesResponse {

    @JsonProperty("statusCode")
    private String statusCode;
    @JsonProperty("title")
    private String title;
    @JsonProperty("message")
    private String message;
    @JsonProperty("responseData")
    private List<GateResponseData> responseData;
    @JsonProperty("error")
    private List<Object> error;

    @JsonProperty("statusCode")
    public String getStatusCode() {
        return statusCode;
    }

    @JsonProperty("statusCode")
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("responseData")
    public List<GateResponseData> getResponseData() {
        return responseData;
    }

    @JsonProperty("responseData")
    public void setResponseData(List<GateResponseData> responseData) {
        this.responseData = responseData;
    }

    @JsonProperty("error")
    public List<Object> getError() {
        return error;
    }

    @JsonProperty("error")
    public void setError(List<Object> error) {
        this.error = error;
    }

}