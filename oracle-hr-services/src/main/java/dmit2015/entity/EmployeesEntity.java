package dmit2015.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "EMPLOYEES", schema = "HR")
public class EmployeesEntity {

    @Id
    @Column(name = "EMPLOYEE_ID", nullable = false, precision = 0)
    private Integer employeeId;
    @Basic
    @Column(name = "FIRST_NAME", nullable = true, length = 20)
    private String firstName;
    @Basic
    @Column(name = "LAST_NAME", nullable = false, length = 25)
    private String lastName;
    @Basic
    @Column(name = "EMAIL", nullable = false, length = 25)
    private String email;
    @Basic
    @Column(name = "PHONE_NUMBER", nullable = true, length = 20)
    private String phoneNumber;
    @Basic
    @Column(name = "HIRE_DATE", nullable = false)
    private LocalDate hireDate;
    @Basic
    @Column(name = "JOB_ID", nullable = false, length = 10)
    private String jobId;
    @Basic
    @Column(name = "SALARY", nullable = true, precision = 2)
    private Integer salary;
    @Basic
    @Column(name = "COMMISSION_PCT", nullable = true, precision = 2)
    private Byte commissionPct;
    @Basic
    @Column(name = "MANAGER_ID", nullable = true, precision = 0)
    private Integer managerId;
    @Basic
    @Column(name = "DEPARTMENT_ID", nullable = true, precision = 0)
    private Short departmentId;
    @OneToMany(mappedBy = "employeesByManagerId")
    private Collection<DepartmentsEntity> departmentsByEmployeeId;
    @ManyToOne
    @JoinColumn(name = "JOB_ID", referencedColumnName = "JOB_ID", nullable = false, insertable = false, updatable = false)
    private JobsEntity jobsByJobId;
    @ManyToOne
    @JoinColumn(name = "MANAGER_ID", referencedColumnName = "EMPLOYEE_ID", insertable = false, updatable = false)
    private EmployeesEntity employeesByManagerId;
    @OneToMany(mappedBy = "employeesByManagerId")
    private Collection<EmployeesEntity> employeesByEmployeeId;
    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "DEPARTMENT_ID", insertable = false, updatable = false)
    private DepartmentsEntity departmentsByDepartmentId;
    @OneToMany(mappedBy = "employeesByEmployeeId")
    private Collection<JobHistoryEntity> jobHistoriesByEmployeeId;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Byte getCommissionPct() {
        return commissionPct;
    }

    public void setCommissionPct(Byte commissionPct) {
        this.commissionPct = commissionPct;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Short getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Short departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeesEntity that = (EmployeesEntity) o;
        return Objects.equals(employeeId, that.employeeId) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(hireDate, that.hireDate) && Objects.equals(jobId, that.jobId) && Objects.equals(salary, that.salary) && Objects.equals(commissionPct, that.commissionPct) && Objects.equals(managerId, that.managerId) && Objects.equals(departmentId, that.departmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, firstName, lastName, email, phoneNumber, hireDate, jobId, salary, commissionPct, managerId, departmentId);
    }

    public Collection<DepartmentsEntity> getDepartmentsByEmployeeId() {
        return departmentsByEmployeeId;
    }

    public void setDepartmentsByEmployeeId(Collection<DepartmentsEntity> departmentsByEmployeeId) {
        this.departmentsByEmployeeId = departmentsByEmployeeId;
    }

    public JobsEntity getJobsByJobId() {
        return jobsByJobId;
    }

    public void setJobsByJobId(JobsEntity jobsByJobId) {
        this.jobsByJobId = jobsByJobId;
    }

    public EmployeesEntity getEmployeesByManagerId() {
        return employeesByManagerId;
    }

    public void setEmployeesByManagerId(EmployeesEntity employeesByManagerId) {
        this.employeesByManagerId = employeesByManagerId;
    }

    public Collection<EmployeesEntity> getEmployeesByEmployeeId() {
        return employeesByEmployeeId;
    }

    public void setEmployeesByEmployeeId(Collection<EmployeesEntity> employeesByEmployeeId) {
        this.employeesByEmployeeId = employeesByEmployeeId;
    }

    public DepartmentsEntity getDepartmentsByDepartmentId() {
        return departmentsByDepartmentId;
    }

    public void setDepartmentsByDepartmentId(DepartmentsEntity departmentsByDepartmentId) {
        this.departmentsByDepartmentId = departmentsByDepartmentId;
    }

    public Collection<JobHistoryEntity> getJobHistoriesByEmployeeId() {
        return jobHistoriesByEmployeeId;
    }

    public void setJobHistoriesByEmployeeId(Collection<JobHistoryEntity> jobHistoriesByEmployeeId) {
        this.jobHistoriesByEmployeeId = jobHistoriesByEmployeeId;
    }
}
