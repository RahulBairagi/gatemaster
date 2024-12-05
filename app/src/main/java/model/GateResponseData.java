package model;

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
        "gate_id",
        "location",
        "entry_type",
        "status",
        "client_id",
        "created_type",
        "modified_type",
        "created_at",
        "updated_at"
})
@Generated("jsonschema2pojo")
public class GateResponseData {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("gate_id")
    private String gateId;
    @JsonProperty("location")
    private String location;
    @JsonProperty("entry_type")
    private String entryType;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("client_id")
    private Integer clientId;
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

    @JsonProperty("gate_id")
    public String getGateId() {
        return gateId;
    }

    @JsonProperty("gate_id")
    public void setGateId(String gateId) {
        this.gateId = gateId;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("entry_type")
    public String getEntryType() {
        return entryType;
    }

    @JsonProperty("entry_type")
    public void setEntryType(String entryType) {
        this.entryType = entryType;
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
