package com.backbase.campaignupload.pojo;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.backbase.campaignupload.rest.spec.v1.corporateoffers.CorporateOffer;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CorporateOfferPutRequest {
	@JsonProperty("updates")
    @Valid
    @NotNull
    private List<CorporateOffer> updates;

	public List<CorporateOffer> getUpdates() {
		return updates;
	}

	public void setUpdates(List<CorporateOffer> updates) {
		this.updates = updates;
	}

	@Override
	public String toString() {
		return "CompanyPutRequest [updates=" + updates + "]";
	}

}
