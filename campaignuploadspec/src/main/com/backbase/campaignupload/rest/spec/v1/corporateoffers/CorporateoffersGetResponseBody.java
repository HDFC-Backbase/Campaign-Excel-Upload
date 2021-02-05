
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
    "corporates"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class CorporateoffersGetResponseBody implements AdditionalPropertiesAware
{

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("corporates")
    @Valid
    @NotNull
    private List<Corporate> corporates = new ArrayList<Corporate>();
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
     *     The corporates
     */
    @JsonProperty("corporates")
    public List<Corporate> getCorporates() {
        return corporates;
    }

    /**
     * 
     * (Required)
     * 
     * @param corporates
     *     The corporates
     */
    @JsonProperty("corporates")
    public void setCorporates(List<Corporate> corporates) {
        this.corporates = corporates;
    }

    public CorporateoffersGetResponseBody withCorporates(List<Corporate> corporates) {
        this.corporates = corporates;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(corporates).toHashCode();
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
        return new EqualsBuilder().append(corporates, rhs.corporates).isEquals();
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
