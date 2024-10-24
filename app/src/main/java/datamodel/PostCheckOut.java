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
        "gate_entries_request_id"
})
@Generated("jsonschema2pojo")
public class PostCheckOut {

    @JsonProperty("gate_entries_request_id")
    private String gateEntriesRequestId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("gate_entries_request_id")
    public String getGateEntriesRequestId() {
        return gateEntriesRequestId;
    }

    @JsonProperty("gate_entries_request_id")
    public void setGateEntriesRequestId(String gateEntriesRequestId) {
        this.gateEntriesRequestId = gateEntriesRequestId;
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
