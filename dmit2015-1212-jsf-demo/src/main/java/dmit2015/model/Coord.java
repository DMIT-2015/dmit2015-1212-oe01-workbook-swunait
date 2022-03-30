package dmit2015.model;

import jakarta.annotation.Generated;

import java.util.HashMap;
import java.util.Map;

@Generated("jsonschema2pojo")
public class Coord {

    private Double lon;
    private Double lat;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
