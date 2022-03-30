
package dmit2015.restclient;

import java.util.HashMap;
import java.util.Map;
import jakarta.annotation.Generated;

@Generated("jsonschema2pojo")
public class TimeZone {

    private String name;
    private Integer offset;
    private String currentTime;
    private Double currentTimeUnix;
    private Boolean isDst;
    private Integer dstSavings;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public Double getCurrentTimeUnix() {
        return currentTimeUnix;
    }

    public void setCurrentTimeUnix(Double currentTimeUnix) {
        this.currentTimeUnix = currentTimeUnix;
    }

    public Boolean getIsDst() {
        return isDst;
    }

    public void setIsDst(Boolean isDst) {
        this.isDst = isDst;
    }

    public Integer getDstSavings() {
        return dstSavings;
    }

    public void setDstSavings(Integer dstSavings) {
        this.dstSavings = dstSavings;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
