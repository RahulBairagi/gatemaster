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
        "id",
        "gate_entries_request_id",
        "client_id",
        "gate_id",
        "vehicle_registration_number",
        "visitor_name",
        "visitor_mobile",
        "visitor_address",
        "visitor_type",
        "purpose",
        "society_id",
        "flat_id",
        "entry_time",
        "entry_by",
        "exit_time",
        "exit_by",
        "created_type",
        "modified_type",
        "created_at",
        "updated_at"
})
@Generated("jsonschema2pojo")

public class ResponseCheckoutData {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("gate_entries_request_id")
    private String gateEntriesRequestId;
    @JsonProperty("client_id")
    private Integer clientId;
    @JsonProperty("gate_id")
    private Integer gateId;
    @JsonProperty("vehicle_registration_number")
    private String vehicleRegistrationNumber;
    @JsonProperty("visitor_name")
    private String visitorName;
    @JsonProperty("visitor_mobile")
    private String visitorMobile;
    @JsonProperty("visitor_address")
    private String visitorAddress;
    @JsonProperty("visitor_type")
    private Integer visitorType;
    @JsonProperty("purpose")
    private String purpose;
    @JsonProperty("society_id")
    private Object societyId;
    @JsonProperty("flat_id")
    private Object flatId;
    @JsonProperty("entry_time")
    private String entryTime;
    @JsonProperty("entry_by")
    private Integer entryBy;
    @JsonProperty("exit_time")
    private String exitTime;
    @JsonProperty("exit_by")
    private Integer exitBy;
    @JsonProperty("created_type")
    private String createdType;
    @JsonProperty("modified_type")
    private String modifiedType;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("gate_entries_request_id")
    public String getGateEntriesRequestId() {
        return gateEntriesRequestId;
    }

    @JsonProperty("gate_entries_request_id")
    public void setGateEntriesRequestId(String gateEntriesRequestId) {
        this.gateEntriesRequestId = gateEntriesRequestId;
    }

    @JsonProperty("client_id")
    public Integer getClientId() {
        return clientId;
    }

    @JsonProperty("client_id")
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @JsonProperty("gate_id")
    public Integer getGateId() {
        return gateId;
    }

    @JsonProperty("gate_id")
    public void setGateId(Integer gateId) {
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
    public Integer getVisitorType() {
        return visitorType;
    }

    @JsonProperty("visitor_type")
    public void setVisitorType(Integer visitorType) {
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

    @JsonProperty("society_id")
    public Object getSocietyId() {
        return societyId;
    }

    @JsonProperty("society_id")
    public void setSocietyId(Object societyId) {
        this.societyId = societyId;
    }

    @JsonProperty("flat_id")
    public Object getFlatId() {
        return flatId;
    }

    @JsonProperty("flat_id")
    public void setFlatId(Object flatId) {
        this.flatId = flatId;
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

    @JsonProperty("exit_time")
    public String getExitTime() {
        return exitTime;
    }

    @JsonProperty("exit_time")
    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }

    @JsonProperty("exit_by")
    public Integer getExitBy() {
        return exitBy;
    }

    @JsonProperty("exit_by")
    public void setExitBy(Integer exitBy) {
        this.exitBy = exitBy;
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

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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