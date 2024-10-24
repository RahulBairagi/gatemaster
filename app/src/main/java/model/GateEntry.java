package model;

public class GateEntry {

    private String vehicleRegistrationNumber;
    private String visitorName;
    private String visitorMobile;
    private String visitorAddress;
    private String purpose;
    private String entryTime;
    private String createdType;
    private String modifiedType;
    private String createdAt;
    private String updatedAt;
    private String GateReqID;


    public GateEntry(String vehicleRegistrationNumber, String visitorName, String visitorMobile,
                     String visitorAddress, String purpose, String entryTime,
                     String createdType, String modifiedType, String createdAt, String updatedAt, String GateReqID) {
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
        this.visitorName = visitorName;
        this.visitorMobile = visitorMobile;
        this.visitorAddress = visitorAddress;
        this.purpose = purpose;
        this.entryTime = entryTime;
        this.createdType = createdType;
        this.modifiedType = modifiedType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.GateReqID = GateReqID;
    }

    // Getters and setters for each field
    public String getVehicleRegistrationNumber() {
        return vehicleRegistrationNumber;
    }

    public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitorMobile() {
        return visitorMobile;
    }

    public void setVisitorMobile(String visitorMobile) {
        this.visitorMobile = visitorMobile;
    }

    public String getVisitorAddress() {
        return visitorAddress;
    }

    public void setVisitorAddress(String visitorAddress) {
        this.visitorAddress = visitorAddress;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getCreatedType() {
        return createdType;
    }

    public void setCreatedType(String createdType) {
        this.createdType = createdType;
    }

    public String getModifiedType() {
        return modifiedType;
    }

    public void setModifiedType(String modifiedType) {
        this.modifiedType = modifiedType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


    public String getGateReqID() {
        return GateReqID;
    }

    public void setGateReqID(String gateReqID) {
        this.GateReqID = gateReqID;
    }
}
