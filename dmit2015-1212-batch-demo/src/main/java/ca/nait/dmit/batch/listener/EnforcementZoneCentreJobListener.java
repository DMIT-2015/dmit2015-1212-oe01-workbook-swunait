package ca.nait.dmit.batch.listener;

import jakarta.batch.api.listener.AbstractJobListener;
import jakarta.batch.runtime.context.JobContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;


/**
 * This executes before and after a job execution runs.
 * To apply this listener to a batch job you must define a listener element in the Job Specification Language (JSL) file
 * BEFORE the step element as follows:
 *
 * <listeners>
 * <listener ref="jobListener" />
 * </listeners>
 */
@Named
public class EnforcementZoneCentreJobListener extends AbstractJobListener {

    @Inject
    private JobContext _jobContext;

    private Logger _logger = Logger.getLogger(EnforcementZoneCentreJobListener.class.getName());

    private long _startTime;

    @Override
    public void beforeJob() throws Exception {
        _logger.info(_jobContext.getJobName() + " beforeJob");
        _startTime = System.currentTimeMillis();


    }

    @Override
    public void afterJob() throws Exception {
        _logger.info(_jobContext.getJobName() + "afterJob");
        long endTime = System.currentTimeMillis();
        long durationSeconds = (endTime - _startTime) / 1000;
        String message = _jobContext.getJobName() + " completed in " + durationSeconds + " seconds";
        _logger.info(message);

    }

}