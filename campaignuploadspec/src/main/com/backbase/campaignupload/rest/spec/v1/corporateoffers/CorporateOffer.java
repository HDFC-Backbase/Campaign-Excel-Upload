
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
	"live",
	"createdBy",
	"updatedBy"
})
public class CorporateOffer implements AdditionalPropertiesAware
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
    @JsonProperty("offerText")
    @NotNull
    private String offertext;
    @JsonProperty("approvalStatus")
    private String approvalstatus;
	
	 @JsonProperty("companyId")
    private String companyid;
	
	@JsonProperty("id")
	@NotNull
    private Integer id;
    /**
     * Additional Properties
     * 
     */
	@JsonProperty("live")
	private boolean live;
	
	@JsonProperty("createdBy")
	private String createdBy;
	
    @JsonProperty("updatedBy")
	private String updatedBy;
	
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> additions = new HashMap<String, String>();

    /**
     * 
     * @return
     *     The title
     */
  
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
 
    public void setTitle(String title) {
        this.title = title;
    }

    public CorporateOffer withTitle(String title) {
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

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public CorporateOffer withLogo(String logo) {
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

    public void setOffertext(String offertext) {
        this.offertext = offertext;
    }
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

    public CorporateOffer withOffertext(String offertext) {
        this.offertext = offertext;
        return this;
    }

    /**
     * 
     * @return
     *     The approvalstatus
     */

    public String getApprovalstatus() {
        return approvalstatus;
    }

    /**
     * 
     * @param approvalstatus
     *     The approvalstatus
     */

    public void setApprovalstatus(String approvalstatus) {
        this.approvalstatus = approvalstatus;
    }

    public CorporateOffer withApprovalstatus(String approvalstatus) {
        this.approvalstatus = approvalstatus;
        return this;
    }

 /**
     * 
     * @return
     *     The companyid
     */
    public String getCompanyid() {
        return companyid;
    }

    /**
     * 
     * @param companyid
     *     The companyid
     */
    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public CorporateOffer withCompanyid(String companyid) {
        this.companyid = companyid;
        return this;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(title).append(logo).append(offertext).append(approvalstatus).append(companyid).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CorporateOffer) == false) {
            return false;
        }
        CorporateOffer rhs = ((CorporateOffer) other);
        return new EqualsBuilder().append(title, rhs.title).append(logo, rhs.logo).append(offertext, rhs.offertext).append(approvalstatus, rhs.approvalstatus).append(companyid, rhs.companyid).isEquals();
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
