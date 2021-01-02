
package com.backbase.campaignupload.rest.spec.v1.campaignupload;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import com.backbase.buildingblocks.persistence.model.AdditionalPropertiesAware;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "header",
    "logo",
    "offertext",
    "approvalstatus"
})
public class Campaign implements AdditionalPropertiesAware
{

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("header")
    @NotNull
    private String header;
    @JsonProperty("logo")
    private String logo;
    @JsonProperty("offertext")
    private String offertext;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("approvalstatus")
    @NotNull
    private String approvalstatus;

	 
	@JsonProperty("header")
    public String getHeader() {
        return header;
    }


    @JsonProperty("header")
    public void setHeader(String header) {
        this.header = header;
    }
	
	@JsonProperty("logo")
    public String getLogo() {
        return logo;
    }

    @JsonProperty("logo")
    public void setLogo(String logo) {
        this.logo = logo;
    }
	@JsonProperty("offertext")
    public String getoffertext() {
        return offertext;
    }

    @JsonProperty("offertext")
    public void setoffertext(String offertext) {
        this.offertext = offertext;
    }
	/**
     * Additional Properties
     * 
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> additions = new HashMap<String, String>();

    @JsonProperty("approvalstatus")
    public String getApprovalstatus() {
        return approvalstatus;
    }

    /**
     * 
     * (Required)
     * 
     * @param approvalstatus
     *     The approvalstatus
     */
    @JsonProperty("approvalstatus")
    public void setApprovalstatus(String approvalstatus) {
        this.approvalstatus = approvalstatus;
    }

    public Campaign withApprovalstatus(String approvalstatus) {
        this.approvalstatus = approvalstatus;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(header).append(logo).append(offertext).append(approvalstatus).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Campaign) == false) {
            return false;
        }
        Campaign rhs = ((Campaign) other);
        return new EqualsBuilder().append(header, rhs.header).append(logo, rhs.logo).append(offertext, rhs.offertext).append(approvalstatus, rhs.approvalstatus).isEquals();
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
