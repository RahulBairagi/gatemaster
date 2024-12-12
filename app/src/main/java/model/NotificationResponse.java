package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationResponse {

    @JsonProperty("statusCode")
    private String statusCode;

    @JsonProperty("title")
    private String title;

    @JsonProperty("message")
    private String message;

    @JsonProperty("responseData")
    private List<ResponseData> responseData;

    @JsonProperty("error")
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

    public List<ResponseData> getResponseData() {
        return responseData;
    }

    public void setResponseData(List<ResponseData> responseData) {
        this.responseData = responseData;
    }

    public List<Object> getError() {
        return error;
    }

    public void setError(List<Object> error) {
        this.error = error;
    }

    public static class ResponseData {
        @JsonProperty("id")
        private int id;

        @JsonProperty("title")
        private String title;

        @JsonProperty("descriptions")
        private String descriptions;

        @JsonProperty("expiry_date")
        private String expiry_date;

        @JsonProperty("notify_type")
        private String notify_type;

        @JsonProperty("status")
        private String status;

        @JsonProperty("guard_id")
        private String guard_id;

        @JsonProperty("client_id")
        private int client_id;

        @JsonProperty("created_type")
        private String created_type;

        @JsonProperty("created_by")
        private int created_by;

        @JsonProperty("modified_type")
        private String modified_type;

        @JsonProperty("modified_by")
        private int modified_by;

        @JsonProperty("created_at")
        private String created_at;

        @JsonProperty("updated_at")
        private String updated_at;

        @JsonProperty("expiry_time")
        private String expiry_time;

        // Getters and Setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescriptions() {
            return descriptions;
        }

        public void setDescriptions(String descriptions) {
            this.descriptions = descriptions;
        }

        public String getExpiryDate() {
            return expiry_date;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiry_date = expiryDate;
        }

        public String getNotifyType() {
            return notify_type;
        }

        public void setNotifyType(String notifyType) {
            this.notify_type = notifyType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getGuardId() {
            return guard_id;
        }

        public void setGuardId(String guardId) {
            this.guard_id = guardId;
        }

        public int getClientId() {
            return client_id;
        }

        public void setClientId(int clientId) {
            this.client_id = clientId;
        }

        public String getCreatedType() {
            return created_type;
        }

        public void setCreatedType(String createdType) {
            this.created_type = createdType;
        }

        public int getCreatedBy() {
            return created_by;
        }

        public void setCreatedBy(int createdBy) {
            this.created_by = createdBy;
        }

        public String getModifiedType() {
            return modified_type;
        }

        public void setModifiedType(String modifiedType) {
            this.modified_type = modifiedType;
        }

        public int getModifiedBy() {
            return modified_by;
        }

        public void setModifiedBy(int modifiedBy) {
            this.modified_by = modifiedBy;
        }

        public String getCreatedAt() {
            return created_at;
        }

        public void setCreatedAt(String createdAt) {
            this.created_at = createdAt;
        }

        public String getUpdatedAt() {
            return updated_at;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updated_at = updatedAt;
        }

        public String getExpiryTime() {
            return expiry_time;
        }

        public void setExpiryTime(String expiryTime) {
            this.expiry_time = expiryTime;
        }
    }
}
