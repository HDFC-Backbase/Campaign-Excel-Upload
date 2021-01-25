
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
    "headerName",
    "field",
    "sortable",
    "filter",
    "editable",
    "hide",
    "type"
})
public class Header implements AdditionalPropertiesAware
{

    @JsonProperty("headerName")
    private String headerName;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("field")
    @NotNull
    private String field;
    @JsonProperty("sortable")
    private Boolean sortable;
    @JsonProperty("filter")
    private Boolean filter;
    @JsonProperty("editable")
    private Boolean editable;
    @JsonProperty("hide")
    private Boolean hide;
    @JsonProperty("type")
    private String type;
    /**
     * Additional Properties
     * 
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> additions = new HashMap<String, String>();

    /**
     * 
     * @return
     *     The headerName
     */
    @JsonProperty("headerName")
    public String getHeaderName() {
        return headerName;
    }

    /**
     * 
     * @param headerName
     *     The headerName
     */
    @JsonProperty("headerName")
    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public Header withHeaderName(String headerName) {
        this.headerName = headerName;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The field
     */
    @JsonProperty("field")
    public String getField() {
        return field;
    }

    /**
     * 
     * (Required)
     * 
     * @param field
     *     The field
     */
    @JsonProperty("field")
    public void setField(String field) {
        this.field = field;
    }

    public Header withField(String field) {
        this.field = field;
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
     *     The filter
     */
    @JsonProperty("filter")
    public Boolean getFilter() {
        return filter;
    }

    /**
     * 
     * @param filter
     *     The filter
     */
    @JsonProperty("filter")
    public void setFilter(Boolean filter) {
        this.filter = filter;
    }

    public Header withFilter(Boolean filter) {
        this.filter = filter;
        return this;
    }

    /**
     * 
     * @return
     *     The editable
     */
    @JsonProperty("editable")
    public Boolean getEditable() {
        return editable;
    }

    /**
     * 
     * @param editable
     *     The editable
     */
    @JsonProperty("editable")
    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Header withEditable(Boolean editable) {
        this.editable = editable;
        return this;
    }

    /**
     * 
     * @return
     *     The hide
     */
    @JsonProperty("hide")
    public Boolean getHide() {
        return hide;
    }

    /**
     * 
     * @param hide
     *     The hide
     */
    @JsonProperty("hide")
    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public Header withHide(Boolean hide) {
        this.hide = hide;
        return this;
    }

    /**
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public Header withType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(headerName).append(field).append(sortable).append(filter).append(editable).append(hide).append(type).toHashCode();
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
        return new EqualsBuilder().append(headerName, rhs.headerName).append(field, rhs.field).append(sortable, rhs.sortable).append(filter, rhs.filter).append(editable, rhs.editable).append(hide, rhs.hide).append(type, rhs.type).isEquals();
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
