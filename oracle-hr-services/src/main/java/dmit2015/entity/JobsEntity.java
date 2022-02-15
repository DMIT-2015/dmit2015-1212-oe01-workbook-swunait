package dmit2015.entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "JOBS", schema = "HR")
public class JobsEntity {

    @Id
    @Column(name = "JOB_ID", nullable = false, length = 10)
    private String jobId;
    @Basic
    @Column(name = "JOB_TITLE", nullable = false, length = 35)
    private String jobTitle;
    @Basic
    @Column(name = "MIN_SALARY", nullable = true, precision = 0)
    private Integer minSalary;
    @Basic
    @Column(name = "MAX_SALARY", nullable = true, precision = 0)
    private Integer maxSalary;
    @OneToMany(mappedBy = "jobsByJobId")
    private Collection<EmployeesEntity> employeesByJobId;
    @OneToMany(mappedBy = "jobsByJobId")
    private Collection<JobHistoryEntity> jobHistoriesByJobId;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Integer getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Integer minSalary) {
        this.minSalary = minSalary;
    }

    public Integer getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Integer maxSalary) {
        this.maxSalary = maxSalary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobsEntity that = (JobsEntity) o;
        return Objects.equals(jobId, that.jobId) && Objects.equals(jobTitle, that.jobTitle) && Objects.equals(minSalary, that.minSalary) && Objects.equals(maxSalary, that.maxSalary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, jobTitle, minSalary, maxSalary);
    }

    public Collection<EmployeesEntity> getEmployeesByJobId() {
        return employeesByJobId;
    }

    public void setEmployeesByJobId(Collection<EmployeesEntity> employeesByJobId) {
        this.employeesByJobId = employeesByJobId;
    }

    public Collection<JobHistoryEntity> getJobHistoriesByJobId() {
        return jobHistoriesByJobId;
    }

    public void setJobHistoriesByJobId(Collection<JobHistoryEntity> jobHistoriesByJobId) {
        this.jobHistoriesByJobId = jobHistoriesByJobId;
    }
}
