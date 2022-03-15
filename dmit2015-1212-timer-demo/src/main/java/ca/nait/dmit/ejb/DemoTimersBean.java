package ca.nait.dmit.ejb;

import common.ejb.EmailSessionBean;
import jakarta.annotation.Resource;
import jakarta.batch.operations.JobOperator;
import jakarta.batch.runtime.BatchRuntime;
import jakarta.batch.runtime.BatchStatus;
import jakarta.batch.runtime.JobExecution;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.logging.Logger;

@Singleton                // Instruct the container to create a single instance of this EJB
@Startup                // Create this EJB is created when this app starts
public class DemoTimersBean {        // Also known as Calendar-Based Timers

//        private Logger _logger = Logger.getLogger(DemoTimersBean.class.getName());
        @Inject
        private Logger _logger;

        /**
         * Assuming you have define the following entries in your META-INF/microprofile-config.properties file
             ca.dmit2015.config.SYSADMIN_EMAIL=yourUsername@yourEmailServer
         * This code assumes that this project is configured to use Eclipse Microprofile.
         * You can add the following to pom.xml to enable Eclipse Microprofile

         <dependency>
            <groupId>org.eclipse.microprofile</groupId>
            <artifactId>microprofile</artifactId>
            <version>5.0</version>
            <type>pom</type>
            <scope>provided</scope>
        </dependency>
         */
        @Inject
        @ConfigProperty(name="ca.dmit2015.config.SYSADMIN_EMAIL")
        private String mailToAddress;

        @Inject
        private EmailSessionBean mail;

        private void sendEmail(Timer timer) {
                if (!mailToAddress.isBlank()) {
                        String mailSubject = timer.getInfo().toString();
                        String mailText = String.format("You have a %s on %s %s, %s  ",
                                timer.getInfo().toString(),
                                timer.getSchedule().getDayOfWeek(),
                                timer.getSchedule().getMonth(),
                                timer.getSchedule().getYear()
                        );
                        try {
                                mail.sendTextEmail(mailToAddress, mailSubject, mailText);
                                _logger.info("Successfully sent email to " + mailToAddress);
                        } catch (Exception e) {
                                e.printStackTrace();
                                _logger.fine("Error sending email with exception " + e.getMessage());
                        }
                }
        }

        // @Schedules({
        //         @Schedule(second = "0", minute ="50", hour = "9", dayOfWeek = "Mon,Wed", month = "Jan-Apr", year = "2022", info ="DMIT2015-OA01 Meeting", persistent = false),
        //         @Schedule(second = "0", minute ="50", hour = "7", dayOfWeek = "Tue", month = "Jan-Apr", year = "2022", info ="DMIT2015-OA01 Meeting", persistent = false)
        // })
        public void dmit2015SectionOA01ClassNotifiation(Timer timer) {
                sendEmail(timer);
        }


        @Inject
        @ConfigProperty(name = "enforcement.job.xml")
        private String jobXmlFileName;

        @Resource
        private TimerService timerService;

        @Timeout
        public void checkBatchJobStatus(Timer timer) {
                // Check the status of the batch job
                long jobId = (long) timer.getInfo();
                JobOperator jobOperator = BatchRuntime.getJobOperator();
                JobExecution jobExecution = jobOperator.getJobExecution(jobId);
                // Cancel the timer if the JobStatus is COMPLETED or FAILED
                if (jobExecution.getBatchStatus() == BatchStatus.COMPLETED || jobExecution.getBatchStatus() == BatchStatus.FAILED) {
                        timer.cancel();

                }
        }

        @Schedule(second = "0", minute ="11", hour = "20", dayOfWeek = "Mon,Wed,Fri", month = "Jan-Apr", year = "2022", info ="DMIT2015-OE01 Meeting", persistent = false)
        public void dmit2015SectionOE01ClassNotifiation(Timer timer) {
             sendEmail(timer);

             // Start a batch job to import data from CSV file to SQL Server
                JobOperator jobOperator = BatchRuntime.getJobOperator();
                long jobId = jobOperator.start(jobXmlFileName, null);

                // Schedule a timer to execute in 3 seconds and repeats every 30 seconds
                timerService.createTimer(3000, 30000, jobId);
        }

}