import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected String by;

    public Deadline(String description, String dateStr) {
        super(description);
        this.by = dateStr;
        this.date = parseDate(dateStr);
    }

    @Override
    public String getTaskInfo() {
        if (date == null) {
            return "by: " + by;
        }

        DateTimeFormatter dateOnly = DateTimeFormatter.ofPattern("MMM d yyyy");
        if (date.getHour() == 0 && date.getMinute() == 0) {
            return "by: " + date.format(dateOnly);
        } else {
            DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");
            return "by: " + date.format(dateTime);
        }
    }

    private LocalDateTime parseDate(String dateStr) {
        if (dateStr == null) {
            return null;
        }

        String restOfDate = dateStr.trim();

        try {
            LocalDate local = LocalDate.parse(restOfDate);
            return local.atStartOfDay();
        } catch (Exception e) {
            
        }

        try {
            DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            return LocalDateTime.parse(restOfDate, dateTimeFormat);
        } catch (Exception e) {
            
        }

        try {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate local = LocalDate.parse(restOfDate, dateFormat);
            return local.atStartOfDay();
        } catch (Exception e) {
            
        }

        return null;
    }

    public String getBy() {
        return this.by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (" + getTaskInfo() + ")";
    }
}
