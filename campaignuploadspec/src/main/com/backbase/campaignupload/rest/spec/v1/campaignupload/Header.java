
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
    "id",
    "sortable",
    "checkbox",
    "searchable",
    "img",
    "link"
})
public class Header implements AdditionalPropertiesAware
{

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("id")
    @NotNull
    private String id;
    @JsonProperty("sortable")
    private Boolean sortable;
    @JsonProperty("checkbox")
    private Boolean checkbox;
    @JsonProperty("searchable")
    private Boolean searchable;
    @JsonProperty("img")
    private Boolean img;
    @JsonProperty("link")
    private Boolean link;
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
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
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
    public void setId(String id) {
        this.id = id;
    }

    public Header withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The sortable
     */
    @JsonProperty("sortable")
    public Boolean getSortable() {
        return sortable;
    }

    /**
     * 
     * @param sortable
     *     The sortable
     */
    @JsonProperty("sortable")
    public void setSortable(Boolean sortable) {
        this.sortable = sortable;
    }

    public Header withSortable(Boolean sortable) {
        this.sortable = sortable;
        return this;
    }

    /**
     * 
     * @return
     *     The checkbox
     */
    @JsonProperty("checkbox")
    public Boolean getCheckbox() {
        return checkbox;
    }

    /**
     * 
     * @param checkbox
     *     The checkbox
     */
    @JsonProperty("checkbox")
    public void setCheckbox(Boolean checkbox) {
        this.checkbox = checkbox;
    }

    public Header withCheckbox(Boolean checkbox) {
        this.checkbox = checkbox;
        return this;
    }

    /**
     * 
     * @return
     *     The searchable
     */
    @JsonProperty("searchable")
    public Boolean getSearchable() {
        return searchable;
    }

    /**
     * 
     * @param searchable
     *     The searchable
     */
    @JsonProperty("searchable")
    public void setSearchable(Boolean searchable) {
        this.searchable = searchable;
    }

    public Header withSearchable(Boolean searchable) {
        this.searchable = searchable;
        return this;
    }

    /**
     * 
     * @return
     *     The img
     */
    @JsonProperty("img")
    public Boolean getImg() {
        return img;
    }

    /**
     * 
     * @param img
     *     The img
     */
    @JsonProperty("img")
    public void setImg(Boolean img) {
        this.img = img;
    }

    public Header withImg(Boolean img) {
        this.img = img;
        return this;
    }

    /**
     * 
     * @return
     *     The link
     */
    @JsonProperty("link")
    public Boolean getLink() {
        return link;
    }

    /**
     * 
     * @param link
     *     The link
     */
    @JsonProperty("link")
    public void setLink(Boolean link) {
        this.link = link;
    }

    public Header withLink(Boolean link) {
        this.link = link;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(sortable).append(checkbox).append(searchable).append(img).append(link).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Header) == false) {
            return false;
        }
        Header rhs = ((Header) other);
        return new EqualsBuilder().append(id, rhs.id).append(sortable, rhs.sortable).append(checkbox, rhs.checkbox).append(searchable, rhs.searchable).append(img, rhs.img).append(link, rhs.link).isEquals();
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
