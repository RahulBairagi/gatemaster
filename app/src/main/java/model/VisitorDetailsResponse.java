package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;

public class VisitorDetailsResponse {

    @SerializedName("statusCode")
    private String statusCode;

    @SerializedName("title")
    private String title;

    @SerializedName("message")
    private String message;

    @SerializedName("responseData")
    private VisitorData responseData;

    @SerializedName("error")
    private Object error;

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

    public VisitorData getResponseData() {
        return responseData;
    }

    public void setResponseData(VisitorData responseData) {
        this.responseData = responseData;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    // Inner class to represent the visitor data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VisitorData {

        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        @SerializedName("mobile")
        private String mobile;

        @SerializedName("address")
        private String address;

        @SerializedName("last_visited_on")
        private String last_visited_on;

        @SerializedName("email")
        private String email;

        @SerializedName("photo")
        private String photo;

        @SerializedName("status")
        private int status;

        @SerializedName("client_id")
        private int clientId;

        @SerializedName("created_type")
        private String createdType;

        @SerializedName("created_by")
        private int createdBy;

        @SerializedName("modified_type")
        private String modifiedType;

        @SerializedName("modified_by")
        private int modifiedBy;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("updated_at")
        private String updatedAt;

        // Getters and Setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLastVisitedOn() {
            return last_visited_on;
        }

        public void setLastVisitedOn(String lastVisitedOn) {
            this.last_visited_on = lastVisitedOn;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getClientId() {
            return clientId;
        }

        public void setClientId(int clientId) {
            this.clientId = clientId;
        }

        public String getCreatedType() {
            return createdType;
        }

        public void setCreatedType(String createdType) {
            this.createdType = createdType;
        }

        public int getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(int createdBy) {
            this.createdBy = createdBy;
        }

        public String getModifiedType() {
            return modifiedType;
        }

        public void setModifiedType(String modifiedType) {
            this.modifiedType = modifiedType;
        }

        public int getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(int modifiedBy) {
            this.modifiedBy = modifiedBy;
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
    }
}
