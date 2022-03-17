package ca.nait.dmit.ejb;

import jakarta.annotation.Resource;
import jakarta.batch.operations.JobOperator;
import jakarta.batch.runtime.BatchRuntime;
import jakarta.batch.runtime.BatchStatus;
import jakarta.batch.runtime.JobExecution;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.logging.Logger;

@Stateless
public class MonitorBatchJobSessionBean {

    private Logger _logger = Logger.getLogger(MonitorBatchJobSessionBean.class.getName());

    @Resource
    private TimerService _timerService;

    @Inject
    @ConfigProperty(name="batch.monitor.initialDuration")
    private long _initialDuration;  // in milliseconds

    @Inject
    @ConfigProperty(name="batch.monitor.intervalDuration")
    private long _intervalDuration;  // in milliseconds

    @Timeout
    public void checkBatchJobStatus(Timer timer) {
        // Extract the jobId from the timer
        long jobId = (long) timer.getInfo();
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        JobExecution jobExecution = jobOperator.getJobExecution(jobId);
        if (jobExecution.getBatchStatus() == BatchStatus.COMPLETED) {
            timer.cancel();
            _logger.info("BATCH job " + jobId + " COMPLETED");
            // send email to notified batch has COMPLETED
        } else if (jobExecution.getBatchStatus() == BatchStatus.FAILED) {
            // send email to notified batch job has FAILED
            timer.cancel();
            _logger.info("BATCH job " + jobId + " FAILED");
        }
    }

    public Timer createTimer(long jobId) {
        return _timerService.createTimer(_initialDuration, _intervalDuration, jobId);
    }

}