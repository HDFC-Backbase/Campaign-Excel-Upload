
package com.backbase.campaignupload.rest.spec.v1.corporateoffers;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
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
    "statuscode",
    "message"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class CorporateoffersPutResponseBody implements AdditionalPropertiesAware
{

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("statuscode")
    @NotNull
    private Integer statuscode;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("message")
    @NotNull
    private String message;
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
     *     The statuscode
     */
    @JsonProperty("statuscode")
    public Integer getStatuscode() {
        return statuscode;
    }

    /**
     * 
     * (Required)
     * 
     * @param statuscode
     *     The statuscode
     */
    @JsonProperty("statuscode")
    public void setStatuscode(Integer statuscode) {
        this.statuscode = statuscode;
    }

    public CorporateoffersPutResponseBody withStatuscode(Integer statuscode) {
        this.statuscode = statuscode;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The message
     */
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    /**
     * 
     * (Required)
     * 
     * @param message
     *     The message
     */
    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    public CorporateoffersPutResponseBody withMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(statuscode).append(message).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CorporateoffersPutResponseBody) == false) {
            return false;
        }
        CorporateoffersPutResponseBody rhs = ((CorporateoffersPutResponseBody) other);
        return new EqualsBuilder().append(statuscode, rhs.statuscode).append(message, rhs.message).isEquals();
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