package dmit2015.entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "DEPARTMENTS", schema = "HR")
public class DepartmentsEntity {

    @Id
    @Column(name = "DEPARTMENT_ID", nullable = false, precision = 0)
    private Short departmentId;
    @Basic
    @Column(name = "DEPARTMENT_NAME", nullable = false, length = 30)
    private String departmentName;
    @Basic
    @Column(name = "MANAGER_ID", nullable = true, precision = 0)
    private Integer managerId;
    @Basic
    @Column(name = "LOCATION_ID", nullable = true, precision = 0)
    private Short locationId;
    @ManyToOne
    @JoinColumn(name = "MANAGER_ID", referencedColumnName = "EMPLOYEE_ID", insertable = false, updatable = false)
    private EmployeesEntity employeesByManagerId;
    @ManyToOne
    @JoinColumn(name = "LOCATION_ID", referencedColumnName = "LOCATION_ID", insertable = false, updatable = false)
    private LocationsEntity locationsByLocationId;
    @OneToMany(mappedBy = "departmentsByDepartmentId")
    private Collection<EmployeesEntity> employeesByDepartmentId;
    @OneToMany(mappedBy = "departmentsByDepartmentId")
    private Collection<JobHistoryEntity> jobHistoriesByDepartmentId;

    public Short getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Short departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Short getLocationId() {
        return locationId;
    }

    public void setLocationId(Short locationId) {
        this.locationId = locationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentsEntity that = (DepartmentsEntity) o;
        return Objects.equals(departmentId, that.departmentId) && Objects.equals(departmentName, that.departmentName) && Objects.equals(managerId, that.managerId) && Objects.equals(locationId, that.locationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentId, departmentName, managerId, locationId);
    }

    public EmployeesEntity getEmployeesByManagerId() {
        return employeesByManagerId;
    }

    public void setEmployeesByManagerId(EmployeesEntity employeesByManagerId) {
        this.employeesByManagerId = employeesByManagerId;
    }

    public LocationsEntity getLocationsByLocationId() {
        return locationsByLocationId;
    }

    public void setLocationsByLocationId(LocationsEntity locationsByLocationId) {
        this.locationsByLocationId = locationsByLocationId;
    }

    public Collection<EmployeesEntity> getEmployeesByDepartmentId() {
        return employeesByDepartmentId;
    }

    public void setEmployeesByDepartmentId(Collection<EmployeesEntity> employeesByDepartmentId) {
        this.employeesByDepartmentId = employeesByDepartmentId;
    }

    public Collection<JobHistoryEntity> getJobHistoriesByDepartmentId() {
        return jobHistoriesByDepartmentId;
    }

    public void setJobHistoriesByDepartmentId(Collection<JobHistoryEntity> jobHistoriesByDepartmentId) {
        this.jobHistoriesByDepartmentId = jobHistoriesByDepartmentId;
    }
}
