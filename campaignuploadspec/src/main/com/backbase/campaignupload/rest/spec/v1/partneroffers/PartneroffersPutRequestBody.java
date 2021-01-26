
package com.backbase.campaignupload.rest.spec.v1.partneroffers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.backbase.buildingblocks.persistence.model.AdditionalPropertiesAware;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "updates"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class PartneroffersPutRequestBody implements AdditionalPropertiesAware
{

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("updates")
    @Valid
    @NotNull
    private List<Object> updates = new ArrayList<Object>();
    /**
     * Additional Properties
     * 
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> additions = new HashMap<String, String>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The updates
     */
    @JsonProperty("updates")
    public List<Object> getUpdates() {
        return updates;
    }

    /**
     * 
     * (Required)
     * 
     * @param updates
     *     The updates
     */
    @JsonProperty("updates")
    public void setUpdates(List<Object> updates) {
        this.updates = updates;
    }

    public PartneroffersPutRequestBody withUpdates(List<Object> updates) {
        this.updates = updates;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(updates).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PartneroffersPutRequestBody) == false) {
            return false;
        }
        PartneroffersPutRequestBody rhs = ((PartneroffersPutRequestBody) other);
        return new EqualsBuilder().append(updates, rhs.updates).isEquals();
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    @JsonProperty("additions")
    public Map<String, String> getAdditions() {
        return this.additions;
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    @JsonProperty("additions")
    public void setAdditions(Map<String, String> additions) {
        this.additions = additions;
    }

}
