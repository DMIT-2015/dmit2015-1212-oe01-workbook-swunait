package dmit2015.entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "LOCATIONS", schema = "HR")
public class LocationsEntity {

    @Id
    @Column(name = "LOCATION_ID", nullable = false, precision = 0)
    private Short locationId;
    @Basic
    @Column(name = "STREET_ADDRESS", nullable = true, length = 40)
    private String streetAddress;
    @Basic
    @Column(name = "POSTAL_CODE", nullable = true, length = 12)
    private String postalCode;
    @Basic
    @Column(name = "CITY", nullable = false, length = 30)
    private String city;
    @Basic
    @Column(name = "STATE_PROVINCE", nullable = true, length = 25)
    private String stateProvince;
    @Basic
    @Column(name = "COUNTRY_ID", nullable = true, length = 2)
    private String countryId;
    @OneToMany(mappedBy = "locationsByLocationId")
    private Collection<DepartmentsEntity> departmentsByLocationId;
    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "COUNTRY_ID", insertable = false, updatable = false)
    private CountriesEntity countriesByCountryId;

    public Short getLocationId() {
        return locationId;
    }

    public void setLocationId(Short locationId) {
        this.locationId = locationId;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationsEntity that = (LocationsEntity) o;
        return Objects.equals(locationId, that.locationId) && Objects.equals(streetAddress, that.streetAddress) && Objects.equals(postalCode, that.postalCode) && Objects.equals(city, that.city) && Objects.equals(stateProvince, that.stateProvince) && Objects.equals(countryId, that.countryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId, streetAddress, postalCode, city, stateProvince, countryId);
    }

    public Collection<DepartmentsEntity> getDepartmentsByLocationId() {
        return departmentsByLocationId;
    }

    public void setDepartmentsByLocationId(Collection<DepartmentsEntity> departmentsByLocationId) {
        this.departmentsByLocationId = departmentsByLocationId;
    }

    public CountriesEntity getCountriesByCountryId() {
        return countriesByCountryId;
    }

    public void setCountriesByCountryId(CountriesEntity countriesByCountryId) {
        this.countriesByCountryId = countriesByCountryId;
    }
}
