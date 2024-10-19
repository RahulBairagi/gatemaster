package datamodel;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "USERID",
        "PASSWORD",
        "STORE"
})

public class UserDetailRequest{


    @JsonProperty("employee_id")
    private String employeeId;
    @JsonProperty("employee_pin")
    private String employeePin;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("employee_id")
    public String getEmployeeId() {
        return employeeId;
    }

    @JsonProperty("employee_id")
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @JsonProperty("employee_pin")
    public String getEmployeePin() {
        return employeePin;
    }

    @JsonProperty("employee_pin")
    public void setEmployeePin(String employeePin) {
        this.employeePin = employeePin;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}