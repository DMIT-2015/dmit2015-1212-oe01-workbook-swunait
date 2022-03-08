package ca.nait.dmit.batch.listener;

import jakarta.batch.api.listener.AbstractStepListener;
import jakarta.batch.runtime.context.JobContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * This executes before and after a step execution runs.
 * To apply this listener to a batch job you must define a listener element in the Job Specification Language (JSL) file
 * INSIDE the step element as follows:
 *
 * <listeners>
 * <listener ref="itemStepListener" />
 * </listeners>
 */
@Named
public class EnforcementZoneCentreStepListener extends AbstractStepListener {

    @Inject
    private JobContext _jobContext;

    private long _startTime;

    @Override
    public void beforeStep() throws Exception {
        System.out.println("beforeStep");
        _startTime = System.currentTimeMillis();
    }

    @Override
    public void afterStep() throws Exception {
        System.out.println("afterStep");
        long endTime = System.currentTimeMillis();
        long durationMilliseconds = (endTime - _startTime);
        String message = String.format("Step completed in %d milliseconds", durationMilliseconds);
        System.out.println(message);

    }

}