package ca.nait.dmit.view;

import ca.nait.dmit.ejb.BatchJobTimerSessionBean;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;

import jakarta.ejb.Timer;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Named("currentBatchJobSchedulerController")
@RequestScoped
public class BatchJobSchedulerController {

    @NotBlank(message = "Batch Job XML Filename field value is required.")
    @Getter @Setter
    private String batchJobXmlFilename;

    @NotNull(message = "Scheduled Date Time field value is required.")
    @Future(message = "Scheduled Date Time field value must be in the future")
    @Getter @Setter
    private LocalDateTime scheduledDateTIme = LocalDateTime.now().plusMinutes(5);    // Set the default time to 15 minutes from now

    @Inject
    private BatchJobTimerSessionBean _batchJobSingleEventTimerSessionBean;

    public void scheduleBatchJob() {
        Timer timer = _batchJobSingleEventTimerSessionBean.createTimer(batchJobXmlFilename, scheduledDateTIme);
        Messages.addGlobalInfo("Successfully created timer that will run at {0}", timer.getNextTimeout().toString());
    }

}