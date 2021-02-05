
package com.backbase.campaignupload.rest.spec.v1.corporateoffers;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
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
    "offerText",
    "companyId",
    "approvalStatus",
    "id",
    "createdBy",
    "updatedBy"
})
public class Update implements AdditionalPropertiesAware
{

    @JsonProperty("title")
    private String title;
    @JsonProperty("logo")
    private String logo;
    @JsonProperty("offerText")
    private String offerText;
    @JsonProperty("companyId")
    private String companyId;
    @JsonProperty("approvalStatus")
    private String approvalStatus;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("updatedBy")
    private String updatedBy;
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

    public Update withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
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
     * @param logo
     *     The logo
     */
    @JsonProperty("logo")
    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Update withLogo(String logo) {
        this.logo = logo;
        return this;
    }

    /**
     * 
     * @return
     *     The offerText
     */
    @JsonProperty("offerText")
    public String getOfferText() {
        return offerText;
    }

    /**
     * 
     * @param offerText
     *     The offerText
     */
    @JsonProperty("offerText")
    public void setOfferText(String offerText) {
        this.offerText = offerText;
    }

    public Update withOfferText(String offerText) {
        this.offerText = offerText;
        return this;
    }

    /**
     * 
     * @return
     *     The companyId
     */
    @JsonProperty("companyId")
    public String getCompanyId() {
        return companyId;
    }

    /**
     * 
     * @param companyId
     *     The companyId
     */
    @JsonProperty("companyId")
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Update withCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    /**
     * 
     * @return
     *     The approvalStatus
     */
    @JsonProperty("approvalStatus")
    public String getApprovalStatus() {
        return approvalStatus;
    }

    /**
     * 
     * @param approvalStatus
     *     The approvalStatus
     */
    @JsonProperty("approvalStatus")
    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Update withApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
        return this;
    }

    /**
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public Update withId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The createdBy
     */
    @JsonProperty("createdBy")
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * 
     * @param createdBy
     *     The createdBy
     */
    @JsonProperty("createdBy")
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Update withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    /**
     * 
     * @return
     *     The updatedBy
     */
    @JsonProperty("updatedBy")
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * 
     * @param updatedBy
     *     The updatedBy
     */
    @JsonProperty("updatedBy")
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Update withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(title).append(logo).append(offerText).append(companyId).append(approvalStatus).append(id).append(createdBy).append(updatedBy).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Update) == false) {
            return false;
        }
        Update rhs = ((Update) other);
        return new EqualsBuilder().append(title, rhs.title).append(logo, rhs.logo).append(offerText, rhs.offerText).append(companyId, rhs.companyId).append(approvalStatus, rhs.approvalStatus).append(id, rhs.id).append(createdBy, rhs.createdBy).append(updatedBy, rhs.updatedBy).isEquals();
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
