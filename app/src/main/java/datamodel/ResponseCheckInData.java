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
        "purpose",
        "client_id",
        "gate_entries_request_id",
        "entry_time",
        "entry_by",
        "created_type",
        "modified_type",
        "updated_at",
        "created_at",
        "id"
})
@Generated("jsonschema2pojo")
public class ResponseCheckInData {

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
    @JsonProperty("client_id")
    private Integer clientId;
    @JsonProperty("gate_entries_request_id")
    private String gateEntriesRequestId;
    @JsonProperty("entry_time")
    private String entryTime;
    @JsonProperty("entry_by")
    private Integer entryBy;
    @JsonProperty("created_type")
    private String createdType;
    @JsonProperty("modified_type")
    private String modifiedType;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("id")
    private Integer id;
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

    @JsonProperty("client_id")
    public Integer getClientId() {
        return clientId;
    }

    @JsonProperty("client_id")
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @JsonProperty("gate_entries_request_id")
    public String getGateEntriesRequestId() {
        return gateEntriesRequestId;
    }

    @JsonProperty("gate_entries_request_id")
    public void setGateEntriesRequestId(String gateEntriesRequestId) {
        this.gateEntriesRequestId = gateEntriesRequestId;
    }

    @JsonProperty("entry_time")
    public String getEntryTime() {
        return entryTime;
    }

    @JsonProperty("entry_time")
    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    @JsonProperty("entry_by")
    public Integer getEntryBy() {
        return entryBy;
    }

    @JsonProperty("entry_by")
    public void setEntryBy(Integer entryBy) {
        this.entryBy = entryBy;
    }

    @JsonProperty("created_type")
    public String getCreatedType() {
        return createdType;
    }

    @JsonProperty("created_type")
    public void setCreatedType(String createdType) {
        this.createdType = createdType;
    }

    @JsonProperty("modified_type")
    public String getModifiedType() {
        return modifiedType;
    }

    @JsonProperty("modified_type")
    public void setModifiedType(String modifiedType) {
        this.modifiedType = modifiedType;
    }

    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
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