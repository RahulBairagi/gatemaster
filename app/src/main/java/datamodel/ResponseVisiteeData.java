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
        "name",
        "mobile",
        "address",
        "last_visited_on",
        "email",
        "photo",
        "status",
        "client_id",
        "created_type",
        "created_by",
        "modified_type",
        "modified_by",
        "created_at",
        "updated_at"
})
@Generated("jsonschema2pojo")
public class ResponseVisiteeData {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("mobile")
    private String mobile;
    @JsonProperty("address")
    private String address;
    @JsonProperty("last_visited_on")
    private String lastVisitedOn;
    @JsonProperty("email")
    private Object email;
    @JsonProperty("photo")
    private Object photo;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("client_id")
    private Integer clientId;
    @JsonProperty("created_type")
    private String createdType;
    @JsonProperty("created_by")
    private Integer createdBy;
    @JsonProperty("modified_type")
    private String modifiedType;
    @JsonProperty("modified_by")
    private Integer modifiedBy;
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

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("mobile")
    public String getMobile() {
        return mobile;
    }

    @JsonProperty("mobile")
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("last_visited_on")
    public String getLastVisitedOn() {
        return lastVisitedOn;
    }

    @JsonProperty("last_visited_on")
    public void setLastVisitedOn(String lastVisitedOn) {
        this.lastVisitedOn = lastVisitedOn;
    }

    @JsonProperty("email")
    public Object getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(Object email) {
        this.email = email;
    }

    @JsonProperty("photo")
    public Object getPhoto() {
        return photo;
    }

    @JsonProperty("photo")
    public void setPhoto(Object photo) {
        this.photo = photo;
    }

    @JsonProperty("status")
    public Integer getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonProperty("client_id")
    public Integer getClientId() {
        return clientId;
    }

    @JsonProperty("client_id")
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @JsonProperty("created_type")
    public String getCreatedType() {
        return createdType;
    }

    @JsonProperty("created_type")
    public void setCreatedType(String createdType) {
        this.createdType = createdType;
    }

    @JsonProperty("created_by")
    public Integer getCreatedBy() {
        return createdBy;
    }

    @JsonProperty("created_by")
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @JsonProperty("modified_type")
    public String getModifiedType() {
        return modifiedType;
    }

    @JsonProperty("modified_type")
    public void setModifiedType(String modifiedType) {
        this.modifiedType = modifiedType;
    }

    @JsonProperty("modified_by")
    public Integer getModifiedBy() {
        return modifiedBy;
    }

    @JsonProperty("modified_by")
    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
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
