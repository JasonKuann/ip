package blitz.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A task that has a deadline date (and optionally time).
 *
 * <p>Parses common date formats into LocalDateTime for nicer display.
 */
public class Deadline extends Task {

    protected final String by;

    /**
     * Construct a Deadline.
     *
     * @param description human description
     * @param dateStr     user-provided date string (e.g., "2019-12-02" or "2/12/2019 1800")
     */
    public Deadline(final String description, final String dateStr) {
        super(description);
        this.by = dateStr;
        this.date = parseDate(dateStr);
    }

    @Override
    public String getTaskInfo() {
        if (date == null) {
            return "by: " + by;
        }

        final DateTimeFormatter dateOnly = DateTimeFormatter.ofPattern("MMM d yyyy");
        if (date.getHour() == 0 && date.getMinute() == 0) {
            return "by: " + date.format(dateOnly);
        }

        final DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");
        return "by: " + date.format(dateTime);
    }

    /**
     * Attempts to parse common date formats into a LocalDateTime.
     *
     * Supported formats:
     * <ul>
     *   <li>yyyy-MM-dd</li>
     *   <li>d/M/yyyy HHmm</li>
     *   <li>d/M/yyyy</li>
     * </ul>
     *
     * @param dateStr user-provided string
     * @return parsed LocalDateTime or null if parsing failed
     */
    private LocalDateTime parseDate(final String dateStr) {
        if (dateStr == null) {
            return null;
        }
        final String s = dateStr.trim();

        // 1) ISO date: yyyy-MM-dd
        try {
            final LocalDate ld = LocalDate.parse(s);
            return ld.atStartOfDay();
        } catch (Exception ignored) {
            // fall through
        }

        // 2) d/M/yyyy HHmm
        try {
            final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            return LocalDateTime.parse(s, dtf);
        } catch (Exception ignored) {
            // fall through
        }

        // 3) d/M/yyyy
        try {
            final DateTimeFormatter df = DateTimeFormatter.ofPattern("d/M/yyyy");
            final LocalDate ld = LocalDate.parse(s, df);
            return ld.atStartOfDay();
        } catch (Exception ignored) {
            // give up
        }

        return null;
    }

    /** Return the original string provided by the user. */
    public String getBy() {
        return by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (" + getTaskInfo() + ")";
    }
}
