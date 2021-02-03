package com.backbase.campaignupload.pojo;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.backbase.campaignupload.rest.spec.v1.partneroffers.PartnerOffer;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PartnerOfferPutRequest {
	@JsonProperty("updates")
    @Valid
    @NotNull
    private List<PartnerOffer> updates;

	public List<PartnerOffer> getUpdates() {
		return updates;
	}

	public void setUpdates(List<PartnerOffer> updates) {
		this.updates = updates;
	}

	@Override
	public String toString() {
		return "CompanyPutRequest [updates=" + updates + "]";
	}

}
