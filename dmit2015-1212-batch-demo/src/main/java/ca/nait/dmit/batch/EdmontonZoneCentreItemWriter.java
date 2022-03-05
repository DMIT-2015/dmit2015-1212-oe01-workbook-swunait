package ca.nait.dmit.batch;

import ca.nait.dmit.entity.EnforcementZoneCentre;
import jakarta.batch.api.chunk.AbstractItemWriter;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Named
public class EdmontonZoneCentreItemWriter extends AbstractItemWriter {

    @PersistenceContext(unitName = "mssql-jpa-pu")
    private EntityManager _entityManager;

    @Override
    public void writeItems(List<Object> items) throws Exception {
        for (Object currentItem : items) {
            EnforcementZoneCentre entity = (EnforcementZoneCentre) currentItem;
            _entityManager.persist(entity);
        }

    }
}
