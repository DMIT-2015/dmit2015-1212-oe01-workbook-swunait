package ca.nait.dmit.repository;

import ca.nait.dmit.entity.EnforcementZoneCentre;
import common.jpa.AbstractJpaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class EnforcementZoneCentreRepository extends AbstractJpaRepository<EnforcementZoneCentre, Short> {

    public EnforcementZoneCentreRepository() {
        super(EnforcementZoneCentre.class);
    }

}
    