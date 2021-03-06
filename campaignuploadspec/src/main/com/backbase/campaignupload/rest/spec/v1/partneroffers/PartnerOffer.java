
package com.backbase.campaignupload.rest.spec.v1.partneroffers;

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
    "title",
    "logo",
    "offertext",
    "approvalstatus"
})
public class PartnerOffer implements AdditionalPropertiesAware
{

    @JsonProperty("title")
    private String title;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("logo")
    @NotNull
    private String logo;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("offertext")
    @NotNull
    private String offertext;
    @JsonProperty("approvalstatus")
    private String approvalstatus;
    /**
     * Additional Properties
     * 
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> additions = new HashMap<String, String>();

    /**
     * 
     * @return
     *     The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    public PartnerOffer withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The logo
     */
    @JsonProperty("logo")
    public String getLogo() {
        return logo;
    }

    /**
     * 
     * (Required)
     * 
     * @param logo
     *     The logo
     */
    @JsonProperty("logo")
    public void setLogo(String logo) {
        this.logo = logo;
    }

    public PartnerOffer withLogo(String logo) {
        this.logo = logo;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The offertext
     */
    @JsonProperty("offertext")
    public String getOffertext() {
        return offertext;
    }

    /**
     * 
     * (Required)
     * 
     * @param offertext
     *     The offertext
     */
    @JsonProperty("offertext")
    public void setOffertext(String offertext) {
        this.offertext = offertext;
    }

    public PartnerOffer withOffertext(String offertext) {
        this.offertext = offertext;
        return this;
    }

    /**
     * 
     * @return
     *     The approvalstatus
     */
    @JsonProperty("approvalstatus")
    public String getApprovalstatus() {
        return approvalstatus;
    }

    /**
     * 
     * @param approvalstatus
     *     The approvalstatus
     */
    @JsonProperty("approvalstatus")
    public void setApprovalstatus(String approvalstatus) {
        this.approvalstatus = approvalstatus;
    }

    public PartnerOffer withApprovalstatus(String approvalstatus) {
        this.approvalstatus = approvalstatus;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(title).append(logo).append(offertext).append(approvalstatus).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PartnerOffer) == false) {
            return false;
        }
        PartnerOffer rhs = ((PartnerOffer) other);
        return new EqualsBuilder().append(title, rhs.title).append(logo, rhs.logo).append(offertext, rhs.offertext).append(approvalstatus, rhs.approvalstatus).isEquals();
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
