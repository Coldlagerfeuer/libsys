
package isdndb.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "index_searched",
    "data"
})
public class ResponseIsdnDb {

    @JsonProperty("index_searched")
    private String indexSearched;
    @JsonProperty("data")
    private List<BookData> data = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseIsdnDb() {
    }

    /**
     * 
     * @param indexSearched
     * @param data
     */
    public ResponseIsdnDb(String indexSearched, List<BookData> data) {
        super();
        this.indexSearched = indexSearched;
        this.data = data;
    }

    @JsonProperty("index_searched")
    public String getIndexSearched() {
        return indexSearched;
    }

    @JsonProperty("index_searched")
    public void setIndexSearched(String indexSearched) {
        this.indexSearched = indexSearched;
    }

    @JsonProperty("data")
    public List<BookData> getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(List<BookData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
