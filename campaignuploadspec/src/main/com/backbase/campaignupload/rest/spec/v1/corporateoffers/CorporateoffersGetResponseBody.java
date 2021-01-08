
package com.backbase.campaignupload.rest.spec.v1.corporateoffers;

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
    "headers",
    "data"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class CorporateoffersGetResponseBody implements AdditionalPropertiesAware
{

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("headers")
    @Valid
    @NotNull
    private List<Header> headers = new ArrayList<Header>();
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("data")
    @Valid
    @NotNull
    private List<Object> data = new ArrayList<Object>();
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
     *     The headers
     */
    @JsonProperty("headers")
    public List<Header> getHeaders() {
        return headers;
    }

    /**
     * 
     * (Required)
     * 
     * @param headers
     *     The headers
     */
    @JsonProperty("headers")
    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public CorporateoffersGetResponseBody withHeaders(List<Header> headers) {
        this.headers = headers;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The data
     */
    @JsonProperty("data")
    public List<Object> getData() {
        return data;
    }

    /**
     * 
     * (Required)
     * 
     * @param data
     *     The data
     */
    @JsonProperty("data")
    public void setData(List<Object> data) {
        this.data = data;
    }

    public CorporateoffersGetResponseBody withData(List<Object> data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(headers).append(data).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CorporateoffersGetResponseBody) == false) {
            return false;
        }
        CorporateoffersGetResponseBody rhs = ((CorporateoffersGetResponseBody) other);
        return new EqualsBuilder().append(headers, rhs.headers).append(data, rhs.data).isEquals();
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
