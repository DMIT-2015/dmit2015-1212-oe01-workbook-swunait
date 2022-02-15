package dmit2015.entity;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "COUNTRIES", schema = "HR")
public class CountriesEntity {

    @Id
    @Column(name = "COUNTRY_ID", nullable = false, length = 2)
    private String countryId;
    @Basic
    @Column(name = "COUNTRY_NAME", nullable = true, length = 40)
    private String countryName;
    @Basic
    @Column(name = "REGION_ID", nullable = true, precision = 0)
    private BigInteger regionId;
    @ManyToOne
    @JoinColumn(name = "REGION_ID", referencedColumnName = "REGION_ID", insertable = false, updatable = false)
    private RegionsEntity regionsByRegionId;
    @OneToMany(mappedBy = "countriesByCountryId")
    private Collection<LocationsEntity> locationsByCountryId;

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public BigInteger getRegionId() {
        return regionId;
    }

    public void setRegionId(BigInteger regionId) {
        this.regionId = regionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountriesEntity that = (CountriesEntity) o;
        return Objects.equals(countryId, that.countryId) && Objects.equals(countryName, that.countryName) && Objects.equals(regionId, that.regionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryId, countryName, regionId);
    }

    public RegionsEntity getRegionsByRegionId() {
        return regionsByRegionId;
    }

    public void setRegionsByRegionId(RegionsEntity regionsByRegionId) {
        this.regionsByRegionId = regionsByRegionId;
    }

    public Collection<LocationsEntity> getLocationsByCountryId() {
        return locationsByCountryId;
    }

    public void setLocationsByCountryId(Collection<LocationsEntity> locationsByCountryId) {
        this.locationsByCountryId = locationsByCountryId;
    }
}
