package common.ejb;

import jakarta.ejb.*;
import java.time.LocalDateTime;

/**
 * The EJB timer service currently does not support LocalDateTime.
 * This helper class contains a method to convert LocalDateTime to ScheduleExpression
 */
public class TimerServiceHelper {

    /**
     * Helper method for converting a LocalDateTime to a ScheduledExpression.
     *
     * @param fromDataTime the LocalDateTime to convert to ScheduleExpression
     * @return the ScheduleExpression created using the LocalDateTime
     */
    public static ScheduleExpression toScheduleExpression(LocalDateTime fromDataTime) {
        ScheduleExpression scheduleExpression = new ScheduleExpression();
        scheduleExpression.year(fromDataTime.getYear());
        scheduleExpression.month(fromDataTime.getMonth().getValue());  // Both ScheduleExpression and LocalDateTime uses a month number from 1 to 12.
        scheduleExpression.dayOfMonth(fromDataTime.getDayOfMonth());
        scheduleExpression.hour(fromDataTime.getHour());
        scheduleExpression.minute(fromDataTime.getMinute());
        scheduleExpression.second(fromDataTime.getSecond());
        return scheduleExpression;
    }
}