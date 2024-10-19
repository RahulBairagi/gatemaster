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
        "client_id",
        "employee_id",
        "name",
        "email",
        "mobile",
        "gender",
        "otp",
        "device_token",
        "device_id",
        "device_type",
        "latitude",
        "longitude",
        "current_location",
        "is_online",
        "status",
        "language",
        "picture",
        "country_id",
        "state_id",
        "city_id",
        "created_type",
        "created_by",
        "modified_type",
        "modified_by"
})
@Generated("jsonschema2pojo")
public class User {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("client_id")
    private Integer clientId;
    @JsonProperty("employee_id")
    private String employeeId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("mobile")
    private String mobile;
    @JsonProperty("gender")
    private Object gender;
    @JsonProperty("otp")
    private Object otp;
    @JsonProperty("device_token")
    private Object deviceToken;
    @JsonProperty("device_id")
    private Object deviceId;
    @JsonProperty("device_type")
    private Object deviceType;
    @JsonProperty("latitude")
    private Object latitude;
    @JsonProperty("longitude")
    private Object longitude;
    @JsonProperty("current_location")
    private Object currentLocation;
    @JsonProperty("is_online")
    private Integer isOnline;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("language")
    private String language;
    @JsonProperty("picture")
    private Object picture;
    @JsonProperty("country_id")
    private Integer countryId;
    @JsonProperty("state_id")
    private Integer stateId;
    @JsonProperty("city_id")
    private Integer cityId;
    @JsonProperty("created_type")
    private String createdType;
    @JsonProperty("created_by")
    private Integer createdBy;
    @JsonProperty("modified_type")
    private String modifiedType;
    @JsonProperty("modified_by")
    private Integer modifiedBy;
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

    @JsonProperty("client_id")
    public Integer getClientId() {
        return clientId;
    }

    @JsonProperty("client_id")
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @JsonProperty("employee_id")
    public String getEmployeeId() {
        return employeeId;
    }

    @JsonProperty("employee_id")
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("mobile")
    public String getMobile() {
        return mobile;
    }

    @JsonProperty("mobile")
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @JsonProperty("gender")
    public Object getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(Object gender) {
        this.gender = gender;
    }

    @JsonProperty("otp")
    public Object getOtp() {
        return otp;
    }

    @JsonProperty("otp")
    public void setOtp(Object otp) {
        this.otp = otp;
    }

    @JsonProperty("device_token")
    public Object getDeviceToken() {
        return deviceToken;
    }

    @JsonProperty("device_token")
    public void setDeviceToken(Object deviceToken) {
        this.deviceToken = deviceToken;
    }

    @JsonProperty("device_id")
    public Object getDeviceId() {
        return deviceId;
    }

    @JsonProperty("device_id")
    public void setDeviceId(Object deviceId) {
        this.deviceId = deviceId;
    }

    @JsonProperty("device_type")
    public Object getDeviceType() {
        return deviceType;
    }

    @JsonProperty("device_type")
    public void setDeviceType(Object deviceType) {
        this.deviceType = deviceType;
    }

    @JsonProperty("latitude")
    public Object getLatitude() {
        return latitude;
    }

    @JsonProperty("latitude")
    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    public Object getLongitude() {
        return longitude;
    }

    @JsonProperty("longitude")
    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("current_location")
    public Object getCurrentLocation() {
        return currentLocation;
    }

    @JsonProperty("current_location")
    public void setCurrentLocation(Object currentLocation) {
        this.currentLocation = currentLocation;
    }

    @JsonProperty("is_online")
    public Integer getIsOnline() {
        return isOnline;
    }

    @JsonProperty("is_online")
    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    @JsonProperty("status")
    public Integer getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    @JsonProperty("picture")
    public Object getPicture() {
        return picture;
    }

    @JsonProperty("picture")
    public void setPicture(Object picture) {
        this.picture = picture;
    }

    @JsonProperty("country_id")
    public Integer getCountryId() {
        return countryId;
    }

    @JsonProperty("country_id")
    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    @JsonProperty("state_id")
    public Integer getStateId() {
        return stateId;
    }

    @JsonProperty("state_id")
    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    @JsonProperty("city_id")
    public Integer getCityId() {
        return cityId;
    }

    @JsonProperty("city_id")
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}