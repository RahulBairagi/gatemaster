package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseData {

    @JsonProperty("id")
    private int id;

    @JsonProperty("gate_entries_request_id")
    private String gateEntriesRequestId;

    @JsonProperty("client_id")
    private int clientId;

    @JsonProperty("gate_id")
    private int gateId;

    @JsonProperty("vehicle_registration_number")
    private String vehicleRegistrationNumber;

    @JsonProperty("visitor_name")
    private String visitorName;

    @JsonProperty("visitor_mobile")
    private String visitorMobile;

    @JsonProperty("visitor_address")
    private String visitorAddress;

    @JsonProperty("visitor_type")
    private int visitorType;

    @JsonProperty("purpose")
    private String purpose;

    @JsonProperty("society_id")
    private Integer societyId;

    @JsonProperty("flat_id")
    private Integer flatId;

    @JsonProperty("entry_time")
    private String entryTime;

    @JsonProperty("entry_by")
    private int entryBy;

    @JsonProperty("exit_time")
    private String exitTime;

    @JsonProperty("exit_by")
    private Integer exitBy;

    @JsonProperty("created_type")
    private String createdType;

    @JsonProperty("modified_type")
    private String modifiedType;

    public int getId() {
        return id;
    }

    public String getGateEntriesRequestId() {
        return gateEntriesRequestId;
    }

    public int getClientId() {
        return clientId;
    }

    public int getGateId() {
        return gateId;
    }

    public String getVehicleRegistrationNumber() {
        return vehicleRegistrationNumber;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public String getVisitorMobile() {
        return visitorMobile;
    }

    public String getVisitorAddress() {
        return visitorAddress;
    }

    public int getVisitorType() {
        return visitorType;
    }

    public String getPurpose() {
        return purpose;
    }

    public Integer getSocietyId() {
        return societyId;
    }

    public Integer getFlatId() {
        return flatId;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public int getEntryBy() {
        return entryBy;
    }

    public String getExitTime() {
        return exitTime;
    }

    public Integer getExitBy() {
        return exitBy;
    }

    public String getCreatedType() {
        return createdType;
    }

    public String getModifiedType() {
        return modifiedType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

}
