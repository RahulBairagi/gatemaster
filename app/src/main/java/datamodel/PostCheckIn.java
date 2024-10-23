package datamodel;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.processing.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "gate_id",
        "vehicle_registration_number",
        "visitor_name",
        "visitor_mobile",
        "visitor_address",
        "visitor_type",
        "purpose"
})
@Generated("jsonschema2pojo")
public class PostCheckIn {

    @JsonProperty("gate_id")
    private String gateId;
    @JsonProperty("vehicle_registration_number")
    private String vehicleRegistrationNumber;
    @JsonProperty("visitor_name")
    private String visitorName;
    @JsonProperty("visitor_mobile")
    private String visitorMobile;
    @JsonProperty("visitor_address")
    private String visitorAddress;
    @JsonProperty("visitor_type")
    private String visitorType;
    @JsonProperty("purpose")
    private String purpose;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("gate_id")
    public String getGateId() {
        return gateId;
    }

    @JsonProperty("gate_id")
    public void setGateId(String gateId) {
        this.gateId = gateId;
    }

    @JsonProperty("vehicle_registration_number")
    public String getVehicleRegistrationNumber() {
        return vehicleRegistrationNumber;
    }

    @JsonProperty("vehicle_registration_number")
    public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
    }

    @JsonProperty("visitor_name")
    public String getVisitorName() {
        return visitorName;
    }

    @JsonProperty("visitor_name")
    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    @JsonProperty("visitor_mobile")
    public String getVisitorMobile() {
        return visitorMobile;
    }

    @JsonProperty("visitor_mobile")
    public void setVisitorMobile(String visitorMobile) {
        this.visitorMobile = visitorMobile;
    }

    @JsonProperty("visitor_address")
    public String getVisitorAddress() {
        return visitorAddress;
    }

    @JsonProperty("visitor_address")
    public void setVisitorAddress(String visitorAddress) {
        this.visitorAddress = visitorAddress;
    }

    @JsonProperty("visitor_type")
    public String getVisitorType() {
        return visitorType;
    }

    @JsonProperty("visitor_type")
    public void setVisitorType(String visitorType) {
        this.visitorType = visitorType;
    }

    @JsonProperty("purpose")
    public String getPurpose() {
        return purpose;
    }

    @JsonProperty("purpose")
    public void setPurpose(String purpose) {
        this.purpose = purpose;
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
