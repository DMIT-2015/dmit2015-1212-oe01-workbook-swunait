package dmit2015.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class JobHistoryEntityPK implements Serializable {
    @Column(name = "EMPLOYEE_ID", nullable = false, precision = 0)
    @Id
    private Integer employeeId;
    @Column(name = "START_DATE", nullable = false)
    @Id
    private LocalDate startDate;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobHistoryEntityPK that = (JobHistoryEntityPK) o;
        return Objects.equals(employeeId, that.employeeId) && Objects.equals(startDate, that.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, startDate);
    }
}
