
package com.backbase.campaignupload.rest.spec.v1.corporateoffers;

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
    "offerText",
    "companyId",
    "approvalStatus",
    "id",
    "createdBy",
    "updatedBy"
})
public class Coporate implements AdditionalPropertiesAware
{

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("title")
    @NotNull
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
    @JsonProperty("offerText")
    @NotNull
    private String offerText;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("companyId")
    @NotNull
    private String companyId;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("approvalStatus")
    @NotNull
    private String approvalStatus;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("id")
    @NotNull
    private Integer id;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("createdBy")
    @NotNull
    private String createdBy;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("updatedBy")
    @NotNull
    private String updatedBy;
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
     *     The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * 
     * (Required)
     * 
     * @param title
     *     The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    public Coporate withTitle(String title) {
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

    public Coporate withLogo(String logo) {
        this.logo = logo;
        return this;
    }

    /**
     * 
     * (Required)
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
     * (Required)
     * 
     * @param offerText
     *     The offerText
     */
    @JsonProperty("offerText")
    public void setOfferText(String offerText) {
        this.offerText = offerText;
    }

    public Coporate withOfferText(String offerText) {
        this.offerText = offerText;
        return this;
    }

    /**
     * 
     * (Required)
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
     * (Required)
     * 
     * @param companyId
     *     The companyId
     */
    @JsonProperty("companyId")
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Coporate withCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    /**
     * 
     * (Required)
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
     * (Required)
     * 
     * @param approvalStatus
     *     The approvalStatus
     */
    @JsonProperty("approvalStatus")
    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Coporate withApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
        return this;
    }

    /**
     * 
     * (Required)
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
     * (Required)
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public Coporate withId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * (Required)
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
     * (Required)
     * 
     * @param createdBy
     *     The createdBy
     */
    @JsonProperty("createdBy")
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Coporate withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    /**
     * 
     * (Required)
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
     * (Required)
     * 
     * @param updatedBy
     *     The updatedBy
     */
    @JsonProperty("updatedBy")
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Coporate withUpdatedBy(String updatedBy) {
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
        if ((other instanceof Coporate) == false) {
            return false;
        }
        Coporate rhs = ((Coporate) other);
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
