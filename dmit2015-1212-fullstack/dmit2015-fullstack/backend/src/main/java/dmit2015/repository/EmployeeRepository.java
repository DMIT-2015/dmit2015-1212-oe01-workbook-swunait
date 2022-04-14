package dmit2015.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.entity.Employee;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class EmployeeRepository extends AbstractJpaRepository<Employee, Long> {

    public EmployeeRepository() {
        super(Employee.class);
    }

}

