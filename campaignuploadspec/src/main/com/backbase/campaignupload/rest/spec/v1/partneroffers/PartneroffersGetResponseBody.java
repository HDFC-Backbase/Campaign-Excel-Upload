
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
    "partners"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class PartneroffersGetResponseBody implements AdditionalPropertiesAware
{

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("partners")
    @Valid
    @NotNull
    private List<Partner> partners = new ArrayList<Partner>();
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
     *     The partners
     */
    @JsonProperty("partners")
    public List<Partner> getPartners() {
        return partners;
    }

    /**
     * 
     * (Required)
     * 
     * @param partners
     *     The partners
     */
    @JsonProperty("partners")
    public void setPartners(List<Partner> partners) {
        this.partners = partners;
    }

    public PartneroffersGetResponseBody withPartners(List<Partner> partners) {
        this.partners = partners;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(partners).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PartneroffersGetResponseBody) == false) {
            return false;
        }
        PartneroffersGetResponseBody rhs = ((PartneroffersGetResponseBody) other);
        return new EqualsBuilder().append(partners, rhs.partners).isEquals();
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
