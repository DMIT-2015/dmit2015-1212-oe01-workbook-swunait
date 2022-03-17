package ca.nait.dmit.ejb;

import common.ejb.TimerServiceHelper;
import jakarta.annotation.Resource;
import jakarta.batch.operations.JobOperator;
import jakarta.batch.runtime.BatchRuntime;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import java.time.LocalDateTime;
import java.util.logging.Logger;

/**
 * A stateless session bean for creating a EJB Timer and for handling the event when the timer expires.
 *
 * To use this session session from another managed class, simply use the @Inject annotation to inject an instance of this bean:
 *
 *  @Inject
 *  private BatchJobTimerSessionBean _batchTimerBean;
 *
 */
@Stateless  // Must be @Stateless or @Singleton. Timer service does not support Stateful session beans
public class BatchJobTimerSessionBean {

   @Resource   // This is a container created resource
   private TimerService _timerService;

   private Logger _logger = Logger.getLogger(BatchJobTimerSessionBean.class.getName());
    // @Inject // Use only if your project includes a LoggerProducer
    // private Logger _logger;

        @Inject
    private MonitorBatchJobSessionBean _monitorBatchJobSessionBean; // This is used to monitor the status of the batch job

    /**
     * The annotation @Timeout method is executed when a programmatic timer expires
     * @param timer contains information about the timer that expired
     */
    @Timeout
    public void timeout(Timer timer) {
        try {
            // Get the name of the batch job XML file that included as information with the timer
            String batchJobXmlFilename = (String) timer.getInfo();
            // Get the JobOperator from the BatchRuntime
            JobOperator jobOperator = BatchRuntime.getJobOperator();
            // Create a new job instance and start the first execution of that instance asynchronously.
            long executionId = jobOperator.start(batchJobXmlFilename, null);
            _logger.info("Successfully started batch job with executionId " + executionId);

                        // Create an interval timer to monitor the status of the batch job
                        _monitorBatchJobSessionBean.createTimer(executionId);

        } catch (Exception e) {
            _logger.fine(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Create a new Timer with info about the batch job XML filename.
     * @param batchJobXmlFilename the info to included with the TimerConfig
     * @param eventDateTime the start date time of the event
     * @return
     */
    public Timer createTimer(String batchJobXmlFilename, LocalDateTime eventDateTime) {
        TimerConfig timerConfig = new TimerConfig();
        timerConfig.setInfo(batchJobXmlFilename);
        ScheduleExpression eventScheduleExpression = TimerServiceHelper.toScheduleExpression(eventDateTime);
        return _timerService.createCalendarTimer(eventScheduleExpression, timerConfig);
    }

}